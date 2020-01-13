package app.datacollect.twitchdata.api.statistics.controller;

import app.datacollect.twitchdata.api.clearchat.service.ClearChatService;
import app.datacollect.twitchdata.api.clearmessage.service.ClearMessageService;
import app.datacollect.twitchdata.api.globalclearchat.service.GlobalClearChatService;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import app.datacollect.twitchdata.api.statistics.counters.Counters;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("statistics")
public class StatisticsController {

  private final TwitchUserService twitchUserService;
  private final NameChangeService nameChangeService;
  private final ClearChatService clearChatService;
  private final GlobalClearChatService globalClearChatService;
  private final ClearMessageService clearMessageService;

  public StatisticsController(
      TwitchUserService twitchUserService,
      NameChangeService nameChangeService,
      ClearChatService clearChatService,
      GlobalClearChatService globalClearChatService,
      ClearMessageService clearMessageService) {
    this.twitchUserService = twitchUserService;
    this.nameChangeService = nameChangeService;
    this.clearChatService = clearChatService;
    this.globalClearChatService = globalClearChatService;
    this.clearMessageService = clearMessageService;
  }

  @GetMapping("counters")
  public ResponseEntity<Counters> getCounters() {
    return ResponseEntity.ok(
        new Counters(
            twitchUserService.getTwitchUserCount(),
            nameChangeService.getNameChangeCount(),
            clearChatService.getClearChatCount(),
            globalClearChatService.getClobalClearChatCount(),
            clearMessageService.getClearMessageCount()));
  }
}
