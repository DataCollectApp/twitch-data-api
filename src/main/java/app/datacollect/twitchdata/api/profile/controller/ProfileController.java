package app.datacollect.twitchdata.api.profile.controller;

import app.datacollect.twitchdata.api.profile.assembler.ProfileResourceAssembler;
import app.datacollect.twitchdata.api.profile.resource.ProfileResource;
import app.datacollect.twitchdata.api.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
public class ProfileController {

  private final ProfileService service;
  private final ProfileResourceAssembler resourceAssembler;

  public ProfileController(ProfileService service, ProfileResourceAssembler resourceAssembler) {
    this.service = service;
    this.resourceAssembler = resourceAssembler;
  }

  @GetMapping("/{user-id}")
  public ResponseEntity<ProfileResource> getProfile(@PathVariable("user-id") long userId) {
    return service
        .getProfile(userId)
        .map(profile -> ResponseEntity.ok(resourceAssembler.assemble(profile)))
        .orElse(ResponseEntity.notFound().build());
  }
}
