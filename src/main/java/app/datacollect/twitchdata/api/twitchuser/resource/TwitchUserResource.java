package app.datacollect.twitchdata.api.twitchuser.resource;

public class TwitchUserResource {

  private final long id;
  private final String username;
  private final String displayName;
  private final String discoveredTime;
  private final String discoveredChannel;

  public TwitchUserResource(
      long id,
      String username,
      String displayName,
      String discoveredTime,
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

  public String getDiscoveredTime() {
    return discoveredTime;
  }

  public String getDiscoveredChannel() {
    return discoveredChannel;
  }
}
