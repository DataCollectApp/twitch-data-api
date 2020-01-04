package app.datacollect.twitchdata.api.globalclearchat.service;

import app.datacollect.twitchdata.api.globalclearchat.domain.GlobalClearChat;
import app.datacollect.twitchdata.api.globalclearchat.repository.GlobalClearChatRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GlobalClearChatService {

  private static final Logger logger = LoggerFactory.getLogger(GlobalClearChatService.class);

  private final GlobalClearChatRepository repository;

  public GlobalClearChatService(GlobalClearChatRepository repository) {
    this.repository = repository;
  }

  public void insert(GlobalClearChat globalClearChat) {
    logger.debug(
        "Saving global clear chat with channel '{}' and room id '{}'",
        globalClearChat.getChannel(),
        globalClearChat.getRoomId());
    repository.insert(globalClearChat);
    logger.info(
        "Saved global clear chat with channel '{}' and room id '{}'",
        globalClearChat.getChannel(),
        globalClearChat.getRoomId());
  }

  public List<GlobalClearChat> getAll() {
    return repository.getAll();
  }
}
