CREATE TABLE `item_log` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`date_of_delivery` int(11) NOT NULL DEFAULT '0',
	`item_name` varchar(13) NOT NULL DEFAULT '',
	`quantity` int(11) NOT NULL DEFAULT '0',
	`transaction_type` varchar(13) NOT NULL DEFAULT '',
	PRIMARY KEY(`id`)
);