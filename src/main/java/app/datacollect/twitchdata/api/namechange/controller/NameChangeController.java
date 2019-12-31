package app.datacollect.twitchdata.api.namechange.controller;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.namechange.assembler.NameChangeResourceAssembler;
import app.datacollect.twitchdata.api.namechange.resource.NameChangeResource;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("name-change")
public class NameChangeController {

  private final NameChangeService service;
  private final NameChangeResourceAssembler resourceAssembler;

  public NameChangeController(NameChangeService service, NameChangeResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping
  public ResponseEntity<List<NameChangeResource>> getNameChanges(
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getNameChanges(sortBy, sortDirection, limit)));
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<List<NameChangeResource>> getNameChangesByUserId(
      @PathVariable("user-id") long userId,
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection) {
    return ResponseEntity.ok(resourceAssembler.assemble(service.getNameChangesByUserId(userId, sortBy, sortDirection)));
  }
}
