CREATE TABLE IF NOT EXISTS `character_vip` (
  `char_id` INT UNSIGNED NOT NULL DEFAULT 0,
  `vip_time` bigint(15) unsigned DEFAULT NULL,
  PRIMARY KEY (`char_id`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8;