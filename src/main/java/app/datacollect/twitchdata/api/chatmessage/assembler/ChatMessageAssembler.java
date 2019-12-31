package app.datacollect.twitchdata.api.chatmessage.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.chatmessage.domain.ChatMessage;
import app.datacollect.twitchdata.feed.events.chatmessage.v1.ChatMessageEventV1;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageAssembler {

  public ChatMessage assemble(ChatMessageEventV1 event) {
    return new ChatMessage(
        event.getId(),
        event.getUsername(),
        event.getMessage(),
        event.getChannel(),
        event.getUserId(),
        event.getRoomId(),
        UTCDateTime.of(event.getTime()));
  }
}
