package app.datacollect.twitchdata.api.clearchat.service;

import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import app.datacollect.twitchdata.api.clearchat.repository.ClearChatRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClearChatService {

  private static final Logger logger = LoggerFactory.getLogger(ClearChatService.class);

  private final ClearChatRepository repository;

  public ClearChatService(ClearChatRepository repository) {
    this.repository = repository;
  }

  public void insert(ClearChat clearChat) {
    logger.debug(
        "Saving clear chat with user id '{}' and username '{}'",
        clearChat.getTargetUserId(),
        clearChat.getTargetUsername());
    repository.insert(clearChat);
    logger.info(
        "Saved clear chat with user id '{}' and username '{}'",
        clearChat.getTargetUserId(),
        clearChat.getTargetUsername());
  }

  public List<ClearChat> getAll(Optional<String> targetUsername, Optional<String> channel) {
    return repository.getAll(targetUsername, channel);
  }
}
