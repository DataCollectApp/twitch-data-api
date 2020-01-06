package app.datacollect.twitchdata.api.twitchuser.service;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import app.datacollect.twitchdata.api.twitchuser.repository.TwitchUserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwitchUserService {

  private static final Logger logger = LoggerFactory.getLogger(TwitchUserService.class);

  private final TwitchUserRepository repository;
  private final NameChangeService nameChangeService;

  public TwitchUserService(TwitchUserRepository repository, NameChangeService nameChangeService) {
    this.repository = repository;
    this.nameChangeService = nameChangeService;
  }

  public void saveTwitchUser(TwitchUser twitchUser) {
    logger.debug(
        "Saving twitch user with id '{}' and username '{}' ('{}')",
        twitchUser.getId(),
        twitchUser.getUsername(),
        twitchUser.getDisplayName());
    repository.saveTwitchUser(twitchUser);
    logger.info(
        "Saved twitch user with id '{}' and username '{}' ('{}')",
        twitchUser.getId(),
        twitchUser.getUsername(),
        twitchUser.getDisplayName());
  }

  public Optional<TwitchUser> getTwitchUser(long id) {
    return repository.getTwitchUser(id);
  }

  public Optional<TwitchUser> getTwitchUser(String username) {
    return repository.getTwitchUser(username);
  }

  public List<TwitchUser> getTwitchUsers() {
    return repository.getTwitchUsers();
  }

  public List<TwitchUser> getTwitchUsers(List<Long> userIds) {
    return repository.getTwitchUsers(userIds);
  }

  public List<TwitchUser> getTwitchUsers(SortBy sortBy, SortDirection sortDirection, int limit) {
    return repository.getTwitchUsers(sortBy, sortDirection, limit);
  }

  public void updateTwitchUser(long id, String username, String displayName) {
    logger.debug(
        "Updating username and display name to '{}' ('{}') for twitch user with id '{}'",
        username,
        displayName,
        id);
    repository.updateTwitchUser(id, username, displayName);
    logger.info(
        "Updated username and display name to '{}' ('{}') for twitch user with id '{}'",
        username,
        displayName,
        id);
  }
}
