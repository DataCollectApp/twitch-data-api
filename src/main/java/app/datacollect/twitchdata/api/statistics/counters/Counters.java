package app.datacollect.twitchdata.api.statistics.counters;

public class Counters {

  private final int twitchUserCount;
  private final int nameChangeCount;

  public Counters(int twitchUserCount, int nameChangeCount) {
    this.twitchUserCount = twitchUserCount;
    this.nameChangeCount = nameChangeCount;
  }

  public int getTwitchUserCount() {
    return twitchUserCount;
  }

  public int getNameChangeCount() {
    return nameChangeCount;
  }
}
