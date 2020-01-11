package app.datacollect.twitchdata.api.globalclearchat.assembler;

import app.datacollect.twitchdata.api.globalclearchat.domain.GlobalClearChat;
import app.datacollect.twitchdata.api.globalclearchat.resource.GlobalClearChatResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GlobalClearChatResourceAssembler {

  public GlobalClearChatResource assemble(GlobalClearChat globalClearChat) {
    return new GlobalClearChatResource(
        globalClearChat.getId(),
        globalClearChat.getChannel(),
        globalClearChat.getRoomId(),
        globalClearChat.getTime().iso8601());
  }

  public List<GlobalClearChatResource> assemble(List<GlobalClearChat> globalClearChatList) {
    return globalClearChatList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
