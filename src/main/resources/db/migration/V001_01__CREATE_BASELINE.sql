CREATE TABLE chat_message
(
    id       UUID PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    message  TEXT        NOT NULL,
    channel  VARCHAR(64) NOT NULL,
    user_id  VARCHAR(64) NOT NULL,
    room_id  VARCHAR(64) NOT NULL,
    time     VARCHAR(64) NOT NULL
);

CREATE TABLE user_join
(
    id       UUID PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    channel  VARCHAR(64) NOT NULL,
    time     VARCHAR(64) NOT NULL
);

CREATE TABLE user_leave
(
    id       UUID PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    channel  VARCHAR(64) NOT NULL,
    time     VARCHAR(64) NOT NULL
);

CREATE TABLE host
(
    id             UUID PRIMARY KEY,
    channel        VARCHAR(64) NOT NULL,
    target_channel VARCHAR(64),
    time           VARCHAR(64) NOT NULL
);

CREATE TABLE clear_chat
(
    id              UUID PRIMARY KEY,
    target_username VARCHAR(64) NOT NULL,
    target_user_id  VARCHAR(64) NOT NULL,
    channel         VARCHAR(64) NOT NULL,
    room_id         VARCHAR(64) NOT NULL,
    seconds         VARCHAR(64) NOT NULL,
    time            VARCHAR(64) NOT NULL
);

CREATE TABLE clear_message
(
    id              UUID PRIMARY KEY,
    target_username VARCHAR(64) NOT NULL,
    channel         VARCHAR(64) NOT NULL,
    message         TEXT        NOT NULL,
    time            VARCHAR(64) NOT NULL
);

CREATE TABLE twitch_user
(
    id                 BIGINT PRIMARY KEY,
    username           VARCHAR(64) NOT NULL,
    display_name       VARCHAR(64) NOT NULL,
    discovered_time    VARCHAR(64) NOT NULL,
    discovered_channel VARCHAR(64) NOT NULL
);

CREATE TABLE invalid_twitch_user
(
    id                 UUID PRIMARY KEY,
    invalid_id         BIGINT      NOT NULL,
    username           VARCHAR(64) NOT NULL,
    discovered_time    VARCHAR(64) NOT NULL,
    discovered_channel VARCHAR(64) NOT NULL
);

CREATE TABLE name_change
(
    id                 VARCHAR(64) PRIMARY KEY,
    user_id            BIGINT      NOT NULL,
    old_username       VARCHAR(64),
    old_display_name   VARCHAR(64),
    new_username       VARCHAR(64) NOT NULL,
    new_display_name   VARCHAR(64) NOT NULL,
    discovered_time    VARCHAR(64) NOT NULL,
    discovered_channel VARCHAR(64) NOT NULL
);

CREATE TABLE last_read
(
    name VARCHAR(64) NOT NULL,
    id   VARCHAR(64) NOT NULL
);