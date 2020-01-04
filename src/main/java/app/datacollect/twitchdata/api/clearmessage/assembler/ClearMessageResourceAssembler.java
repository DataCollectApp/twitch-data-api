package app.datacollect.twitchdata.api.clearmessage.assembler;

import app.datacollect.twitchdata.api.clearmessage.domain.ClearMessage;
import app.datacollect.twitchdata.api.clearmessage.resource.ClearMessageResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClearMessageResourceAssembler {

  public ClearMessageResource assemble(ClearMessage clearMessage) {
    return new ClearMessageResource(
        clearMessage.getId().toString(),
        clearMessage.getTargetUsername(),
        clearMessage.getChannel(),
        clearMessage.getMessage(),
        clearMessage.getUserId(),
        clearMessage.getTime().iso8601());
  }

  public List<ClearMessageResource> assemble(List<ClearMessage> clearMessageList) {
    return clearMessageList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
