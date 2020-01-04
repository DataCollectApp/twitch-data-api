package app.datacollect.twitchdata.api.clearchat.resource;

public class ClearChatResource {
  private final String id;
  private final String targetUsername;
  private final long targetUserId;
  private final String channel;
  private final long roomId;
  private final long seconds;
  private final String time;

  public ClearChatResource(
      String id,
      String targetUsername,
      long targetUserId,
      String channel,
      long roomId,
      long seconds,
      String time) {
    this.id = id;
    this.targetUsername = targetUsername;
    this.targetUserId = targetUserId;
    this.channel = channel;
    this.roomId = roomId;
    this.seconds = seconds;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getTargetUsername() {
    return targetUsername;
  }

  public long getTargetUserId() {
    return targetUserId;
  }

  public String getChannel() {
    return channel;
  }

  public long getRoomId() {
    return roomId;
  }

  public long getSeconds() {
    return seconds;
  }

  public String getTime() {
    return time;
  }
}
