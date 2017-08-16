CREATE DATABASE inventory;

CREATE TABLE category (
category_id int,
category_name varchar(75),
date_created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (category_id)
);

CREATE TABLE item (
item_id int,
name varchar(75),
category_id int NOT NULL,
date_created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (item_id),
FOREIGN KEY (category_id) REFERENCES category(category_id)
);

CREATE TABLE quantity (
quantity_id int,
item_id int,
quantity int,
date_modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (quantity_id,item_id),
FOREIGN KEY (item_id) REFERENCES item(item_id)
);