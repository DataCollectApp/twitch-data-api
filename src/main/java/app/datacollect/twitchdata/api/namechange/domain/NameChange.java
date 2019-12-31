package app.datacollect.twitchdata.api.namechange.domain;

import app.datacollect.time.UTCDateTime;
import java.util.Optional;
import java.util.UUID;

public class NameChange {

  private final UUID id;
  private final long userId;
  private final Optional<String> oldUsername;
  private final String newUsername;
  private final UTCDateTime discoveredTime;
  private final String discoveredChannel;

  public NameChange(
      UUID id,
      long userId,
      Optional<String> oldUsername,
      String newUsername,
      UTCDateTime discoveredTime,
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

  public Optional<String> getOldUsername() {
    return oldUsername;
  }

  public String getNewUsername() {
    return newUsername;
  }

  public UTCDateTime getDiscoveredTime() {
    return discoveredTime;
  }

  public String getDiscoveredChannel() {
    return discoveredChannel;
  }
}
