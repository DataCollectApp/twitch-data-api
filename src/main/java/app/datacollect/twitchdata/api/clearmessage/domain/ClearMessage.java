package app.datacollect.twitchdata.api.clearmessage.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class ClearMessage {
  private final UUID id;
  private final String targetUsername;
  private final String channel;
  private final String message;
  private final UTCDateTime time;

  public ClearMessage(
      UUID id, String targetUsername, String channel, String message, UTCDateTime time) {
    this.id = id;
    this.targetUsername = targetUsername;
    this.channel = channel;
    this.message = message;
    this.time = time;
  }

  public ClearMessage(String targetUsername, String channel, String message) {
    this.id = UUID.randomUUID();
    this.targetUsername = targetUsername;
    this.channel = channel;
    this.message = message;
    this.time = UTCDateTime.now();
  }

  public UUID getId() {
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

  public UTCDateTime getTime() {
    return time;
  }
}
