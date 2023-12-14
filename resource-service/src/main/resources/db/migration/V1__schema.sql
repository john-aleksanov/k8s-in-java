CREATE SEQUENCE IF NOT EXISTS resource_id_sequence;

CREATE TABLE IF NOT EXISTS resources
(
    id      INTEGER PRIMARY KEY,
    content BYTEA
);
