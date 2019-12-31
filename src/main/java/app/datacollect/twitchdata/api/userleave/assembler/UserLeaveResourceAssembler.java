package app.datacollect.twitchdata.api.userleave.assembler;

import app.datacollect.twitchdata.api.userleave.domain.UserLeave;
import app.datacollect.twitchdata.api.userleave.resource.UserLeaveResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserLeaveResourceAssembler {

  public UserLeaveResource assemble(UserLeave userLeave) {
    return new UserLeaveResource(
        userLeave.getId().toString(),
        userLeave.getUsername(),
        userLeave.getChannel(),
        userLeave.getTime().iso8601());
  }

  public List<UserLeaveResource> assemble(List<UserLeave> userLeaveList) {
    return userLeaveList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
