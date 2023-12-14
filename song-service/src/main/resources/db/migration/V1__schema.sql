CREATE SEQUENCE IF NOT EXISTS song_id_sequence;

CREATE TABLE IF NOT EXISTS songs
(
    id          INTEGER PRIMARY KEY,
    name        VARCHAR,
    artist      VARCHAR,
    album       VARCHAR,
    length      VARCHAR,
    resource_id VARCHAR,
    song_year   VARCHAR
)
