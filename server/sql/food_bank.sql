CREATE TABLE IF NOT EXISTS `food_bank` (
	`food_bank_id` int(11) NOT NULL,
	`address` varchar(20) DEFAULT NULL,
	`zip_code` varchar(5) DEFAULT NULL,
	`food_bank_name` varchar(13) DEFAULT NULL,
	PRIMARY KEY(`food_bank_id`)
);