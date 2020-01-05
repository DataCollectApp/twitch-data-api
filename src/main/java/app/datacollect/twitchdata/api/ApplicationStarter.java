package app.datacollect.twitchdata.api;

import app.datacollect.lastread.EnableLastRead;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableLastRead
public class ApplicationStarter {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationStarter.class, args);
  }
}
