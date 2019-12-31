package app.datacollect.twitchdata.api.clearchat.assembler;

import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import app.datacollect.twitchdata.api.clearchat.resource.ClearChatResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ClearChatResourceAssembler {

  public ClearChatResource assemble(ClearChat clearChat) {
    return new ClearChatResource(
        clearChat.getId().toString(),
        clearChat.getTargetUsername(),
        clearChat.getTargetUserId(),
        clearChat.getChannel(),
        clearChat.getRoomId(),
        clearChat.getSeconds(),
        clearChat.getTime().iso8601());
  }

  public List<ClearChatResource> assemble(List<ClearChat> clearChatList) {
    return clearChatList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
