package app.datacollect.twitchdata.api.userleave.domain;

import app.datacollect.time.UTCDateTime;
import java.util.UUID;

public class UserLeave {

  private final UUID id;
  private final String username;
  private final String channel;
  private final UTCDateTime time;

  public UserLeave(UUID id, String username, String channel, UTCDateTime time) {
    this.id = id;
    this.username = username;
    this.channel = channel;
    this.time = time;
  }

  public UserLeave(String username, String channel) {
    this.id = UUID.randomUUID();
    this.username = username;
    this.channel = channel;
    this.time = UTCDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getChannel() {
    return channel;
  }

  public UTCDateTime getTime() {
    return time;
  }
}
