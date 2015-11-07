CREATE TABLE IF NOT EXISTS `item` (
	`item_id` varchar(15) NOT NULL
	`food_bank_id` int(11) DEFAULT NULL,
	`item_name` varchar(15) DEFAULT NULL,
	`item_quantity` int(11) DEFAULT '0',
	PRIMARY KEY(`item_id`),
	INDEX(`food_bank_id`)
);