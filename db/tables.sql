-- create database

create database altheio;
-- altheio.clothes_item definition

CREATE TABLE `clothes_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `currency` varchar(10) NOT NULL,
  `current_stock_qty` bigint DEFAULT NULL,
  `manufacturing_country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- altheio.clothes_item_sale_country definition

CREATE TABLE `clothes_item_sale_country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_item_id` int NOT NULL,
  `sale_country_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_item_id` (`order_item_id`),
  CONSTRAINT `clothes_item_sale_country_ibfk_1` FOREIGN KEY (`order_item_id`) REFERENCES `clothes_item` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- altheio.food_item definition

CREATE TABLE `food_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `currency` varchar(10) NOT NULL,
  `current_stock_qty` bigint DEFAULT NULL,
  `manufacturing_country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- altheio.food_item_sale_country definition

CREATE TABLE `food_item_sale_country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_item_id` int NOT NULL,
  `sale_country_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_item_id` (`order_item_id`),
  CONSTRAINT `food_item_sale_country_ibfk_1` FOREIGN KEY (`order_item_id`) REFERENCES `food_item` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




-- altheio.order_contact_info definition

CREATE TABLE `order_contact_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contact_email` varchar(40) DEFAULT NULL,
  `contact_name` varchar(40) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- altheio.order_items definition

CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `item_type` varchar(20) DEFAULT NULL,
  `quantity` bigint DEFAULT NULL,
  `item_name` varchar(20) DEFAULT NULL,
  `expected_arrival_date` varchar(20) DEFAULT NULL,
  `item_price` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- altheio.orders definition

CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contact_id` int NOT NULL,
  `order_date` bigint DEFAULT NULL,
  `total_price` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
