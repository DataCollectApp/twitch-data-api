package app.datacollect.twitchdata.api.host.repository;

import app.datacollect.time.UTCDateTime;
import app.datacollect.twitchdata.api.host.domain.Host;
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
public class HostRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public HostRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insert(Host host) {
    jdbcTemplate.update(
        "INSERT INTO host(id, channel, target_channel, time) VALUES (:id, :channel, :target_channel, :time)",
        new MapSqlParameterSource()
            .addValue("id", host.getId())
            .addValue("channel", host.getChannel())
            .addValue("target_channel", host.getTargetChannel().orElse(null))
            .addValue("time", host.getTime().iso8601()));
  }

  public List<Host> getAll(Optional<String> channel, Optional<String> targetChannel) {
    final Map<String, Object> params = new HashMap<>();
    final StringBuilder sql =
        new StringBuilder("SELECT id, channel, target_channel, time FROM host");
    if (channel.isPresent() && targetChannel.isPresent()) {
      sql.append(" WHERE channel = :channel AND target_channel = :target_channel");
      params.put("channel", channel.get());
      params.put("target_channel", targetChannel.get());
    } else if (channel.isPresent()) {
      sql.append(" WHERE channel = :channel");
      params.put("channel", channel.get());
    } else if (targetChannel.isPresent()) {
      sql.append(" WHERE target_channel = :target_channel");
      params.put("target_channel", targetChannel.get());
    }
    return jdbcTemplate.query(sql.toString(), params, this::mapRow);
  }

  private Host mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    return new Host(
        UUID.fromString(resultSet.getString("id")),
        resultSet.getString("channel"),
        Optional.ofNullable(resultSet.getString("target_channel")),
        UTCDateTime.of(resultSet.getString("time")));
  }
}
