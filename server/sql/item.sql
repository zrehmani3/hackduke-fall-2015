CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_bank_id` int(11) DEFAULT '0',
  `name` varchar(15) DEFAULT '',
  `quantity` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `food_bank_id` (`food_bank_id`),
  KEY `quantity` (`quantity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;