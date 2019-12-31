package app.datacollect.twitchdata.api.chatmessage.resource;

public class ChatMessageResource {

  private final String id;
  private final String username;
  private final String message;
  private final String channel;
  private final long userId;
  private final long roomId;
  private final String time;

  public ChatMessageResource(
      String id,
      String username,
      String message,
      String channel,
      long userId,
      long roomId,
      String time) {
    this.id = id;
    this.username = username;
    this.message = message;
    this.channel = channel;
    this.userId = userId;
    this.roomId = roomId;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getMessage() {
    return message;
  }

  public String getChannel() {
    return channel;
  }

  public long getUserId() {
    return userId;
  }

  public long getRoomId() {
    return roomId;
  }

  public String getTime() {
    return time;
  }
}
