package app.datacollect.twitchdata.api.clearchat.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.clearchat.domain.ClearChat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  public List<ClearChat> getAll(Optional<String> targetUsername, Optional<String> channel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, target_username, target_user_id, channel, room_id, seconds, time FROM clear_chat");
    if (targetUsername.isPresent() && channel.isPresent()) {
      sql.append(" WHERE target_username = :target_username AND channel = :channel");
      params.put("target_username", targetUsername.get());
      params.put("channel", channel.get());
    } else if (targetUsername.isPresent()) {
      sql.append(" WHERE target_username = :target_username");
      params.put("target_username", targetUsername.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    }
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
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
