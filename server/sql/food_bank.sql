CREATE TABLE `food_bank` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`address` varchar(20) DEFAULT '',
	`zip_code` varchar(5) DEFAULT '',
	`food_bank_name` varchar(13) DEFAULT '',
	PRIMARY KEY(`id`)
);