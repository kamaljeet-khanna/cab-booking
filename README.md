# Online Cab Booking Service


#### Find Nearest Cab API
###### GET-URL: http://localhost:8080/wednesday/cab?cur_latitude=18.591585624322512&cur_longitude=73.7340545654297

#### Book Cab API
###### POST-URL: http://localhost:8080/wednesday/cab

###### Request Body JSON
```
{
	"customerId": "1",
	"driverId": "D001",
	"pickupLatitude": 18.551814999999998,
	"pickupLongitude": 73.95115298694478,
	"dropLatitude": 18.177168793544702,
	"dropLongitude": 74.01489257812501
}
```

#### Find Customer Past Bookings API
###### GET-URL: http://localhost:8080/wednesday/cab/1

## Please do set-up the MySql DB before running above APIs
```

--
-- Create Database Schema
--
CREATE DATABASE `cab_booking`;

--
-- Table structure for table `bookings`
--
CREATE TABLE `bookings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(45) NOT NULL,
  `BOOKING_TIME` timestamp NOT NULL,
  `DRIVER_ID` varchar(45) NOT NULL,
  `PICKUP_LATITUDE` double NOT NULL,
  `PICKUP_LONGITUDE` double NOT NULL,
  `DROP_LATITUDE` double NOT NULL,
  `DROP_LONGITUDE` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `bookings`
--
INSERT INTO `bookings` VALUES (1,'1','2020-06-28 18:30:00','D001',18.551814999999998,73.95115298694478,18.177168793544702,74.01489257812501),(2,'1','2020-06-29 12:02:13','D001',18.551814999999998,73.95115298694478,18.177168793544702,74.01489257812501);

--
-- Table structure for table `cab`
--
CREATE TABLE `cab` (
  `DRIVER_ID` varchar(45) NOT NULL,
  `DRIVER_NAME` varchar(45) NOT NULL,
  `DRIVER_CONTACT_NUMBER` varchar(45) NOT NULL,
  `CAB_MODEL` varchar(45) NOT NULL,
  `CAB_REGISTRATION_NO` varchar(45) NOT NULL,
  `CAB_COLOR` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `cab`
--
INSERT INTO `cab` VALUES ('01','TEST','1234567890','MP 20','9999','BLUE');

--
-- Table structure for table `customer`
--
CREATE TABLE `customer` (
  `id` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `gender` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `customer`
--
INSERT INTO `customer` VALUES ('1','test','test@gmail.com','9877899870','F'),('2','rahul','rahul@gmail.com','9877899871','M');

--
-- Table structure for table `drivers`
--
CREATE TABLE `drivers` (
  `DRIVER_ID` varchar(20) NOT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `CUR_LATITUDE` double NOT NULL,
  `CUR_LONGITUDE` double NOT NULL,
  `CAB_MODEL` varchar(45) DEFAULT NULL,
  `LOCATION_NAME` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`DRIVER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `drivers`
--
INSERT INTO `drivers` VALUES ('D001','John',18.551814999999998,73.95115298694478,'Swift Desire','EON Kharadi'),('D002','Mike',18.54927752465053,73.90125274658205,'Swift Desire','Kalyani Nagar'),('D003','Albernt',18.56929381841284,73.85026931762697,'Swift Desire','Khadki'),('D004','Pinto',18.598744417722013,73.79894256591798,'Swift Desire','Pimple Saudagar');

```
