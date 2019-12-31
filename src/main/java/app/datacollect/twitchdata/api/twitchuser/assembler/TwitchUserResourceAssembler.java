package app.datacollect.twitchdata.api.twitchuser.assembler;

import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import app.datacollect.twitchdata.api.twitchuser.resource.TwitchUserResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TwitchUserResourceAssembler {

  public TwitchUserResource assemble(TwitchUser twitchUser) {
    return new TwitchUserResource(
        twitchUser.getId(),
        twitchUser.getUsername(),
        twitchUser.getDisplayName(),
        twitchUser.getDiscoveredTime().iso8601(),
        twitchUser.getDiscoveredChannel());
  }

  public List<TwitchUserResource> assemble(List<TwitchUser> twitchUsers) {
    return twitchUsers.stream().map(this::assemble).collect(Collectors.toList());
  }
}
