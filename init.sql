
CREATE TABLE wut_dev.trade_data (
	trade_num BIGINT UNSIGNED auto_increment NOT NULL,
	auth_key varchar(100) NOT NULL,
	event_name varchar(30) NULL,
	event_time varchar(20) NULL,
	req_exchange varchar(20) NOT NULL,
	order_symbol varchar(30) NOT NULL,
	order_mode varchar(10) NULL,
	order_name varchar(20) NOT NULL,
	order_action varchar(10) NOT NULL,
	order_size varchar(100) NOT NULL,
	tp_price varchar(20) NULL,
	req_data varchar(1000) NULL,
	res_data varchar(1000) NULL,
	create_time TIMESTAMP NULL,
	req_time TIMESTAMP NULL,
	res_time TIMESTAMP NULL,
	CONSTRAINT trade_data_PK PRIMARY KEY (trade_num)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;
