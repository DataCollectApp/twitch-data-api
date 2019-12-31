package app.datacollect.twitchdata.api.invalidtwitchuser.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.invalidtwitchuser.domain.InvalidTwitchUser;
import app.datacollect.twitchdata.feed.events.chatmessage.v1.ChatMessageEventV1;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class InvalidTwitchUserAssembler {

  public InvalidTwitchUser assemble(ChatMessageEventV1 event) {
    return new InvalidTwitchUser(
        UUID.randomUUID(),
        event.getUserId(),
        event.getUsername(),
        UTCDateTime.of(event.getTime()),
        event.getChannel());
  }
}
