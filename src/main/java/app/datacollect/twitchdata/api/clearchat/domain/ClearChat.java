package app.datacollect.twitchdata.api.clearchat.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class ClearChat {
  private final UUID id;
  private final String targetUsername;
  private final String targetUserId;
  private final String channel;
  private final long roomId;
  private final long seconds;
  private final UTCDateTime time;

  public ClearChat(
      UUID id,
      String targetUsername,
      String targetUserId,
      String channel,
      long roomId,
      long seconds,
      UTCDateTime time) {
    this.id = id;
    this.targetUsername = targetUsername;
    this.targetUserId = targetUserId;
    this.channel = channel;
    this.roomId = roomId;
    this.seconds = seconds;
    this.time = time;
  }

  public ClearChat(
      String targetUsername, String targetUserId, String channel, long roomId, long seconds) {
    this.id = UUID.randomUUID();
    this.targetUsername = targetUsername;
    this.targetUserId = targetUserId;
    this.channel = channel;
    this.roomId = roomId;
    this.seconds = seconds;
    this.time = UTCDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getTargetUsername() {
    return targetUsername;
  }

  public String getTargetUserId() {
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

  public UTCDateTime getTime() {
    return time;
  }
}
