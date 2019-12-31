package app.datacollect.twitchdata.api.profile.service;

import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import app.datacollect.twitchdata.api.namechange.service.NameChangeService;
import app.datacollect.twitchdata.api.profile.domain.Profile;
import app.datacollect.twitchdata.api.twitchuser.service.TwitchUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private final TwitchUserService twitchUserService;
  private final NameChangeService nameChangeService;

  public ProfileService(TwitchUserService twitchUserService, NameChangeService nameChangeService) {
    this.twitchUserService = twitchUserService;
    this.nameChangeService = nameChangeService;
  }

  public Optional<Profile> getProfile(long userId) {
    return twitchUserService
        .getTwitchUser(userId)
        .map(
            twitchUser -> {
              final List<NameChange> nameChanges =
                  nameChangeService.getNameChanges(twitchUser.getId());
              return new Profile(twitchUser, nameChanges, nameChanges.size());
            });
  }
}
