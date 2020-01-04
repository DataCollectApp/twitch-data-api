package app.datacollect.twitchdata.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("feature-toggle")
public class FeatureToggle {

  private boolean shouldReadPunishments;

  public boolean getShouldReadPunishments() {
    return shouldReadPunishments;
  }

  public void setShouldReadPunishments(boolean shouldReadPunishments) {
    this.shouldReadPunishments = shouldReadPunishments;
  }
}
