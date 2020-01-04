package app.datacollect.twitchdata.api.clearchat.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import app.datacollect.twitchdata.feed.events.clearchat.v1.ClearChatEventV1;
import org.springframework.stereotype.Component;

@Component
public class ClearChatAssembler {

  public ClearChat assemble(ClearChatEventV1 event) {
    return new ClearChat(
        event.getId(),
        event.getTargetUsername(),
        event.getTargetUserId(),
        event.getChannel(),
        event.getRoomId(),
        event.getSeconds(),
        UTCDateTime.of(event.getTime()));
  }
}
