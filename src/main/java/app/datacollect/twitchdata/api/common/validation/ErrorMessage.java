package app.datacollect.twitchdata.api.common.validation;

public class ErrorMessage {
  private final String key;
  private final String message;
  private final String time;

  public ErrorMessage(String key, String message, String time) {
    this.key = key;
    this.message = message;
    this.time = time;
  }

  public String getKey() {
    return key;
  }

  public String getMessage() {
    return message;
  }

  public String getTime() {
    return time;
  }
}
