package app.datacollect.twitchdata.api.clearchat.service;

import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import app.datacollect.twitchdata.api.clearchat.repository.ClearChatRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClearChatService {

  private final ClearChatRepository repository;

  public ClearChatService(ClearChatRepository repository) {
    this.repository = repository;
  }

  public void insert(ClearChat clearChat) {
    repository.insert(clearChat);
  }

  public List<ClearChat> getAll(Optional<String> targetUsername, Optional<String> channel) {
    return repository.getAll(targetUsername, channel);
  }
}
