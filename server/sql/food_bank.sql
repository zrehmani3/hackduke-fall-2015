CREATE TABLE `food_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(1000) DEFAULT '',
  `zip_code` varchar(5) DEFAULT '',
  `food_bank_name` varchar(1000) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;