CREATE TABLE `item` (
	`id` varchar(15) NOT NULL AUTO_INCREMENT,
	`food_bank_id` int(11) DEFAULT '0',
	`item_name` varchar(15) DEFAULT '',
	`item_quantity` int(11) DEFAULT '0',
	PRIMARY KEY(`id`),
	INDEX(`food_bank_id`)
);