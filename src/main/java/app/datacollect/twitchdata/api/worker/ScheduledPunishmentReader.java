package app.datacollect.twitchdata.api.worker;

import app.datacollect.lastread.service.LastReadService;
import app.datacollect.twitchdata.api.clearchat.assembler.ClearChatAssembler;
import app.datacollect.twitchdata.api.clearchat.service.ClearChatService;
import app.datacollect.twitchdata.api.clearmessage.assembler.ClearMessageAssembler;
import app.datacollect.twitchdata.api.clearmessage.service.ClearMessageService;
import app.datacollect.twitchdata.api.config.FeatureToggle;
import app.datacollect.twitchdata.api.globalclearchat.assembler.GlobalClearChatAssembler;
import app.datacollect.twitchdata.api.globalclearchat.service.GlobalClearChatService;
import app.datacollect.twitchdata.api.namechange.assembler.NameChangeAssembler;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import app.datacollect.twitchdata.api.twitchuser.assembler.TwitchUserAssembler;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import app.datacollect.twitchdata.feed.events.Event;
import app.datacollect.twitchdata.feed.events.clearchat.v1.ClearChatEventV1;
import app.datacollect.twitchdata.feed.events.clearmessage.v1.ClearMessageEventV1;
import app.datacollect.twitchdata.feed.events.globalclearchat.v1.GlobalClearChatEventV1;
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
public class ScheduledPunishmentReader {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledPunishmentReader.class);

  private static final String LAST_READ_NAME = "punishment";

  private final TwitchDataFeedReader twitchDataFeedReader;
  private final LastReadService lastReadService;
  private final FeatureToggle featureToggle;
  private final ClearMessageService clearMessageService;
  private final ClearMessageAssembler clearMessageAssembler;
  private final ClearChatService clearChatService;
  private final ClearChatAssembler clearChatAssembler;
  private final GlobalClearChatService globalClearChatService;
  private final GlobalClearChatAssembler globalClearChatAssembler;
  private final TwitchUserService twitchUserService;
  private final TwitchUserAssembler twitchUserAssembler;
  private final NameChangeService nameChangeService;
  private final NameChangeAssembler nameChangeAssembler;

  public ScheduledPunishmentReader(
      @Qualifier("punishmentFeedReader") TwitchDataFeedReader twitchDataFeedReader,
      LastReadService lastReadService,
      FeatureToggle featureToggle,
      ClearMessageService clearMessageService,
      ClearMessageAssembler clearMessageAssembler,
      ClearChatService clearChatService,
      ClearChatAssembler clearChatAssembler,
      GlobalClearChatService globalClearChatService,
      GlobalClearChatAssembler globalClearChatAssembler,
      TwitchUserService twitchUserService,
      TwitchUserAssembler twitchUserAssembler,
      NameChangeService nameChangeService,
      NameChangeAssembler nameChangeAssembler) {
    this.twitchDataFeedReader = twitchDataFeedReader;
    this.lastReadService = lastReadService;
    this.featureToggle = featureToggle;
    this.clearMessageService = clearMessageService;
    this.clearMessageAssembler = clearMessageAssembler;
    this.clearChatService = clearChatService;
    this.clearChatAssembler = clearChatAssembler;
    this.globalClearChatService = globalClearChatService;
    this.globalClearChatAssembler = globalClearChatAssembler;
    this.twitchUserService = twitchUserService;
    this.twitchUserAssembler = twitchUserAssembler;
    this.nameChangeService = nameChangeService;
    this.nameChangeAssembler = nameChangeAssembler;
  }

  @Scheduled(fixedDelay = 5000)
  public void process() {
    if (!featureToggle.getShouldReadPunishments()) {
      logger.debug("Not reading from punishment feed since feature toggle is false");
      return;
    }
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
      final String eventId = event.getMetadata().getEventId().toString();
      if (event.getEventData() instanceof ClearMessageEventV1) {
        success = process((ClearMessageEventV1) event.getEventData(), eventId);
      } else if (event.getEventData() instanceof ClearChatEventV1) {
        success = process((ClearChatEventV1) event.getEventData(), eventId);
      } else if (event.getEventData() instanceof GlobalClearChatEventV1) {
        success = process((GlobalClearChatEventV1) event.getEventData(), eventId);
      } else {
        logger.warn(
            "Encountered unexpected event with id '{}', type '{}', object type '{}' and version '{}'",
            event.getMetadata().getEventId(),
            event.getEventData().getEventType(),
            event.getEventData().getObjectType(),
            event.getEventData().getVersion());
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
          "Successfully processed '{}' '{}' events from the punishment feed",
          eventsProcessed,
          events.size());
    }
  }

  private boolean process(ClearMessageEventV1 event, String eventId) {
    try {
      final Optional<TwitchUser> twitchUser =
          twitchUserService.getTwitchUser(event.getTargetUsername());
      if (twitchUser.isEmpty()) {
        logger.error(
            "No twitch user with username '{}' found when processing clear message event with id '{}'",
            event.getTargetUsername(),
            eventId);
        return false;
      }
      clearMessageService.insert(clearMessageAssembler.assemble(event, twitchUser.get().getId()));
      return true;
    } catch (Exception ex) {
      logger.error(
          "Exception occurred while processing '{}' with id '{}'",
          event.getEventType(),
          eventId,
          ex);
      return false;
    }
  }

  private boolean process(ClearChatEventV1 event, String eventId) {
    try {
      Optional<TwitchUser> twitchUser =
          twitchUserService.getTwitchUser(Long.parseLong(event.getTargetUserId()));
      if (twitchUser.isEmpty()) {
        twitchUserService.saveTwitchUser(twitchUserAssembler.assemble(event));
        nameChangeService.saveNameChange(nameChangeAssembler.assemble(event, Optional.empty()));
        twitchUser = twitchUserService.getTwitchUser(Long.parseLong(event.getTargetUserId()));
      }
      if (twitchUser.isEmpty()) {
        logger.error(
            "No twitch user with username '{}' found when processing clear chat event with id '{}'",
            event.getTargetUsername(),
            eventId);
        return false;
      }
      clearChatService.insert(clearChatAssembler.assemble(event));
      return true;
    } catch (Exception ex) {
      logger.error(
          "Exception occurred while processing '{}' with id '{}'",
          event.getEventType(),
          eventId,
          ex);
      return false;
    }
  }

  private boolean process(GlobalClearChatEventV1 event, String eventId) {
    try {
      globalClearChatService.insert(globalClearChatAssembler.assemble(event));
      return true;
    } catch (Exception ex) {
      logger.error(
          "Exception occurred while processing '{}' with id '{}'",
          event.getEventType(),
          eventId,
          ex);
      return false;
    }
  }
}
