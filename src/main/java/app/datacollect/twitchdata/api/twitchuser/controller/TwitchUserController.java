package app.datacollect.twitchdata.api.twitchuser.controller;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.twitchuser.assembler.TwitchUserResourceAssembler;
import app.datacollect.twitchdata.api.twitchuser.resource.TwitchUserResource;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("twitch-user")
public class TwitchUserController {

  private final TwitchUserService service;
  private final TwitchUserResourceAssembler resourceAssembler;

  public TwitchUserController(TwitchUserService service, TwitchUserResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<TwitchUserResource>> getTwitchUsers(
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getTwitchUsers(sortBy, sortDirection, limit)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TwitchUserResource> getTwitchUser(@PathVariable("id") long id) {
    return service.getTwitchUser(id).map(twitchUser -> ResponseEntity.ok(resourceAssembler.assemble(twitchUser))).orElse(ResponseEntity.notFound().build());
  }
}
