package app.datacollect.twitchdata.api.common.validation;

import app.datacollect.time.UTCDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RequestParamValidator {

  public Optional<UTCDateTime> getValidDateTime(Optional<String> dateTimeString) {
    try {
      return dateTimeString.map(UTCDateTime::of);
    } catch (DateTimeParseException ex) {
      return Optional.empty();
    }
  }
}
