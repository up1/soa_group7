CREATE TABLE `reactions`.`reactions` (
  `id` BIGINT(20) NOT NULL,
  `user_id` BIGINT(20) NOT NULL,
  `post_id` BIGINT(20) NOT NULL,
  `reaction` ENUM('like', 'love', 'haha', 'wow', 'sad', 'angry') NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`));
