package app.datacollect.twitchdata.api.namechange.service;

import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import app.datacollect.twitchdata.api.namechange.repository.NameChangeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NameChangeService {

  private static final Logger logger = LoggerFactory.getLogger(NameChangeService.class);

  private final NameChangeRepository repository;

  public NameChangeService(NameChangeRepository repository) {
    this.repository = repository;
  }

  public void saveNameChange(NameChange nameChange) {
    logger.debug(
        "Saving name change from username '{}' to '{}' for user id '{}'",
        nameChange.getOldUsername().orElse(null),
        nameChange.getNewUsername(),
        nameChange.getUserId());
    repository.saveNameChange(nameChange);
    logger.info(
        "Saved name change from username '{}' to '{}' for user id '{}'",
        nameChange.getOldUsername().orElse(null),
        nameChange.getNewUsername(),
        nameChange.getUserId());
  }

  public Optional<NameChange> getNameChange(UUID id) {
    return repository.getNameChange(id);
  }

  public List<NameChange> getNameChanges(long userId) {
    return repository.getNameChanges(userId);
  }

  public List<NameChange> getNameChanges(SortBy sortBy, SortDirection sortDirection, int limit) {
    return repository.getNameChanges(sortBy, sortDirection, limit);
  }

  public List<NameChange> getNameChangesByUserId(long userId, SortBy sortBy, SortDirection sortDirection) {
    return repository.getNameChangesByUserId(userId, sortBy, sortDirection);
  }
}
