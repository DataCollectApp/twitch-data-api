package app.datacollect.twitchdata.api.chatmessage.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class ChatMessage {
  private final UUID id;
  private final String username;
  private final String message;
  private final String channel;
  private final long userId;
  private final long roomId;
  private final UTCDateTime time;

  public ChatMessage(
      UUID id,
      String username,
      String message,
      String channel,
      long userId,
      long roomId,
      UTCDateTime time) {
    this.id = id;
    this.username = username;
    this.message = message;
    this.channel = channel;
    this.userId = userId;
    this.roomId = roomId;
    this.time = time;
  }

  public ChatMessage(String username, String message, String channel, long userId, long roomId) {
    this.id = UUID.randomUUID();
    this.username = username;
    this.message = message;
    this.channel = channel;
    this.userId = userId;
    this.roomId = roomId;
    this.time = UTCDateTime.now();
  }

  public UUID getId() {
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

  public UTCDateTime getTime() {
    return time;
  }
}
