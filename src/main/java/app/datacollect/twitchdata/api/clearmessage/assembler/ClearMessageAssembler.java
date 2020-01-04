package app.datacollect.twitchdata.api.clearmessage.assembler;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.clearmessage.domain.ClearMessage;
import app.datacollect.twitchdata.feed.events.clearmessage.v1.ClearMessageEventV1;
import org.springframework.stereotype.Component;

@Component
public class ClearMessageAssembler {

  public ClearMessage assemble(ClearMessageEventV1 event, long userId) {
    return new ClearMessage(
        event.getId(),
        event.getTargetUsername(),
        event.getChannel(),
        event.getMessage(),
        userId,
        UTCDateTime.of(event.getTime()));
  }
}
