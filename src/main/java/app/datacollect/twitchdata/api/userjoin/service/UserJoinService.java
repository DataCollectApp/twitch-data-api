package app.datacollect.twitchdata.api.userjoin.service;

import app.datacollect.twitchdata.api.userjoin.domain.UserJoin;
import app.datacollect.twitchdata.api.userjoin.repository.UserJoinRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {

  private final UserJoinRepository repository;

  public UserJoinService(UserJoinRepository repository) {
    this.repository = repository;
  }

  public void insert(UserJoin userJoin) {
    repository.insert(userJoin);
  }

  public List<UserJoin> getAll(Optional<String> username, Optional<String> channel) {
    if (username.isEmpty() && channel.isEmpty()) {
      throw new IllegalArgumentException(
          "Unfiltered GET for user join has been restricted due to performance issues.");
    }
    return repository.getAll(username, channel);
  }
}
