package app.datacollect.twitchdata.api.namechange.assembler;

import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import app.datacollect.twitchdata.api.namechange.resource.NameChangeResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NameChangeResourceAssembler {

  public NameChangeResource assemble(NameChange nameChange) {
    return new NameChangeResource(
        nameChange.getId(),
        nameChange.getUserId(),
        nameChange.getOldUsername().orElse(null),
        nameChange.getNewUsername(),
        nameChange.getDiscoveredTime().iso8601(),
        nameChange.getDiscoveredChannel());
  }

  public List<NameChangeResource> assemble(List<NameChange> nameChanges) {
    return nameChanges.stream().map(this::assemble).collect(Collectors.toList());
  }
}
