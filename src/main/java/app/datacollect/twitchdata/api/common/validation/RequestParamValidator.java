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

  public Optional<ErrorMessage> validateLimit(int limit) {
    if (limit < 0 || limit > 100) {
      return Optional.of(
          new ErrorMessage(
              "INVALID_LIMIT",
              "Limit must be a number between 0 and 100 (inclusive)",
              UTCDateTime.now().iso8601()));
    }
    return Optional.empty();
  }
}
