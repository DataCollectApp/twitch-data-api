package app.datacollect.twitchdata.api.host.resource;

import java.util.Optional;

public class HostResource {

  private final String id;
  private final String channel;
  private final Optional<String> targetChannel;
  private final String time;

  public HostResource(String id, String channel, Optional<String> targetChannel, String time) {
    this.id = id;
    this.channel = channel;
    this.targetChannel = targetChannel;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public String getChannel() {
    return channel;
  }

  public Optional<String> getTargetChannel() {
    return targetChannel;
  }

  public String getTime() {
    return time;
  }
}
