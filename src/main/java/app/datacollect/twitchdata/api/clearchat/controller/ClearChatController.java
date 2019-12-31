package app.datacollect.twitchdata.api.clearchat.controller;

import app.datacollect.twitchdata.api.clearchat.assembler.ClearChatResourceAssembler;
import app.datacollect.twitchdata.api.clearchat.resource.ClearChatResource;
import app.datacollect.twitchdata.api.clearchat.service.ClearChatService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("clear-chat")
public class ClearChatController {

  private final ClearChatService service;
  private final ClearChatResourceAssembler resourceAssembler;

  public ClearChatController(
      ClearChatService service, ClearChatResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<ClearChatResource>> getAll(
      @RequestParam("targetUsername") Optional<String> targetUsername,
      @RequestParam("channel") Optional<String> channel) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getAll(targetUsername, channel)));
  }
}
