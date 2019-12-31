package app.datacollect.twitchdata.api.worker;

import app.datacollect.twitchdata.api.invalidtwitchuser.assembler.InvalidTwitchUserAssembler;
import app.datacollect.twitchdata.api.invalidtwitchuser.service.InvalidTwitchUserService;
import app.datacollect.twitchdata.api.lastread.service.LastReadService;
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
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
      TwitchDataFeedReader twitchDataFeedReader,
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

    final List<ChatMessageEventV1> chatMessageEvents =
        events.stream()
            .filter(e -> e instanceof ChatMessageEventV1)
            .map(e -> (ChatMessageEventV1) e)
            .collect(Collectors.toList());

    for (final ChatMessageEventV1 event : chatMessageEvents) {
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
        logger.info(
            "Saved invalid user with id '{}' and username '{}'",
            event.getUserId(),
            event.getUsername());
      }
    }

    if (lastReadIdFromDb.isPresent()) {
      final Optional<String> lastReadId = twitchDataFeedReader.getLastReadId();
      if (lastReadId.isPresent() && !lastReadId.get().equals(lastReadIdFromDb.get())) {
        lastReadService.updateLastReadId(LAST_READ_NAME, lastReadId.get());
      }
    } else {
      twitchDataFeedReader
          .getLastReadId()
          .ifPresent(
              currentLastReadId ->
                  lastReadService.saveLastReadId(LAST_READ_NAME, currentLastReadId));
    }

    if (!events.isEmpty()) {
      logger.info(
          "Successfully processed '{}' of '{}' events from the chat message feed",
          chatMessageEvents.size(),
          events.size());
    }
  }
}
