package app.datacollect.twitchdata.api.invalidtwitchuser.service;

import app.datacollect.twitchdata.api.invalidtwitchuser.domain.InvalidTwitchUser;
import app.datacollect.twitchdata.api.invalidtwitchuser.repository.InvalidTwitchUserRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InvalidTwitchUserService {

  private static final Logger logger = LoggerFactory.getLogger(InvalidTwitchUserService.class);

  private final InvalidTwitchUserRepository repository;

  public InvalidTwitchUserService(InvalidTwitchUserRepository repository) {
    this.repository = repository;
  }

  public void saveInvalidTwitchUser(InvalidTwitchUser invalidTwitchUser) {
    logger.debug(
        "Saving invalid twitch user with id '{}' and username '{}'",
        invalidTwitchUser.getId(),
        invalidTwitchUser.getUsername());
    repository.saveInvalidTwitchUser(invalidTwitchUser);
    logger.info(
        "Saved invalid twitch user with id '{}' and username '{}'",
        invalidTwitchUser.getId(),
        invalidTwitchUser.getUsername());
  }

  public Optional<InvalidTwitchUser> getInvalidTwitchUser(UUID id) {
    return repository.getInvalidTwitchUser(id);
  }
}
