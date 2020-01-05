package app.datacollect.twitchdata.api.twitchuser.resource;

import java.util.List;

public class TwitchUserSearch {
  private final List<Long> userIds;

  public TwitchUserSearch(List<Long> userIds) {
    this.userIds = userIds;
  }

  public List<Long> getUserIds() {
    return userIds;
  }
}
