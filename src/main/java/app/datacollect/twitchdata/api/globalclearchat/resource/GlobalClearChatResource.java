package app.datacollect.twitchdata.api.globalclearchat.resource;

import java.util.UUID;

public class GlobalClearChatResource {

  private final UUID id;
  private final String channel;
  private final long roomId;
  private final String time;

  public GlobalClearChatResource(UUID id, String channel, long roomId, String time) {
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

  public String getTime() {
    return time;
  }
}
