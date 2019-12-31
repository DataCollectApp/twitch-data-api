package app.datacollect.twitchdata.api.chatmessage.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.chatmessage.domain.ChatMessage;
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
public class ChatMessageRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ChatMessageRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(ChatMessage chatMessage) {
    jdbcTemplate.update(
        "INSERT INTO chat_message(id, username, message, channel, user_id, room_id, time) "
            + "VALUES (:id, :username, :message, :channel, :user_id, :room_id, :time)",
        new MapSqlParameterSource()
            .addValue("id", chatMessage.getId())
            .addValue("username", chatMessage.getUsername())
            .addValue("message", chatMessage.getMessage())
            .addValue("channel", chatMessage.getChannel())
            .addValue("user_id", chatMessage.getUserId())
            .addValue("room_id", chatMessage.getRoomId())
            .addValue("time", chatMessage.getTime().iso8601()));
  }

  public List<ChatMessage> getAllByUsername(String username) {
    return jdbcTemplate.query(
        "SELECT id, username, message, channel, user_id, room_id, time FROM chat_message WHERE username = :username ORDER BY time",
        Map.of("username", username),
        this::mapRow);
  }

  public List<ChatMessage> getAll(Optional<String> username, Optional<String> channel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, username, message, channel, user_id, room_id, time FROM chat_message");
    if (username.isPresent() && channel.isPresent()) {
      sql.append(" WHERE username = :username AND channel = :channel");
      params.put("username", username.get());
      params.put("channel", channel.get());
    } else if (username.isPresent()) {
      sql.append(" WHERE username = :username");
      params.put("username", username.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    }
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
  }

  public List<ChatMessage> getAllAfterTime(
      Optional<String> username, Optional<String> channel, UTCDateTime time) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, username, message, channel, user_id, room_id, time FROM chat_message");
    if (username.isPresent() && channel.isPresent()) {
      sql.append(" WHERE username = :username AND channel = :channel");
      params.put("username", username.get());
      params.put("channel", channel.get());
    } else if (username.isPresent()) {
      sql.append(" WHERE username = :username");
      params.put("username", username.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    }
    sql.append(" AND time >= :time ORDER BY time");
    params.put("time", time.iso8601());
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
  }

  public List<ChatMessage> getAllBeforeTime(
      Optional<String> username, Optional<String> channel, UTCDateTime time) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, username, message, channel, user_id, room_id, time FROM chat_message");
    if (username.isPresent() && channel.isPresent()) {
      sql.append(" WHERE username = :username AND channel = :channel");
      params.put("username", username.get());
      params.put("channel", channel.get());
    } else if (username.isPresent()) {
      sql.append(" WHERE username = :username");
      params.put("username", username.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    }
    sql.append(" AND time <= :time ORDER BY time");
    params.put("time", time.iso8601());
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
  }

  public List<ChatMessage> getAllBetweenTimes(
      Optional<String> username,
      Optional<String> channel,
      UTCDateTime fromDateTime,
      UTCDateTime toDateTime) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder(
            "SELECT id, username, message, channel, user_id, room_id, time FROM chat_message");
    if (username.isPresent() && channel.isPresent()) {
      sql.append(" WHERE username = :username AND channel = :channel");
      params.put("username", username.get());
      params.put("channel", channel.get());
    } else if (username.isPresent()) {
      sql.append(" WHERE username = :username");
      params.put("username", username.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    }
    sql.append(" AND time >= :from_date_time AND time <= :to_date_time ORDER BY time");
    params.put("from_date_time", fromDateTime.iso8601());
    params.put("to_date_time", toDateTime.iso8601());
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
  }

  private ChatMessage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new ChatMessage(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("username"),
        resultSet.getString("message"),
        resultSet.getString("channel"),
        resultSet.getLong("user_id"),
        resultSet.getLong("room_id"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
