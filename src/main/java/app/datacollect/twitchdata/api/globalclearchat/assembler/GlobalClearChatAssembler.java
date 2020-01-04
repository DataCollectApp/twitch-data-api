package app.datacollect.twitchdata.api.globalclearchat.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.globalclearchat.domain.GlobalClearChat;
import app.datacollect.twitchdata.feed.events.globalclearchat.v1.GlobalClearChatEventV1;
import org.springframework.stereotype.Component;

@Component
public class GlobalClearChatAssembler {

  public GlobalClearChat assemble(GlobalClearChatEventV1 event) {
    return new GlobalClearChat(
        event.getId(), event.getChannel(), event.getRoomId(), UTCDateTime.of(event.getTime()));
  }
}
