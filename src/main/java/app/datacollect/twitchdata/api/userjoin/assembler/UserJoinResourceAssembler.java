package app.datacollect.twitchdata.api.userjoin.assembler;

import app.datacollect.twitchdata.api.userjoin.domain.UserJoin;
import app.datacollect.twitchdata.api.userjoin.resource.UserJoinResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserJoinResourceAssembler {

  public UserJoinResource assemble(UserJoin userJoin) {
    return new UserJoinResource(
        userJoin.getId().toString(),
        userJoin.getUsername(),
        userJoin.getChannel(),
        userJoin.getTime().iso8601());
  }

  public List<UserJoinResource> assemble(List<UserJoin> userJoinList) {
    return userJoinList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
