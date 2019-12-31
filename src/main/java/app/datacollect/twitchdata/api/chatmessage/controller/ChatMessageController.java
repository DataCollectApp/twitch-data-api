package app.datacollect.twitchdata.api.chatmessage.controller;

import app.datacollect.twitchdata.api.chatmessage.assembler.ChatMessageResourceAssembler;
import app.datacollect.twitchdata.api.chatmessage.domain.ChatMessage;
import app.datacollect.twitchdata.api.chatmessage.resource.ChatMessageResource;
import app.datacollect.twitchdata.api.chatmessage.service.ChatMessageService;
import app.datacollect.twitchdata.api.common.validation.RequestParamValidator;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat-message")
public class ChatMessageController {

  private final ChatMessageService service;
  private final ChatMessageResourceAssembler resourceAssembler;
  private final RequestParamValidator requestParamValidator;

  public ChatMessageController(
      ChatMessageService service,
      ChatMessageResourceAssembler resourceAssembler,
      RequestParamValidator requestParamValidator) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
    this.requestParamValidator = requestParamValidator;
  }

  @GetMapping
  public ResponseEntity<List<ChatMessageResource>> getAll(
      @RequestParam("username") Optional<String> username,
      @RequestParam("channel") Optional<String> channel,
      @RequestParam("fromDateTime") Optional<String> fromDateTime,
      @RequestParam("toDateTime") Optional<String> toDateTime) {
    final List<ChatMessage> chatMessages =
        service.getAll(
            username,
            channel,
            requestParamValidator.getValidDateTime(fromDateTime),
            requestParamValidator.getValidDateTime(toDateTime));
    return ResponseEntity.ok(resourceAssembler.assemble(chatMessages));
  }
}
