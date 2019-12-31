package app.datacollect.twitchdata.api.chatmessage.assembler;

import app.datacollect.twitchdata.api.chatmessage.domain.ChatMessage;
import app.datacollect.twitchdata.api.chatmessage.resource.ChatMessageResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageResourceAssembler {

  public ChatMessageResource assemble(ChatMessage chatMessage) {
    return new ChatMessageResource(
        chatMessage.getId().toString(),
        chatMessage.getUsername(),
        chatMessage.getMessage(),
        chatMessage.getChannel(),
        chatMessage.getUserId(),
        chatMessage.getRoomId(),
        chatMessage.getTime().iso8601());
  }

  public List<ChatMessageResource> assemble(List<ChatMessage> chatMessageList) {
    return chatMessageList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
