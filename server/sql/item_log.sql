CREATE TABLE IF NOT EXISTS `item_log` (
	`log_id` int(11) NOT NULL,
	`date_of_delivery` int(11) NOT NULL,
	`item_name` varchar(13) NOT NULL,
	`quantity` int(11) NOT NULL,
	`transaction_type` varchar(13) NOT NULL,
	PRIMARY KEY(`log_id`)
);

