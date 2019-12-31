package app.datacollect.twitchdata.api.userleave.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.userleave.domain.UserLeave;
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
public class UserLeaveRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public UserLeaveRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(UserLeave userLeave) {
    jdbcTemplate.update(
        "INSERT INTO user_leave(id, username, channel, time) VALUES (:id, :username, :channel, :time)",
        new MapSqlParameterSource()
            .addValue("id", userLeave.getId())
            .addValue("username", userLeave.getUsername())
            .addValue("channel", userLeave.getChannel())
            .addValue("time", userLeave.getTime().iso8601()));
  }

  public List<UserLeave> getAll(Optional<String> username, Optional<String> channel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder("SELECT id, username, channel, time FROM user_leave");
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

  private UserLeave mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new UserLeave(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("username"),
        resultSet.getString("channel"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
