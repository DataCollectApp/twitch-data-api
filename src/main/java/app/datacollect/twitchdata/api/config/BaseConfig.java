package app.datacollect.twitchdata.api.config;

import app.datacollect.twitchdata.feed.reader.TwitchDataFeedReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class BaseConfig {

  @Bean
  public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper = builder.build();
    objectMapper.registerModule(new Jdk8Module());
    return objectMapper;
  }

  @Bean("chatMessageFeedReader")
  public TwitchDataFeedReader chatMessageFeedReader(
      @Qualifier("chatMessageFeedProperties") FeedProperties feedProperties) {
    return new TwitchDataFeedReader(
        feedProperties.getUrl(),
        feedProperties.getUsername(),
        feedProperties.getPassword(),
        feedProperties.getPageSize());
  }

  @Bean("chatMessageFeedProperties")
  @ConfigurationProperties("twitch-data-feed-reader.chat-message-feed")
  public FeedProperties chatMessageFeedProperties() {
    return new FeedProperties();
  }

  @Bean("punishmentFeedReader")
  public TwitchDataFeedReader punishmentFeedReader(
      @Qualifier("punishmentFeedProperties") FeedProperties feedProperties) {
    return new TwitchDataFeedReader(
        feedProperties.getUrl(),
        feedProperties.getUsername(),
        feedProperties.getPassword(),
        feedProperties.getPageSize());
  }

  @Bean("punishmentFeedProperties")
  @ConfigurationProperties("twitch-data-feed-reader.punishment-feed")
  public FeedProperties punishmentFeedProperties() {
    return new FeedProperties();
  }
}
