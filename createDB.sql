SET DEFINE OFF;

--1. Business 
CREATE TABLE Business (
	bid VARCHAR2(100) PRIMARY KEY NOT NULL,
	bname VARCHAR2(255),
	addr VARCHAR2(255),
	city VARCHAR2(30),
	state VARCHAR2(20),
	stars NUMBER(*,1),
	numReviews NUMBER,
	checkin_number NUMBER,
	type VARCHAR2(20)
);

--1.5. BusinessHour
CREATE TABLE BusinessHour (
	bid VARCHAR2(100) NOT NULL,
	day VARCHAR2(20),
	open VARCHAR2(10),
	close VARCHAR2(10),
	PRIMARY KEY (bid, day),
	FOREIGN KEY (bid) REFERENCES Business ON DELETE CASCADE
);

SELECT open
FROM BusinessHour
WHERE day = 'Tuesday'
AND ROWNUM <= 10;

--2. Users
CREATE TABLE users (
	userid VARCHAR2(100) PRIMARY KEY NOT NULL,
	name VARCHAR2(50),
	yelping_since VARCHAR2(30),
  	votes VARCHAR2(100), 
  	review_count NUMBER,
  	fans NUMBER,
  	avg_stars NUMBER(*,2),
	type VARCHAR2(20) NOT NULL
);

--3. Reviews
CREATE TABLE Review (
  	rid VARCHAR2(100) PRIMARY KEY NOT NULL,
  	stars NUMBER(*,1),
  	bid VARCHAR2(100) NOT NULL,
  	review_date VARCHAR2(50),
  	type VARCHAR2(20),  	
  	FOREIGN KEY (bid) REFERENCES Business ON DELETE CASCADE
);


--4. main category
CREATE TABLE mainCatg (
	mcname VARCHAR2(100) PRIMARY KEY NOT NULL
);


--5. sub category
CREATE TABLE subCatg (
	scname VARCHAR2(100) NOT NULL,
	mcname VARCHAR2(100),
	PRIMARY KEY (scname, mcname),
	FOREIGN KEY (mcname) REFERENCES mainCatg
);

--6. attribute
CREATE TABLE attribute (
	avalue VARCHAR2(255) PRIMARY KEY
);

--7. attribute with categories
CREATE TABLE attr_catg (
	scname VARCHAR2(100),
	mcname VARCHAR2(100),
	avalue VARCHAR2(255),
	PRIMARY KEY (scname, mcname, avalue),
	FOREIGN KEY (mcname) REFERENCES mainCatg
);

--8. main to business
CREATE TABLE main_busi (
	bid VARCHAR2(100),
	mcname VARCHAR2(100),
	PRIMARY KEy (bid, mcname),
	FOREIGN KEY (mcname) REFERENCES mainCatg
);

--9. subCatg to business
CREATE TABLE sub_busi (
	bid VARCHAR2(100),
	scname VARCHAR2(100),
	PRIMARY KEY (bid, scname)
);

--10. attri to business
CREATE TABLE attri_busi (
	bid VARCHAR2(100),
	avalue VARCHAR2(100),
	PRIMARY KEY (bid, avalue),
	FOREIGN key (avalue) REFERENCES attribute
);


CREATE INDEX mcid ON mainCatg(mcname);
CREATE INDEX scid ON subCatg(scname);
CREATE INDEX aid ON attribute(avalue);