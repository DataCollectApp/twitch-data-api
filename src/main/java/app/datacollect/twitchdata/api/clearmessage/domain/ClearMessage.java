package app.datacollect.twitchdata.api.clearmessage.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class ClearMessage {
  private final UUID id;
  private final String targetUsername;
  private final String channel;
  private final String message;
  private final long userId;
  private final UTCDateTime time;

  public ClearMessage(
      UUID id,
      String targetUsername,
      String channel,
      String message,
      long userId,
      UTCDateTime time) {
    this.id = id;
    this.targetUsername = targetUsername;
    this.channel = channel;
    this.message = message;
    this.userId = userId;
    this.time = time;
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

  public long getUserId() {
    return userId;
  }

  public UTCDateTime getTime() {
    return time;
  }
}
