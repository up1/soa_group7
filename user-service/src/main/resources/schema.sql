DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  username varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  full_name varchar(255) NOT NULL,
  bio TEXT DEFAULT NULL,
  profile_picture varchar(255) DEFAULT NULL,
  display_name varchar(255) DEFAULT NULL,
  follows int(20) DEFAULT 0,
  followed_by int(20) DEFAULT 0,
  post_count int(20) DEFAULT 0,
  role VARCHAR(255) NOT NULL,
  enable TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id),
  FULLTEXT (username, full_name, display_name),
  CONSTRAINT contacts_unique UNIQUE (username)
)
