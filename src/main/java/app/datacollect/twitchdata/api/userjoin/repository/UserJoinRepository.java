package app.datacollect.twitchdata.api.userjoin.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.userjoin.domain.UserJoin;
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
public class UserJoinRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public UserJoinRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(UserJoin userJoin) {
    jdbcTemplate.update(
        "INSERT INTO user_join(id, username, channel, time) VALUES (:id, :username, :channel, :time)",
        new MapSqlParameterSource()
            .addValue("id", userJoin.getId())
            .addValue("username", userJoin.getUsername())
            .addValue("channel", userJoin.getChannel())
            .addValue("time", userJoin.getTime().iso8601()));
  }

  public List<UserJoin> getAll(Optional<String> username, Optional<String> channel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder("SELECT id, username, channel, time FROM user_join");
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

  private UserJoin mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new UserJoin(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("username"),
        resultSet.getString("channel"),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
