Use versionedhoteldb;

CREATE TABLE BatchIDSequence (
ID bigint AUTO_INCREMENT
,processID int NOT NULL
,PRIMARY KEY (ID)
);

DELIMITER $$
CREATE PROCEDURE pNextVal()
BEGIN
	INSERT INTO BatchIDSequence (processID) VALUES(1);
	SELECT LAST_INSERT_ID();
END $$
 DELIMITER ;



