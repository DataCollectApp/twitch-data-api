package app.datacollect.twitchdata.api.namechange.resource;

import java.util.UUID;

public class NameChangeResource {

  private final UUID id;
  private final long userId;
  private final String oldUsername;
  private final String newUsername;
  private final String discoveredTime;
  private final String discoveredChannel;

  public NameChangeResource(
      UUID id,
      long userId,
      String oldUsername,
      String newUsername,
      String discoveredTime,
      String discoveredChannel) {
    this.id = id;
    this.userId = userId;
    this.oldUsername = oldUsername;
    this.newUsername = newUsername;
    this.discoveredTime = discoveredTime;
    this.discoveredChannel = discoveredChannel;
  }

  public UUID getId() {
    return id;
  }

  public long getUserId() {
    return userId;
  }

  public String getOldUsername() {
    return oldUsername;
  }

  public String getNewUsername() {
    return newUsername;
  }

  public String getDiscoveredTime() {
    return discoveredTime;
  }

  public String getDiscoveredChannel() {
    return discoveredChannel;
  }
}
