package app.datacollect.twitchdata.api.chatmessage.service;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.chatmessage.domain.ChatMessage;
import app.datacollect.twitchdata.api.chatmessage.repository.ChatMessageRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageService {

  private final ChatMessageRepository repository;

  public ChatMessageService(ChatMessageRepository repository) {
    this.repository = repository;
  }

  public void insert(ChatMessage chatMessage) {
    repository.insert(chatMessage);
  }

  public List<ChatMessage> getAll(
      Optional<String> username,
      Optional<String> channel,
      Optional<UTCDateTime> fromDateTime,
      Optional<UTCDateTime> toDateTime) {
    if (username.isEmpty() && channel.isEmpty()) {
      throw new IllegalArgumentException("Username and/or channel query parameters is required");
    }
    if (fromDateTime.isPresent() && toDateTime.isPresent()) {
      return repository.getAllBetweenTimes(username, channel, fromDateTime.get(), toDateTime.get());
    } else if (fromDateTime.isPresent()) {
      return repository.getAllAfterTime(username, channel, fromDateTime.get());
    } else if (toDateTime.isPresent()) {
      return repository.getAllBeforeTime(username, channel, toDateTime.get());
    } else {
      return repository.getAll(username, channel);
    }
  }

  public List<ChatMessage> getAllByUsername(String username) {
    return repository.getAllByUsername(username);
  }
}
