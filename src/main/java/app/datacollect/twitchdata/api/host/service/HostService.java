package app.datacollect.twitchdata.api.host.service;

import app.datacollect.twitchdata.api.host.domain.Host;
import app.datacollect.twitchdata.api.host.repository.HostRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class HostService {

  private final HostRepository repository;

  public HostService(HostRepository repository) {
    this.repository = repository;
  }

  public void insert(Host host) {
    repository.insert(host);
  }

  public List<Host> getAll(Optional<String> channel, Optional<String> targetChannel) {
    return repository.getAll(channel, targetChannel);
  }
}
