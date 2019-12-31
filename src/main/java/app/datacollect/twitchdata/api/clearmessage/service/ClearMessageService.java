package app.datacollect.twitchdata.api.clearmessage.service;

import app.datacollect.twitchdata.api.clearmessage.domain.ClearMessage;
import app.datacollect.twitchdata.api.clearmessage.repository.ClearMessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClearMessageService {

  private final ClearMessageRepository repository;

  public ClearMessageService(ClearMessageRepository repository) {
    this.repository = repository;
  }

  public void insert(ClearMessage clearMessage) {
    repository.insert(clearMessage);
  }

  public List<ClearMessage> getAll(Optional<String> targetUsername, Optional<String> channel) {
    return repository.getAll(targetUsername, channel);
  }
}
