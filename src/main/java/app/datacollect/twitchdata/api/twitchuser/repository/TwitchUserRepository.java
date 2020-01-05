package app.datacollect.twitchdata.api.twitchuser.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.twitchuser.domain.TwitchUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TwitchUserRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public TwitchUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveTwitchUser(TwitchUser twitchUser) {
    jdbcTemplate.update(
        "INSERT INTO twitch_user(id, username, display_name, discovered_time, discovered_channel) VALUES (:id, :username, :display_name, :discovered_time, :discovered_channel)",
        Map.of(
            "id",
            twitchUser.getId(),
            "username",
            twitchUser.getUsername(),
            "display_name",
            twitchUser.getDisplayName(),
            "discovered_time",
            twitchUser.getDiscoveredTime().iso8601(),
            "discovered_channel",
            twitchUser.getDiscoveredChannel()));
  }

  public Optional<TwitchUser> getTwitchUser(long id) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "SELECT id, username, display_name, discovered_time, discovered_channel FROM twitch_user WHERE id = :id",
              Map.of("id", id),
              this::mapRow));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public Optional<TwitchUser> getTwitchUser(String username) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "SELECT id, username, display_name, discovered_time, discovered_channel FROM twitch_user WHERE username = :username",
              Map.of("username", username),
              this::mapRow));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public List<TwitchUser> getTwitchUsers() {
    return jdbcTemplate.query(
        "SELECT id, username, display_name, discovered_time, discovered_channel FROM twitch_user",
        this::mapRow);
  }

  public List<TwitchUser> getTwitchUsers(List<Long> userIds) {
    return jdbcTemplate.query(
        "SELECT id, username, display_name, discovered_time, discovered_channel FROM twitch_user WHERE id IN (:user_ids)",
        Map.of("user_ids", userIds),
        this::mapRow);
  }

  public List<TwitchUser> getTwitchUsers(SortBy sortBy, SortDirection sortDirection, int limit) {
    return jdbcTemplate.query(
        "SELECT id, username, display_name, discovered_time, discovered_channel "
            + "FROM twitch_user "
            + "ORDER BY " + sortBy.getDatabaseName() + " " + sortDirection.getDatabaseName() + " LIMIT " + limit,
        this::mapRow);
  }

  public void updateTwitchUser(long id, String username, String displayName) {
    jdbcTemplate.update(
        "UPDATE twitch_user SET username = :username, display_name = :display_name WHERE id = :id",
        Map.of("username", username, "id", id, "display_name", displayName));
  }

  private TwitchUser mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new TwitchUser(
        resultSet.getLong("id"),
        resultSet.getString("username"),
        resultSet.getString("display_name"),
        UTCDateTime.of(resultSet.getString("discovered_time")),
        resultSet.getString("discovered_channel"));
  }
}
