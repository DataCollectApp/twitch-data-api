package app.datacollect.twitchdata.api.profile.assembler;

import app.datacollect.twitchdata.api.namechange.assembler.NameChangeResourceAssembler;
import app.datacollect.twitchdata.api.profile.domain.Profile;
import app.datacollect.twitchdata.api.profile.resource.ProfileResource;
import app.datacollect.twitchdata.api.twitchuser.assembler.TwitchUserResourceAssembler;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ProfileResourceAssembler {

  private final TwitchUserResourceAssembler twitchUserResourceAssembler;
  private final NameChangeResourceAssembler nameChangeResourceAssembler;

  public ProfileResourceAssembler(
      TwitchUserResourceAssembler twitchUserResourceAssembler,
      NameChangeResourceAssembler nameChangeResourceAssembler) {
    this.twitchUserResourceAssembler = twitchUserResourceAssembler;
    this.nameChangeResourceAssembler = nameChangeResourceAssembler;
  }

  public ProfileResource assemble(Profile profile) {
    return new ProfileResource(
        twitchUserResourceAssembler.assemble(profile.getTwitchUser()),
        nameChangeResourceAssembler.assemble(profile.getNameChanges()),
        profile.getNameChangeCount());
  }

  public List<ProfileResource> assemble(List<Profile> profiles) {
    return profiles.stream().map(this::assemble).collect(Collectors.toList());
  }
}
