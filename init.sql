
CREATE TABLE trade_data (
	trade_num BIGINT UNSIGNED auto_increment NOT NULL,
	auth_key varchar(100) NOT NULL,
	event_name varchar(30) NULL,
	event_time varchar(50) NULL,
	req_exchange varchar(20) NOT NULL,
	order_symbol varchar(30) NOT NULL,
	order_mode varchar(10) NULL,
	order_name varchar(20) NOT NULL,
	order_action varchar(10) NOT NULL,
	order_size varchar(100) NOT NULL,
	tp_price varchar(20) NULL,
	req_data varchar(1000) NULL,
	res_data varchar(1000) NULL,
	create_time varchar(50) NULL,
	req_time varchar(50) NULL,
	res_time varchar(50) NULL,
	CONSTRAINT trade_data_PK PRIMARY KEY (trade_num)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

CREATE TABLE allowed_account (
	allowed_num BIGINT UNSIGNED auto_increment NOT NULL,
	allowed_type varchar(12) NOT NULL,
	allowed_name varchar(100) NOT NULL,
	CONSTRAINT allowed_account_PK PRIMARY KEY (allowed_num)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci;

ALTER TABLE allowed_account ADD CONSTRAINT allowed_account_UN UNIQUE KEY (allowed_type,allowed_name);

