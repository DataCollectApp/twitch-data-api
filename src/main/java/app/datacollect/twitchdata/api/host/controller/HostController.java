package app.datacollect.twitchdata.api.host.controller;

import app.datacollect.twitchdata.api.host.assembler.HostResourceAssembler;
import app.datacollect.twitchdata.api.host.resource.HostResource;
import app.datacollect.twitchdata.api.host.service.HostService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("host")
public class HostController {

  private final HostService service;
  private final HostResourceAssembler resourceAssembler;

  public HostController(HostService service, HostResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<HostResource>> getAll(
      @RequestParam("channel") Optional<String> channel,
      @RequestParam("targetChannel") Optional<String> targetChannel) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getAll(channel, targetChannel)));
  }
}
