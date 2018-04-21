Create Database `koi`;

Use `koi`;
Drop Table If Exists `staff`;

Create Table `staff`(
	`staff_id` int(11) Not Null Auto_Increment,
	`first_name` varchar(30) Not Null,
	`last_name` varchar(30) Not Null,
	`email` varchar(30) Not Null,
	`password` varchar(65) Not Null,
	Primary Key (`staff_id`)
	) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

Lock Tables `staff` Write;
INSERT INTO `staff` VALUES (1,'Travis','Roberts','123'),(2,'Johny','Adams','1234');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `drink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drink` (
  `drink_code` int(11) NOT NULL AUTO_INCREMENT,
  `drink_name` varchar(45) NOT NULL,
  `drink_category` varchar(45) NOT NULL,
  `price` float(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`drink_code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;


Lock Tables `drink` Write; 
INSERT INTO `drink` VALUES (1,'Honey Green Tea','tea',1.6,Null),(2,'Lemon Green Tea','tea',1.6,Null),(3,'Green Milk Tea','milk tea',1.6,Null),(4,'Caramel Milk Tea','milk tea',1.6,Null),(5,'Caramel Koi Cafe','coffee',1.7,Null);
Unlock Tables;

Drop Table If Exists `orders`;
Create Table `orders` (
	`id` int(11) Not Null Auto_Increment,
	`staff_id` int(11) Not Null, 
	`order_total` float(11) Not Null,
	Primary Key (`id`),
	Key `fk1_idx` (`staff_id`),
	Constraint `fk1` Foreign Key (`staff_id`) References `staff` (`staff_id`) On Delete No Action On Update No Action
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

Lock Tables `orders` Write;
INSERT INTO `orders` VALUES (1,1,356),(2,1,350),(3,2,740),(4,2,670),(5,1,485);
Unlock Tables;
