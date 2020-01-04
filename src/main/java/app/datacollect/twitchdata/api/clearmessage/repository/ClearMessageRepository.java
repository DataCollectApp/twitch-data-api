package app.datacollect.twitchdata.api.clearmessage.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.clearmessage.domain.ClearMessage;
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
public class ClearMessageRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ClearMessageRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(ClearMessage clearMessage) {
    jdbcTemplate.update(
        "INSERT INTO clear_message(id, target_username, channel, message, user_id, time) VALUES (:id, :target_username, :channel, :message, :user_id, :time)",
        new MapSqlParameterSource()
            .addValue("id", clearMessage.getId())
            .addValue("target_username", clearMessage.getTargetUsername())
            .addValue("channel", clearMessage.getChannel())
            .addValue("message", clearMessage.getMessage())
            .addValue("user_id", clearMessage.getUserId())
            .addValue("time", clearMessage.getTime().iso8601()));
  }

  public List<ClearMessage> getAll(Optional<String> targetUsername, Optional<String> channel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, target_username, channel, message, user_id, time FROM clear_message");
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

  private ClearMessage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ClearMessage(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("target_username"),
        resultSet.getString("channel"),
        resultSet.getString("message"),
        resultSet.getLong("user_id"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
