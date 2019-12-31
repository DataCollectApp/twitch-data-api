package app.datacollect.twitchdata.api.invalidtwitchuser.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.invalidtwitchuser.domain.InvalidTwitchUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvalidTwitchUserRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public InvalidTwitchUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveInvalidTwitchUser(InvalidTwitchUser invalidTwitchUser) {
    jdbcTemplate.update(
        "INSERT INTO invalid_twitch_user(id, invalid_id, username, discovered_time, discovered_channel) VALUES (:id, :invalid_id, :username, :discovered_time, :discovered_channel)",
        Map.of(
            "id",
            invalidTwitchUser.getId(),
            "invalid_id",
            invalidTwitchUser.getInvalidId(),
            "username",
            invalidTwitchUser.getUsername(),
            "discovered_time",
            invalidTwitchUser.getDiscoveredTime().iso8601(),
            "discovered_channel",
            invalidTwitchUser.getDiscoveredChannel()));
  }

  public Optional<InvalidTwitchUser> getInvalidTwitchUser(UUID id) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "SELECT id, invalid_id, username, discovered_time, discovered_channel FROM invalid_twitch_user WHERE id = :id",
              Map.of("id", id),
              this::mapRow));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  private InvalidTwitchUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new InvalidTwitchUser(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getLong("invalid_id"),
        resultSet.getString("username"),
        UTCDateTime.of(resultSet.getString("discovered_time")),
        resultSet.getString("discovered_channel"));
  }
}
