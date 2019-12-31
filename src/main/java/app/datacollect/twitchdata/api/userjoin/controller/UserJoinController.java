package app.datacollect.twitchdata.api.userjoin.controller;

import app.datacollect.twitchdata.api.userjoin.assembler.UserJoinResourceAssembler;
import app.datacollect.twitchdata.api.userjoin.resource.UserJoinResource;
import app.datacollect.twitchdata.api.userjoin.service.UserJoinService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-join")
public class UserJoinController {

  private final UserJoinService service;
  private final UserJoinResourceAssembler resourceAssembler;

  public UserJoinController(UserJoinService service, UserJoinResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<UserJoinResource>> getAll(
      @RequestParam("username") Optional<String> username,
      @RequestParam("channel") Optional<String> channel) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getAll(username, channel)));
  }
}
