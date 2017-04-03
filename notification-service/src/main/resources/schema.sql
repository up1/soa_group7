DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS notificationUsers;
DROP TABLE IF EXISTS notificationReactions;
DROP TABLE IF EXISTS notificationPosts;

CREATE TABLE notifications(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  userId BIGINT(20),
  type_ varchar(20),
  text varchar(255) DEFAULT '',
  thumbnail varchar(255) DEFAULT '',
  time DATETIME DEFAULT CURRENT_TIMESTAMP,
  notificationId BIGINT(255),
  checkStatus TINYINT(1) DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE notificationUsers(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  userId BIGINT(20),
  PRIMARY KEY (id)
);

CREATE TABLE notificationReactions(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  reaction_id BIGINT(20),
  PRIMARY KEY (id)
);

CREATE TABLE notificationPosts(
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  post_id BIGINT(20),
  comment_id BIGINT(20),
  PRIMARY KEY (id)
);