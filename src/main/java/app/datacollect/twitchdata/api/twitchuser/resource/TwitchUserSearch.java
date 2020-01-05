package app.datacollect.twitchdata.api.twitchuser.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;

public class TwitchUserSearch {
  private final List<Long> userIds;

  @JsonCreator
  public TwitchUserSearch(List<Long> userIds) {
    this.userIds = userIds;
  }

  public List<Long> getUserIds() {
    return userIds;
  }
}
