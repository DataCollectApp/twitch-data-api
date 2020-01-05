package app.datacollect.twitchdata.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);

    http.csrf().ignoringAntMatchers("/twitch-user/search/**");
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().mvcMatchers(HttpMethod.GET, "/twitch-user/**", "/name-change/**");
  }
}
