package app.datacollect.twitchdata.api.namechange.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import app.datacollect.twitchdata.feed.events.chatmessage.v1.ChatMessageEventV1;
import app.datacollect.twitchdata.feed.events.clearchat.v1.ClearChatEventV1;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class NameChangeAssembler {

  public NameChange assemble(ChatMessageEventV1 event, Optional<String> oldUsername) {
    return new NameChange(
        UUID.randomUUID(),
        event.getUserId(),
        oldUsername,
        event.getUsername(),
        UTCDateTime.of(event.getTime()),
        event.getChannel());
  }

  public NameChange assemble(ClearChatEventV1 event, Optional<String> oldUsername) {
    return new NameChange(
        UUID.randomUUID(),
        Long.parseLong(event.getTargetUserId()),
        oldUsername,
        event.getTargetUsername(),
        UTCDateTime.of(event.getTime()),
        event.getChannel());
  }
}
