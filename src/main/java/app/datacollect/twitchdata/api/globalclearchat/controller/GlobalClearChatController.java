package app.datacollect.twitchdata.api.globalclearchat.controller;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.common.validation.ErrorMessage;
import app.datacollect.twitchdata.api.common.validation.RequestParamValidator;
import app.datacollect.twitchdata.api.globalclearchat.assembler.GlobalClearChatResourceAssembler;
import app.datacollect.twitchdata.api.globalclearchat.service.GlobalClearChatService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("global-clear-chat")
public class GlobalClearChatController {

  private final GlobalClearChatService service;
  private final GlobalClearChatResourceAssembler resourceAssembler;
  private final RequestParamValidator requestParamValidator;

  public GlobalClearChatController(
      GlobalClearChatService service,
      GlobalClearChatResourceAssembler resourceAssembler,
      RequestParamValidator requestParamValidator) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
    this.requestParamValidator = requestParamValidator;
  }

  @GetMapping
  public ResponseEntity<?> getAll(
      @RequestParam(value = "sortBy", defaultValue = "discoveredTime") SortBy sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(value = "limit", defaultValue = "100") int limit) {
    final Optional<ErrorMessage> errorMessage = requestParamValidator.validateLimit(limit);
    if (errorMessage.isPresent()) {
      return ResponseEntity.badRequest().body(errorMessage.get());
    }
    return ResponseEntity.ok(
        resourceAssembler.assemble(service.getAll(sortBy, sortDirection, limit)));
  }
}
