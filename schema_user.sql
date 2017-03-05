DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  email varchar(45) NOT NULL,
  password varchar(45) NOT NULL,
  firstname varchar(45) NOT NULL,
  lastname varchar(45) NOT NULL,
  bio varchar(45) DEFAULT NULL,
  profile_picture varchar(100) DEFAULT NULL,
  display_name varchar(45) DEFAULT NULL,
  follows int(11) DEFAULT 0,
  followed_by int(11) DEFAULT 0,
  PRIMARY KEY (id)
)
