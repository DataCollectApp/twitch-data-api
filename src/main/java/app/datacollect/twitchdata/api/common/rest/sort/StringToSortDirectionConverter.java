package app.datacollect.twitchdata.api.common.rest.sort;

import java.util.Arrays;
import org.springframework.core.convert.converter.Converter;

public class StringToSortDirectionConverter implements Converter<String, SortDirection> {

  @Override
  public SortDirection convert(String source) {
    return Arrays.stream(SortDirection.values()).filter(value -> value.getResourceName().equals(source)).findFirst().orElseThrow(IllegalArgumentException::new);
  }
}
