package app.datacollect.twitchdata.api.userleave.resource;

public class UserLeaveResource {

  private final String id;
  private final String username;
  private final String channel;
  private final String time;

  public UserLeaveResource(String id, String username, String channel, String time) {
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
