package app.datacollect.twitchdata.api.namechange.controller;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.common.validation.ErrorMessage;
import app.datacollect.twitchdata.api.common.validation.RequestParamValidator;
import app.datacollect.twitchdata.api.namechange.assembler.NameChangeResourceAssembler;
import app.datacollect.twitchdata.api.namechange.resource.NameChangeResource;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import java.util.List;
import java.util.Optional;
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
  private final RequestParamValidator requestParamValidator;

  public NameChangeController(
      NameChangeService service, NameChangeResourceAssembler resourceAssembler, RequestParamValidator requestParamValidator) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
    this.requestParamValidator = requestParamValidator;
  }

  @GetMapping
  public ResponseEntity<?> getNameChanges(
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit,
      @RequestParam(value = "excludeOrigin", defaultValue = "false") boolean excludeOrigin) {
    final Optional<ErrorMessage> errorMessage = requestParamValidator.validateLimit(limit);
    if (errorMessage.isPresent()) {
      return ResponseEntity.badRequest().body(errorMessage.get());
    }
    return ResponseEntity.ok(
        resourceAssembler.assemble(
            service.getNameChanges(sortBy, sortDirection, limit, excludeOrigin)));
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<List<NameChangeResource>> getNameChangesByUserId(
      @PathVariable("user-id") long userId,
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "excludeOrigin", defaultValue = "false") boolean excludeOrigin) {
    return ResponseEntity.ok(
        resourceAssembler.assemble(
            service.getNameChangesByUserId(userId, sortBy, sortDirection, excludeOrigin)));
  }
}
