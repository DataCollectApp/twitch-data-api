package app.datacollect.twitchdata.api.clearchat.controller;

import app.datacollect.twitchdata.api.clearchat.assembler.ClearChatResourceAssembler;
import app.datacollect.twitchdata.api.clearchat.service.ClearChatService;
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
@RequestMapping("clear-chat")
public class ClearChatController {

  private final ClearChatService service;
  private final ClearChatResourceAssembler resourceAssembler;
  private final RequestParamValidator requestParamValidator;

  public ClearChatController(
      ClearChatService service,
      ClearChatResourceAssembler resourceAssembler,
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
      @RequestParam("targetUserId") Optional<Long> targetUserId) {
    final Optional<ErrorMessage> errorMessage = requestParamValidator.validateLimit(limit);
    if (errorMessage.isPresent()) {
      return ResponseEntity.badRequest().body(errorMessage.get());
    }
    final var nameChanges =
        targetUserId
            .map(t -> service.getAllByTargetUserId(sortBy, sortDirection, t))
            .orElse(service.getAll(sortBy, sortDirection, limit));
    return ResponseEntity.ok(resourceAssembler.assemble(nameChanges));
  }
}
