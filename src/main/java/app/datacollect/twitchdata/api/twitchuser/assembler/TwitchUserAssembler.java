package app.datacollect.twitchdata.api.twitchuser.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import app.datacollect.twitchdata.feed.events.chatmessage.v1.ChatMessageEventV1;
import app.datacollect.twitchdata.feed.events.clearchat.v1.ClearChatEventV1;
import org.springframework.stereotype.Component;

@Component
public class TwitchUserAssembler {

  public TwitchUser assemble(ChatMessageEventV1 event) {
    return new TwitchUser(
        event.getUserId(),
        event.getUsername(),
        event.getDisplayName(),
        UTCDateTime.of(event.getTime()),
        event.getChannel());
  }

  public TwitchUser assemble(ClearChatEventV1 event) {
    return new TwitchUser(
        Long.parseLong(event.getTargetUserId()),
        event.getTargetUsername(),
        event.getTargetUsername(),
        UTCDateTime.of(event.getTime()),
        event.getChannel());
  }
}
