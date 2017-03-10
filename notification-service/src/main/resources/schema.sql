-- DROP TABLE IF EXISTS users;

-- CREATE TABLE users (
--   id BIGINT(20) NOT NULL AUTO_INCREMENT,
--   username varchar(255) NOT NULL,
--   password varchar(255) NOT NULL,
--   firstname varchar(255) NOT NULL,
--   lastname varchar(255) NOT NULL,
--   bio TEXT DEFAULT NULL,
--   profile_picture varchar(255) DEFAULT NULL,
--   display_name varchar(255) DEFAULT NULL,
--   follows int(20) DEFAULT 0,
--   followed_by int(20) DEFAULT 0,
--   role VARCHAR(255) NOT NULL,
--   enable TINYINT(1) NOT NULL DEFAULT 1,
--   PRIMARY KEY (id)
-- )
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS notificationUser;
DROP TABLE IF EXISTS notificationReaction;
DROP TABLE IF EXISTS notificationPost;

CREATE TABLE notifications(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  userId BIGINT(20),
  type_ varchar(20),
  text varchar(255),
  thumbnail varchar(255),
  notificationId varchar(255),
  PRIMARY KEY (id)
);

CREATE TABLE notificationUser(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  userId BIGINT(20),
  PRIMARY KEY (id)
);

CREATE TABLE notificationReaction(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  post_id BIGINT(20),
  comment_id BIGINT(20),
  PRIMARY KEY (id)
);

CREATE TABLE notificationPost(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  reaction_id BIGINT(20),
  PRIMARY KEY (id)
);