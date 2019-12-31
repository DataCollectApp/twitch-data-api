package app.datacollect.twitchdata.api.profile.resource;

import app.datacollect.twitchdata.api.namechange.resource.NameChangeResource;
import app.datacollect.twitchdata.api.twitchuser.resource.TwitchUserResource;
import java.util.List;

public class ProfileResource {

  private final TwitchUserResource twitchUser;
  private final List<NameChangeResource> nameChanges;
  private final int nameChangeCount;

  public ProfileResource(
      TwitchUserResource twitchUser, List<NameChangeResource> nameChanges, int nameChangeCount) {
    this.twitchUser = twitchUser;
    this.nameChanges = nameChanges;
    this.nameChangeCount = nameChangeCount;
  }

  public TwitchUserResource getTwitchUser() {
    return twitchUser;
  }

  public List<NameChangeResource> getNameChanges() {
    return nameChanges;
  }

  public int getNameChangeCount() {
    return nameChangeCount;
  }
}
