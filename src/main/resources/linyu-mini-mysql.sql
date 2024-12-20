CREATE TABLE IF NOT EXISTS `user`
(
    `id`          VARCHAR(255) NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `portrait`    TEXT         DEFAULT NULL,
    `email`       VARCHAR(255) DEFAULT NULL,
    `create_time` timestamp(3) NOT NULL,
    `update_time` timestamp(3) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `chat_list`
(
    `id`           VARCHAR(255) NOT NULL,
    `user_id`      VARCHAR(255) NOT NULL,
    `target_id`    VARCHAR(255) NOT NULL,
    `unread_count` INT          DEFAULT 0,
    `last_message` TEXT         DEFAULT NULL,
    `type`         VARCHAR(255) DEFAULT NULL,
    `create_time`  timestamp(3) NOT NULL,
    `update_time`  timestamp(3) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `message`
(
    `id`           VARCHAR(255) NOT NULL,
    `from_id`      VARCHAR(255) NOT NULL,
    `to_id`        VARCHAR(255) NOT NULL,
    `from_info`    TEXT         NOT NULL,
    `message`      TEXT         DEFAULT NULL,
    `is_show_time` TINYINT(1) DEFAULT 0,
    `type`         VARCHAR(255) DEFAULT NULL,
    `source`       VARCHAR(255) DEFAULT NULL,
    `create_time`  timestamp(3) NOT NULL,
    `update_time`  timestamp(3) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE INDEX `idx_message_from_id_to_id` ON `message` (`from_id`, `to_id`);
