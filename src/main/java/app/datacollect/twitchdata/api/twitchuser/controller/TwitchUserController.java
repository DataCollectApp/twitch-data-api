package app.datacollect.twitchdata.api.twitchuser.controller;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.common.validation.ErrorMessage;
import app.datacollect.twitchdata.api.common.validation.RequestParamValidator;
import app.datacollect.twitchdata.api.twitchuser.assembler.TwitchUserResourceAssembler;
import app.datacollect.twitchdata.api.twitchuser.resource.TwitchUserResource;
import app.datacollect.twitchdata.api.twitchuser.resource.TwitchUserSearch;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("twitch-user")
public class TwitchUserController {

  private final TwitchUserService service;
  private final TwitchUserResourceAssembler resourceAssembler;
  private final RequestParamValidator requestParamValidator;

  public TwitchUserController(TwitchUserService service, TwitchUserResourceAssembler resourceAssembler, RequestParamValidator requestParamValidator) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
    this.requestParamValidator = requestParamValidator;
  }

  @GetMapping
  public ResponseEntity<?> getTwitchUsers(
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    final Optional<ErrorMessage> errorMessage = requestParamValidator.validateLimit(limit);
    if (errorMessage.isPresent()) {
      return ResponseEntity.badRequest().body(errorMessage.get());
    }
    return ResponseEntity.ok(resourceAssembler.assemble(service.getTwitchUsers(sortBy, sortDirection, limit)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TwitchUserResource> getTwitchUser(@PathVariable("id") long id) {
    return service.getTwitchUser(id).map(twitchUser -> ResponseEntity.ok(resourceAssembler.assemble(twitchUser))).orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<TwitchUserResource> getTwitchUserByUsername(@RequestParam("username") String username) {
    return service.getTwitchUser(username).map(twitchUser -> ResponseEntity.ok(resourceAssembler.assemble(twitchUser))).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/search")
  public ResponseEntity<List<TwitchUserResource>> search(@RequestBody TwitchUserSearch twitchUserSearch) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getTwitchUsers(twitchUserSearch.getUserIds())));
  }
}
