CREATE TABLE IF NOT EXISTS "user"
(
    "id"          TEXT    NOT NULL,
    "name"        TEXT    NOT NULL,
    "portrait"    TEXT DEFAULT NULL,
    "email"       TEXT DEFAULT NULL,
    "create_time" INTEGER NOT NULL,
    "update_time" INTEGER NOT NULL,
    PRIMARY KEY ("id")
);