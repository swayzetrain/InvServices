CREATE DATABASE inventory;

CREATE TABLE users (
user_Id int AUTO_INCREMENT,
username varchar(25) UNIQUE NOT NULL,
password varchar(255) NOT NULL,
enabled bit NOT NULL DEFAULT 1,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (user_Id)
);

CREATE TABLE role (
role_Id int AUTO_INCREMENT,
role_Name varchar(50) unique NOT NULL,
role_Description varchar(150),
PRIMARY KEY (role_Id)
);

CREATE TABLE instance (
instance_Id int AUTO_INCREMENT,
instance_Name varchar(75) NOT NULL,
instance_Description varchar(150),
creation_User_Id int,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (instance_Id)
);

CREATE TABLE userrole (
userrole_Id int AUTO_INCREMENT,
user_Id int NOT NULL,
role_Id int NOT NULL,
instance_Id int NOT NULL,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (userrole_Id),
FOREIGN KEY (user_Id) REFERENCES users(user_Id),
FOREIGN KEY (role_Id) REFERENCES role(role_Id),
FOREIGN KEY (instance_Id) REFERENCES instance(instance_Id)
);

CREATE TABLE category (
category_Id int AUTO_INCREMENT,
category_Name varchar(75) NOT NULL,
instance_Id int NOT NULL,
creation_User_Id int,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (category_Id)
);

CREATE TABLE item (
item_Id int AUTO_INCREMENT,
item_Name varchar(75) NOT NULL,
category_Id int NOT NULL,
instance_Id int NOT NULL,
creation_User_Id int,
date_Created datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (item_Id),
FOREIGN KEY (category_Id) REFERENCES category(category_Id)
);

CREATE TABLE quantity (
quantity_Id int AUTO_INCREMENT,
item_Id int UNIQUE NOT NULL,
quantity int DEFAULT 0 NOT NULL,
quantity_Type varchar(50) NOT NULL,
date_Modified datetime DEFAULT CURRENT_TIMESTAMP NOT NULL,
PRIMARY KEY (quantity_Id),
FOREIGN KEY (item_Id) REFERENCES item(item_Id)
);