package app.datacollect.twitchdata.api.worker;

import app.datacollect.lastread.service.LastReadService;
import app.datacollect.twitchdata.api.invalidtwitchuser.assembler.InvalidTwitchUserAssembler;
import app.datacollect.twitchdata.api.invalidtwitchuser.service.InvalidTwitchUserService;
import app.datacollect.twitchdata.api.namechange.assembler.NameChangeAssembler;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import app.datacollect.twitchdata.api.twitchuser.assembler.TwitchUserAssembler;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import app.datacollect.twitchdata.feed.events.Event;
import app.datacollect.twitchdata.feed.events.chatmessage.v1.ChatMessageEventV1;
import app.datacollect.twitchdata.feed.reader.TwitchDataFeedReader;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScheduledTwitchUserNameHistoryReader {

  private static final Logger logger =
      LoggerFactory.getLogger(ScheduledTwitchUserNameHistoryReader.class);

  private static final String LAST_READ_NAME = "twitch_user_name_history";
  private static final List<Long> INVALID_USER_ID_LIST = List.of(0L, 1L);

  private final TwitchDataFeedReader twitchDataFeedReader;
  private final LastReadService lastReadService;
  private final TwitchUserService twitchUserService;
  private final TwitchUserAssembler twitchUserAssembler;
  private final InvalidTwitchUserService invalidTwitchUserService;
  private final InvalidTwitchUserAssembler invalidTwitchUserAssembler;
  private final NameChangeService nameChangeService;
  private final NameChangeAssembler nameChangeAssembler;

  public ScheduledTwitchUserNameHistoryReader(
      @Qualifier("chatMessageFeedReader") TwitchDataFeedReader twitchDataFeedReader,
      LastReadService lastReadService,
      TwitchUserService twitchUserService,
      TwitchUserAssembler twitchUserAssembler,
      InvalidTwitchUserService invalidTwitchUserService,
      InvalidTwitchUserAssembler invalidTwitchUserAssembler,
      NameChangeService nameChangeService,
      NameChangeAssembler nameChangeAssembler) {
    this.twitchDataFeedReader = twitchDataFeedReader;
    this.lastReadService = lastReadService;
    this.twitchUserService = twitchUserService;
    this.twitchUserAssembler = twitchUserAssembler;
    this.invalidTwitchUserService = invalidTwitchUserService;
    this.invalidTwitchUserAssembler = invalidTwitchUserAssembler;
    this.nameChangeService = nameChangeService;
    this.nameChangeAssembler = nameChangeAssembler;
  }

  @Scheduled(fixedDelay = 5000)
  public void process() {
    processPage();
  }

  @Transactional
  public void processPage() {
    final Optional<String> lastReadIdFromDb = lastReadService.getLastReadId(LAST_READ_NAME);
    lastReadIdFromDb.ifPresent(twitchDataFeedReader::setLastReadId);

    final List<Event> events = twitchDataFeedReader.getPage();

    boolean success = true;
    int eventsProcessed = 0;
    Optional<String> lastProcessedId = Optional.empty();
    for (int i = 0; i < events.size() && success; i++) {
      final Event event = events.get(i);
      if (event.getEventData() instanceof ChatMessageEventV1) {
        success =
            process(
                (ChatMessageEventV1) event.getEventData(),
                event.getMetadata().getEventId().toString());
      } else {
        logger.warn(
            "Encountered unexpected chat message event with id '{}'",
            event.getMetadata().getEventId());
        success = false;
      }
      if (success) {
        eventsProcessed++;
        lastProcessedId = Optional.of(event.getMetadata().getEventId().toString());
      }
    }

    if (lastReadIdFromDb.isPresent()) {
      if (lastProcessedId.isPresent() && !lastProcessedId.get().equals(lastReadIdFromDb.get())) {
        lastReadService.updateLastReadId(LAST_READ_NAME, lastProcessedId.get());
      }
    } else {
      lastProcessedId.ifPresent(
          currentLastReadId -> lastReadService.saveLastReadId(LAST_READ_NAME, currentLastReadId));
    }

    if (!events.isEmpty()) {
      logger.info(
          "Successfully processed '{}' of '{}' events from the chat message feed",
          eventsProcessed,
          events.size());
    }
  }

  private boolean process(ChatMessageEventV1 event, String eventId) {
    try {
      if (!INVALID_USER_ID_LIST.contains(event.getUserId())) {
        final Optional<TwitchUser> optionalTwitchUser =
            twitchUserService.getTwitchUser(event.getUserId());
        if (optionalTwitchUser.isPresent()) {
          final TwitchUser twitchUser = optionalTwitchUser.get();
          if (!twitchUser.getUsername().equals(event.getUsername())
              && !twitchUser.getDisplayName().equals(event.getDisplayName())) {
            twitchUserService.updateTwitchUser(
                event.getUserId(), event.getUsername(), event.getDisplayName());
            nameChangeService.saveNameChange(
                nameChangeAssembler.assemble(event, Optional.of(twitchUser.getUsername())));
          } else if (!twitchUser.getDisplayName().equals(event.getDisplayName())) {
            twitchUserService.updateTwitchUser(
                event.getUserId(), event.getUsername(), event.getDisplayName());
          }
        } else {
          twitchUserService.saveTwitchUser(twitchUserAssembler.assemble(event));
          nameChangeService.saveNameChange(nameChangeAssembler.assemble(event, Optional.empty()));
        }
      } else {
        invalidTwitchUserService.saveInvalidTwitchUser(invalidTwitchUserAssembler.assemble(event));
      }
      return true;
    } catch (Exception ex) {
      logger.error(
          "Exception occurred while processing chat message event with id '{}'", eventId, ex);
      return false;
    }
  }
}
