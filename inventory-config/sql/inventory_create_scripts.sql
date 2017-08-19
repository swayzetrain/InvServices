CREATE DATABASE inventory;

CREATE TABLE category (
category_Id int AUTO_INCREMENT,
category_Name varchar(75),
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (category_Id)
);

CREATE TABLE item (
item_Id int AUTO_INCREMENT,
item_Name varchar(75),
category_Id int NOT NULL,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (item_Id),
FOREIGN KEY (category_Id) REFERENCES category(category_Id)
);

CREATE TABLE quantity (
quantity_Id int AUTO_INCREMENT,
item_Id int,
quantity int DEFAULT 0 NOT NULL,
quantity_Type varchar(50),
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (quantity_Id,item_Id),
FOREIGN KEY (item_Id) REFERENCES item(item_Id)
);