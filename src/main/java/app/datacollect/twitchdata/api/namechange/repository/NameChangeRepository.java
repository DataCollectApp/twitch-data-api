package app.datacollect.twitchdata.api.namechange.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import app.datacollect.twitchdata.api.namechange.domain.NameChange;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NameChangeRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public NameChangeRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void saveNameChange(NameChange nameChange) {
    jdbcTemplate.update(
        "INSERT INTO name_change(id, user_id, old_username, new_username, discovered_time, discovered_channel) "
            + "VALUES (:id, :user_id, :old_username, :new_username, :discovered_time, :discovered_channel)",
        new MapSqlParameterSource()
            .addValue("id", nameChange.getId())
            .addValue("user_id", nameChange.getUserId())
            .addValue("old_username", nameChange.getOldUsername().orElse(null))
            .addValue("new_username", nameChange.getNewUsername())
            .addValue("discovered_time", nameChange.getDiscoveredTime().iso8601())
            .addValue("discovered_channel", nameChange.getDiscoveredChannel()));
  }

  public Optional<NameChange> getNameChange(UUID id) {
    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "SELECT id, user_id, old_username, new_username, discovered_time, discovered_channel FROM name_change WHERE id = :id",
              Map.of("id", id),
              this::mapRow));
    } catch (EmptyResultDataAccessException ex) {
      return Optional.empty();
    }
  }

  public List<NameChange> getNameChanges(long userId) {
    return jdbcTemplate.query(
        "SELECT id, user_id, old_username, new_username, discovered_time, discovered_channel FROM name_change WHERE user_id = :user_id",
        Map.of("user_id", userId),
        this::mapRow);
  }

  public List<NameChange> getNameChangesByOldUsername(String oldUsername) {
    return jdbcTemplate.query(
        "SELECT id, user_id, old_username, new_username, discovered_time, discovered_channel FROM name_change WHERE old_username = :old_username",
        Map.of("old_username", oldUsername),
        this::mapRow);
  }

  public List<NameChange> getNameChanges(
      SortBy sortBy, SortDirection sortDirection, int limit, boolean excludeOrigin) {
    return jdbcTemplate.query(
        "SELECT id, user_id, old_username, new_username, discovered_time, discovered_channel "
            + "FROM name_change "
            + (excludeOrigin ? "WHERE old_username IS NOT NULL " : "")
            + "ORDER BY "
            + sortBy.getDatabaseName()
            + " "
            + sortDirection.getDatabaseName()
            + " LIMIT "
            + limit,
        this::mapRow);
  }

  public List<NameChange> getNameChangesByUserId(
      long userId, SortBy sortBy, SortDirection sortDirection, boolean excludeOrigin) {
    return jdbcTemplate.query(
        "SELECT id, user_id, old_username, new_username, discovered_time, discovered_channel "
            + "FROM name_change "
            + "WHERE user_id = :user_id "
            + (excludeOrigin ? "AND old_username IS NOT NULL " : "")
            + "ORDER BY "
            + sortBy.getDatabaseName()
            + " "
            + sortDirection.getDatabaseName(),
        Map.of("user_id", userId),
        this::mapRow);
  }

  private NameChange mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new NameChange(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getLong("user_id"),
        Optional.ofNullable(resultSet.getString("old_username")),
        resultSet.getString("new_username"),
        UTCDateTime.of(resultSet.getString("discovered_time")),
        resultSet.getString("discovered_channel"));
  }
}
