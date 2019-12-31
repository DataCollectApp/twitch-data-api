package app.datacollect.twitchdata.api.host.domain;

import app.datacollect.time.UTCDateTime;
import java.util.Optional;
import java.util.UUID;

public class Host {

  private final UUID id;
  private final String channel;
  private final Optional<String> targetChannel;
  private final UTCDateTime time;

  public Host(UUID id, String channel, Optional<String> targetChannel, UTCDateTime time) {
    this.id = id;
    this.channel = channel;
    this.targetChannel = targetChannel;
    this.time = time;
  }

  public Host(String channel, Optional<String> targetChannel) {
    this.id = UUID.randomUUID();
    this.channel = channel;
    this.targetChannel = targetChannel;
    this.time = UTCDateTime.now();
  }

  public UUID getId() {
    return id;
  }

  public String getChannel() {
    return channel;
  }

  public Optional<String> getTargetChannel() {
    return targetChannel;
  }

  public UTCDateTime getTime() {
    return time;
  }
}
