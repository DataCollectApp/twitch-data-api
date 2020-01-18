package app.datacollect.twitchdata.api.clearchat.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import app.datacollect.twitchdata.api.common.rest.sort.SortBy;
import app.datacollect.twitchdata.api.common.rest.sort.SortDirection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClearChatRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ClearChatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(ClearChat clearChat) {
    jdbcTemplate.update(
        "INSERT INTO clear_chat(id, target_username, target_user_id, channel, room_id, seconds, time) VALUES (:id, :target_username, :target_user_id, :channel, :room_id, :seconds, :time)",
        new MapSqlParameterSource()
            .addValue("id", clearChat.getId())
            .addValue("target_username", clearChat.getTargetUsername())
            .addValue("target_user_id", clearChat.getTargetUserId())
            .addValue("channel", clearChat.getChannel())
            .addValue("room_id", clearChat.getRoomId())
            .addValue("seconds", clearChat.getSeconds())
            .addValue("time", clearChat.getTime().iso8601()));
  }

  public List<ClearChat> getAll(SortBy sortBy, SortDirection sortDirection, int limit) {
    return jdbcTemplate.query(
        "SELECT id, target_username, target_user_id, channel, room_id, seconds, time "
            + "FROM clear_chat "
            + "ORDER BY " + sortBy.getDatabaseName() + " " + sortDirection.getDatabaseName() + " LIMIT " + limit,
        this::mapRow);
  }

  public List<ClearChat> getAllByTargetUserId(
      SortBy sortBy, SortDirection sortDirection, long targetUserId) {
    return jdbcTemplate.query(
        "SELECT id, target_username, target_user_id, channel, room_id, seconds, time "
            + "FROM clear_chat "
            + "WHERE target_user_id = :target_user_id "
            + "ORDER BY " + sortBy.getDatabaseName() + " " + sortDirection.getDatabaseName(),
        Map.of("target_user_id", targetUserId),
        this::mapRow);
  }

  public Integer getClearChatCount() {
    return jdbcTemplate.queryForObject("SELECT count(id) FROM clear_chat", Map.of(), Integer.class);
  }

  private ClearChat mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ClearChat(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("target_username"),
        resultSet.getLong("target_user_id"),
        resultSet.getString("channel"),
        resultSet.getLong("room_id"),
        resultSet.getLong("seconds"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
