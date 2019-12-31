package app.datacollect.twitchdata.api.userleave.service;

import app.datacollect.twitchdata.api.userleave.domain.UserLeave;
import app.datacollect.twitchdata.api.userleave.repository.UserLeaveRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserLeaveService {

  private final UserLeaveRepository repository;

  public UserLeaveService(UserLeaveRepository repository) {
    this.repository = repository;
  }

  public void insert(UserLeave userLeave) {
    repository.insert(userLeave);
  }

  public List<UserLeave> getAll(Optional<String> username, Optional<String> channel) {
    if (username.isEmpty() && channel.isEmpty()) {
      throw new IllegalArgumentException(
          "Unfiltered GET for user leave has been restricted due to performance issues.");
    }
    return repository.getAll(username, channel);
  }
}
