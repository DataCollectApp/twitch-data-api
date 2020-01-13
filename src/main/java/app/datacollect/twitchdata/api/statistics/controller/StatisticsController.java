package app.datacollect.twitchdata.api.statistics.controller;

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

  public StatisticsController(
      TwitchUserService twitchUserService, NameChangeService nameChangeService) {
    this.twitchUserService = twitchUserService;
    this.nameChangeService = nameChangeService;
  }

  @GetMapping("counters")
  public ResponseEntity<Counters> getCounters() {
    return ResponseEntity.ok(
        new Counters(
            twitchUserService.getTwitchUserCount(), nameChangeService.getNameChangeCount()));
  }
}
