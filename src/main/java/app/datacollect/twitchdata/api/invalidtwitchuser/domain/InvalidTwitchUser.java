package app.datacollect.twitchdata.api.invalidtwitchuser.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class InvalidTwitchUser {
  private final UUID id;
  private final long invalidId;
  private final String username;
  private final UTCDateTime discoveredTime;
  private final String discoveredChannel;

  public InvalidTwitchUser(
      UUID id,
      long invalidId,
      String username,
      UTCDateTime discoveredTime,
      String discoveredChannel) {
    this.id = id;
    this.invalidId = invalidId;
    this.username = username;
    this.discoveredTime = discoveredTime;
    this.discoveredChannel = discoveredChannel;
  }

  public UUID getId() {
    return id;
  }

  public long getInvalidId() {
    return invalidId;
  }

  public String getUsername() {
    return username;
  }

  public UTCDateTime getDiscoveredTime() {
    return discoveredTime;
  }

  public String getDiscoveredChannel() {
    return discoveredChannel;
  }
}
