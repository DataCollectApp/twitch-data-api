# twitch-data-api
Application to expose data from Twitch

### Environment variables
The following properties are required to run the application:  

| Name  | Description |  
| ------------- | ------------- |  
| SPRING_SECURITY_USER_NAME  | Username for accessing endpoints  |  
| SPRING_SECURITY_USER_PASSWORD   | Password for accessing endpoints  |  
| SPRING_DATASOURCE_URL   | Database URL (Postgres  supported)  |  
| SPRING_DATASOURCE_USERNAME   | Database username  |  
| SPRING_DATASOURCE_PASSWORD   | Database password  |
| TWITCH_DATA_FEED_READER_CHAT_MESSAGE_FEED_URL | URL to the feed of chat messages |
| TWITCH_DATA_FEED_READER_CHAT_MESSAGE_FEED_USERNAME | Username to the feed |
| TWITCH_DATA_FEED_READER_CHAT_MESSAGE_FEED_PASSWORD | Password to the feed |
| TWITCH_DATA_FEED_READER_CHAT_MESSAGE_FEED_PAGE_SIZE | The amount of events to read at a time |
| TWITCH_DATA_FEED_READER_PUNISHMENT_FEED_URL | URL to the feed of punishments |
| TWITCH_DATA_FEED_READER_PUNISHMENT_FEED_USERNAME | Username to the feed |
| TWITCH_DATA_FEED_READER_PUNISHMENT_FEED_PASSWORD | Password to the feed |
| TWITCH_DATA_FEED_READER_PUNISHMENT_FEED_PAGE_SIZE | The amount of events to read at a time |