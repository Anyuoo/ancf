CREATE TABLE `comment`
(
    `id`            int      NOT NULL AUTO_INCREMENT,
    `content`       varchar(255) NULL,
    `user_id`       int      NOT NULL,
    `entity_id`     int      NOT NULL,
    `entity_type`   tinyint  NOT NULL,
    `target_id`     int NULL,
    `status`        tinyint  NOT NULL,
    `create_time`   datetime NOT NULL,
    `modified_time` datetime NOT NULL,
    PRIMARY KEY (`id`),
    INDEX           `cmt_userid_idx`(`user_id`, `entity_id`) USING BTREE
);

CREATE TABLE `message`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `from_id`       int          NOT NULL,
    `to_id`         int          NOT NULL,
    `chart_id`      varchar(10)  NOT NULL,
    `content`       varchar(255) NOT NULL,
    `status`        tinyint      NOT NULL,
    `create_time`   datetime     NOT NULL,
    `modified_time` datetime     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX           `msg_idx_from`(`from_id`) USING BTREE,
    INDEX           `msg_idx_to`(`to_id`) USING BTREE,
    INDEX           `msg_idx_chart_id`(`chart_id`) USING BTREE
);

CREATE TABLE `post`
(
    `id`            int         NOT NULL AUTO_INCREMENT,
    `type`          tinyint     NOT NULL,
    `user_id`       int         NOT NULL,
    `title`         varchar(20) NOT NULL,
    `content`       varchar(255) NULL,
    `cmt_num`       int         NOT NULL,
    `score`         float NULL,
    `status`        tinyint     NOT NULL,
    `create_time`   datetime    NOT NULL,
    `modified_time` datetime    NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`            int NOT NULL AUTO_INCREMENT,
    `account`       char(16) NULL,
    `gender`        tinyint NULL,
    `age`           int NULL,
    `birthday`      datetime NULL,
    `nickname`      char(9) NULL,
    `real_Name`     char(6) NULL,
    `avatar`        char(100) NULL,
    `email`         char(25) NULL,
    `mobile`        char(11) NULL,
    `salt`          char(5) NULL,
    `password`      varchar(12) NULL,
    `activation`    tinyint NULL,
    `status`        tinyint NULL,
    `create_time`   datetime NULL,
    `modified_time` datetime NULL,
    PRIMARY KEY (`id`),
    INDEX           `user_idx_account`(`account`) USING BTREE,
    INDEX           `user_idx_email`(`email`) USING BTREE,
    INDEX           `user_idx_modile`(`mobile`) USING BTREE
);

