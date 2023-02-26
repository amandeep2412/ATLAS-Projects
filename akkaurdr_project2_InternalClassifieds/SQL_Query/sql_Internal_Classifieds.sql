create database classifiedsdb

--USER TABLE--------------------------------------------------
create table Users(
    userID INT IDENTITY(1,1),
    name NVARCHAR(50) NOT NULL,
    phone NVARCHAR(20) NOT NULL UNIQUE,
    email NVARCHAR(30) NOT NULL UNIQUE,
    password NVARCHAR(100) NOT NULL,
    address NVARCHAR(100),
    userType INT NOT NULL, --(1-Admin, 2-Buyer, 3-Seller)
    userStatus BIT NOT NULL,    --(1-Active, 0 Inactive)
    createdOn DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(userID));


--_____________________________________________________
create table Category(
	categoryID INT IDENTITY(100,1),
	title NVARCHAR(20) NOT NULL UNIQUE,
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(categoryID));



--Trigger to update "lastUpdatedOn" column on any updation on "Title" column.
CREATE TRIGGER tgrAfterUpdate ON Category
AFTER UPDATE 
AS
  UPDATE Category set lastUpdatedOn=CURRENT_TIMESTAMP 
  FROM 
  Category
  INNER JOIN INSERTED  
  ON Category.title = INSERTED.title;
-----------------------------------------------------------------------------------------

create table Classifieds(
	classifiedID INT IDENTITY(1,1), 
	categoryID INT constraint classifieds_categoryID_fk references Category(categoryID), 
	status INT NOT NULL, 
	headline NVARCHAR(100) NOT NULL, 
	productName NVARCHAR(50) NOT NULL, 
	brand NVARCHAR(25) NOT NULL, 
	condition NVARCHAR(50), 
	description NVARCHAR(100), 
	price INT NOT NULL, 
	pictures NVARCHAR(100), 
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(classifiedID));



-----------------------------------------------------------------------------------------

create table Orders(
	orderID INT IDENTITY(1,1),
	classifiedID INT constraint orders_classifiedID_fk references Classifieds(classifiedID),
	fromUserID INT constraint orders_sellerUserID_fk references Users(userID), 
	toUserId INT constraint orders_buyerUserID_fk references Users(userID),
	proposedPrice INT NOT NULL,
	status INT NOT NULL, 
	lastUpdatedOn DATETIME DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(orderID));



-----------------------------------------------------------------------------------------

