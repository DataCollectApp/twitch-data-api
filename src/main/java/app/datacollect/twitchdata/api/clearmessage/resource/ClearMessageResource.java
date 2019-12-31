package app.datacollect.twitchdata.api.clearmessage.resource;

public class ClearMessageResource {
  private final String id;
  private final String targetUsername;
  private final String channel;
  private final String message;
  private final String time;

  public ClearMessageResource(
      String id, String targetUsername, String channel, String message, String time) {
    this.id = id;
    this.targetUsername = targetUsername;
    this.channel = channel;
    this.message = message;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getTargetUsername() {
    return targetUsername;
  }

  public String getChannel() {
    return channel;
  }

  public String getMessage() {
    return message;
  }

  public String getTime() {
    return time;
  }
}
