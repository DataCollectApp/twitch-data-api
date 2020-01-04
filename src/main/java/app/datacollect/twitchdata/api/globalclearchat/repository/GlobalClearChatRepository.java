package app.datacollect.twitchdata.api.globalclearchat.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.globalclearchat.domain.GlobalClearChat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GlobalClearChatRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public GlobalClearChatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(GlobalClearChat globalClearChat) {
    jdbcTemplate.update(
        "INSERT INTO global_clear_chat(id, channel, room_id, time) VALUES (:id, :channel, :room_id, :time)",
        Map.of(
            "id",
            globalClearChat.getId(),
            "channel",
            globalClearChat.getChannel(),
            "room_id",
            globalClearChat.getRoomId(),
            "time",
            globalClearChat.getTime().iso8601()));
  }

  public List<GlobalClearChat> getAll() {
    return jdbcTemplate.query(
        "SELECT id, channel, room_id, time FROM global_clear_chat", this::mapRow);
  }

  private GlobalClearChat mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new GlobalClearChat(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("channel"),
        resultSet.getLong("room_id"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
