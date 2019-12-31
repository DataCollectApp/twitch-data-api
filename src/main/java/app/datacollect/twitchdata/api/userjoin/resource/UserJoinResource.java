package app.datacollect.twitchdata.api.userjoin.resource;

public class UserJoinResource {

  private final String id;
  private final String username;
  private final String channel;
  private final String time;

  public UserJoinResource(String id, String username, String channel, String time) {
    this.id = id;
    this.username = username;
    this.channel = channel;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getChannel() {
    return channel;
  }

  public String getTime() {
    return time;
  }
}
