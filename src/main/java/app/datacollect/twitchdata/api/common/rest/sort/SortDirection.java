package app.datacollect.twitchdata.api.common.rest.sort;

public enum SortDirection {
  ASCENDING("ASC", "ASC"),
  DESCENDING("DESC", "DESC");

  private final String resourceName;
  private final String databaseName;

  SortDirection(String resourceName, String databaseName) {
    this.resourceName = resourceName;
    this.databaseName = databaseName;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getDatabaseName() {
    return databaseName;
  }
}
