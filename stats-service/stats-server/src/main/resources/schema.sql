DROP TABLE IF EXISTS endpoint_hits;

CREATE TABLE IF NOT EXISTS endpoint_hits
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    app  VARCHAR(300)                             NOT NULL,
    uri  VARCHAR(300)                             NOT NULL,
    ip   VARCHAR(300)                             NOT NULL,
    hit_time TIMESTAMP                          NOT NULL
    );