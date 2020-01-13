package app.datacollect.twitchdata.api.statistics.counters;

public class Counters {

  private final int twitchUserCount;
  private final int nameChangeCount;
  private final int clearChatCount;
  private final int globalClearChatCount;
  private final int clearMessageCount;

  public Counters(
      int twitchUserCount,
      int nameChangeCount,
      int clearChatCount,
      int globalClearChatCount,
      int clearMessageCount) {
    this.twitchUserCount = twitchUserCount;
    this.nameChangeCount = nameChangeCount;
    this.clearChatCount = clearChatCount;
    this.globalClearChatCount = globalClearChatCount;
    this.clearMessageCount = clearMessageCount;
  }

  public int getTwitchUserCount() {
    return twitchUserCount;
  }

  public int getNameChangeCount() {
    return nameChangeCount;
  }

  public int getClearChatCount() {
    return clearChatCount;
  }

  public int getGlobalClearChatCount() {
    return globalClearChatCount;
  }

  public int getClearMessageCount() {
    return clearMessageCount;
  }
}
