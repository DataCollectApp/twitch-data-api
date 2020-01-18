package app.datacollect.twitchdata.api.clearmessage.controller;

import app.datacollect.twitchdata.api.clearmessage.assembler.ClearMessageResourceAssembler;
import app.datacollect.twitchdata.api.clearmessage.service.ClearMessageService;
import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.common.validation.ErrorMessage;
import app.datacollect.twitchdata.api.common.validation.RequestParamValidator;
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
  private final RequestParamValidator requestParamValidator;

  public ClearMessageController(
      ClearMessageService service,
      ClearMessageResourceAssembler resourceAssembler,
      RequestParamValidator requestParamValidator) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
    this.requestParamValidator = requestParamValidator;
  }

  @GetMapping
  public ResponseEntity<?> getAll(
      @RequestParam(value = "sortBy", defaultValue = "time") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit,
      @RequestParam("userId") Optional<Long> userId) {
    final Optional<ErrorMessage> errorMessage = requestParamValidator.validateLimit(limit);
    if (errorMessage.isPresent()) {
      return ResponseEntity.badRequest().body(errorMessage.get());
    }
    final var nameChanges =
        userId
            .map(u -> service.getAllByUserId(sortBy, sortDirection, u))
            .orElse(service.getAll(sortBy, sortDirection, limit));
    return ResponseEntity.ok(resourceAssembler.assemble(nameChanges));
  }
}
