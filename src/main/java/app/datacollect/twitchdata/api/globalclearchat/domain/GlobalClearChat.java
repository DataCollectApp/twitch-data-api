package app.datacollect.twitchdata.api.globalclearchat.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class GlobalClearChat {

  private final UUID id;
  private final String channel;
  private final long roomId;
  private final UTCDateTime time;

  public GlobalClearChat(UUID id, String channel, long roomId, UTCDateTime time) {
    this.id = id;
    this.channel = channel;
    this.roomId = roomId;
    this.time = time;
  }

  public UUID getId() {
    return id;
  }

  public String getChannel() {
    return channel;
  }

  public long getRoomId() {
    return roomId;
  }

  public UTCDateTime getTime() {
    return time;
  }
}
