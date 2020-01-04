package app.datacollect.twitchdata.api.clearmessage.service;

import app.datacollect.twitchdata.api.clearmessage.domain.ClearMessage;
import app.datacollect.twitchdata.api.clearmessage.repository.ClearMessageRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClearMessageService {

  private static final Logger logger = LoggerFactory.getLogger(ClearMessageService.class);

  private final ClearMessageRepository repository;

  public ClearMessageService(ClearMessageRepository repository) {
    this.repository = repository;
  }

  public void insert(ClearMessage clearMessage) {
    logger.debug(
        "Saving clear message with user id '{}' and username '{}'",
        clearMessage.getUserId(),
        clearMessage.getTargetUsername());
    repository.insert(clearMessage);
    logger.info(
        "Saved clear message with user id '{}' and username '{}'",
        clearMessage.getUserId(),
        clearMessage.getTargetUsername());
  }

  public List<ClearMessage> getAll(Optional<String> targetUsername, Optional<String> channel) {
    return repository.getAll(targetUsername, channel);
  }
}
