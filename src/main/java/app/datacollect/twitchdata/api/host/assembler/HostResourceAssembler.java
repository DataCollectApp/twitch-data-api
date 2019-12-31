package app.datacollect.twitchdata.api.host.assembler;

import app.datacollect.twitchdata.api.host.domain.Host;
import app.datacollect.twitchdata.api.host.resource.HostResource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class HostResourceAssembler {

  public HostResource assemble(Host host) {
    return new HostResource(
        host.getId().toString(),
        host.getChannel(),
        host.getTargetChannel(),
        host.getTime().iso8601());
  }

  public List<HostResource> assemble(List<Host> hostList) {
    return hostList.stream().map(this::assemble).collect(Collectors.toList());
  }
}
