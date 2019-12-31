package app.datacollect.twitchdata.api.clearmessage.controller;

import app.datacollect.twitchdata.api.clearmessage.assembler.ClearMessageResourceAssembler;
import app.datacollect.twitchdata.api.clearmessage.resource.ClearMessageResource;
import app.datacollect.twitchdata.api.clearmessage.service.ClearMessageService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clear-message")
public class ClearMessageController {

  private final ClearMessageService service;
  private final ClearMessageResourceAssembler resourceAssembler;

  public ClearMessageController(
      ClearMessageService service, ClearMessageResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<ClearMessageResource>> getAll(
      @RequestParam("targetUsername") Optional<String> targetUsername,
      @RequestParam("channel") Optional<String> channel) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getAll(targetUsername, channel)));
  }
}
