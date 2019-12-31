package app.datacollect.twitchdata.api.common.rest.sort;

import java.util.Arrays;
import org.springframework.core.convert.converter.Converter;

public class StringToSortByConverter implements Converter<String, SortBy> {

  @Override
  public SortBy convert(String source) {
    return Arrays.stream(SortBy.values()).filter(value -> value.getResourceName().equals(source)).findFirst().orElseThrow(IllegalArgumentException::new);
  }
}
