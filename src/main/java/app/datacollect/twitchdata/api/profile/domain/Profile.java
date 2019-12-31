package app.datacollect.twitchdata.api.profile.domain;

import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import java.util.List;

public class Profile {

  private final TwitchUser twitchUser;
  private final List<NameChange> nameChanges;
  private final int nameChangeCount;

  public Profile(TwitchUser twitchUser, List<NameChange> nameChanges, int nameChangeCount) {
    this.twitchUser = twitchUser;
    this.nameChanges = nameChanges;
    this.nameChangeCount = nameChangeCount;
  }

  public TwitchUser getTwitchUser() {
    return twitchUser;
  }

  public List<NameChange> getNameChanges() {
    return nameChanges;
  }

  public int getNameChangeCount() {
    return nameChangeCount;
  }
}
