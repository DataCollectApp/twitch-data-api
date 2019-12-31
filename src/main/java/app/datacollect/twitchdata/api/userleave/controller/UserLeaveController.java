package app.datacollect.twitchdata.api.userleave.controller;

import app.datacollect.twitchdata.api.userleave.assembler.UserLeaveResourceAssembler;
import app.datacollect.twitchdata.api.userleave.resource.UserLeaveResource;
import app.datacollect.twitchdata.api.userleave.service.UserLeaveService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user-leave")
public class UserLeaveController {

  private final UserLeaveService service;
  private final UserLeaveResourceAssembler resourceAssembler;

  public UserLeaveController(
      UserLeaveService service, UserLeaveResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<UserLeaveResource>> getAll(
      @RequestParam("username") Optional<String> username,
      @RequestParam("channel") Optional<String> channel) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getAll(username, channel)));
  }
}
