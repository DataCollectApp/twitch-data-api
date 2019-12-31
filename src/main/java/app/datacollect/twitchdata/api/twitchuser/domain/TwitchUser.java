package app.datacollect.twitchdata.api.twitchuser.domain;

import app.datacollect.time.UTCDateTime;

public class TwitchUser {
  private final long id;
  private final String username;
  private final String displayName;
  private final UTCDateTime discoveredTime;
  private final String discoveredChannel;

  public TwitchUser(
      long id,
      String username,
      String displayName,
      UTCDateTime discoveredTime,
      String discoveredChannel) {
    this.id = id;
    this.username = username;
    this.displayName = displayName;
    this.discoveredTime = discoveredTime;
    this.discoveredChannel = discoveredChannel;
  }

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getDisplayName() {
    return displayName;
  }

  public UTCDateTime getDiscoveredTime() {
    return discoveredTime;
  }

  public String getDiscoveredChannel() {
    return discoveredChannel;
  }
}
