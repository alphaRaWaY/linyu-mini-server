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

CREATE TABLE IF NOT EXISTS "chat_list"
(
    "id"           TEXT    NOT NULL,
    "user_id"      TEXT    NOT NULL,
    "target_id"    TEXT    NOT NULL,
    "unread_count" INTEGER DEFAULT 0,
    "last_message" TEXT    DEFAULT NULL,
    "type"         TEXT    DEFAULT NULL,
    "create_time"  INTEGER NOT NULL,
    "update_time"  INTEGER NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "message"
(
    "id"           TEXT    NOT NULL,
    "from_id"      TEXT    NOT NULL,
    "to_id"        TEXT    NOT NULL,
    "from_info"    TEXT    NOT NULL,
    "message"      TEXT    DEFAULT NULL,
    "is_show_time" BOOLEAN DEFAULT 0,
    "type"         TEXT    DEFAULT NULL,
    "source"       TEXT    DEFAULT NULL,
    "create_time"  INTEGER NOT NULL,
    "update_time"  INTEGER NOT NULL,
    PRIMARY KEY ("id")
);