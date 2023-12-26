CREATE TABLE XSA_RELATIONSHIP (ID BIGINT NOT NULL, PRIMARY_PARENT_ID BIGINT NULL, SECONDARY_PARENT_ID BIGINT NULL, TYPE VARCHAR(4) NULL, PRIMARY KEY (ID))
;

CREATE TABLE XSA_PERSON_IDENTITY (END_DATE BIGINT NULL, ID BIGINT NOT NULL, PERSON_ID BIGINT NOT NULL, START_DATE BIGINT NULL, PRIMARY KEY (ID))
;

CREATE TABLE XSA_PERSON (ID BIGINT NOT NULL, PARENT_RELATIONSHIP_ID BIGINT NULL, PRIMARY_IDENTITY_ID BIGINT NOT NULL, PRIMARY KEY (ID))
;

CREATE TABLE XSA_PERSON_NAME (ID BIGINT NOT NULL, NAME VARCHAR(128) NOT NULL, PERSON_IDENTITY_ID BIGINT NOT NULL, SEQ_NUM SMALLINT NOT NULL, TYPE VARCHAR(16) NOT NULL, PRIMARY KEY (ID))
;

CREATE TABLE XSA_RELATIONSHIP_EVENT (APPROXIMATION VARCHAR(4) NULL, DTM BIGINT NULL, ID BIGINT NOT NULL, PRECISION VARCHAR(8) NULL, RELATIONSHIP_ID BIGINT NOT NULL, TYPE VARCHAR(4) NULL, PRIMARY KEY (ID))
;

CREATE TABLE XSA_PERSON_EVENT (APPROXIMATION VARCHAR(4) NULL, DTM BIGINT NOT NULL, ID BIGINT NOT NULL, PERSON_ID BIGINT NOT NULL, PRECISION VARCHAR(8) NOT NULL, TYPE VARCHAR(4) NOT NULL, PRIMARY KEY (ID))
;

ALTER TABLE XSA_RELATIONSHIP ADD FOREIGN KEY (PRIMARY_PARENT_ID) REFERENCES XSA_PERSON (ID)
;

ALTER TABLE XSA_RELATIONSHIP ADD FOREIGN KEY (SECONDARY_PARENT_ID) REFERENCES XSA_PERSON (ID)
;

ALTER TABLE XSA_PERSON_IDENTITY ADD FOREIGN KEY (PERSON_ID) REFERENCES XSA_PERSON (ID)
;

ALTER TABLE XSA_PERSON ADD FOREIGN KEY (PRIMARY_IDENTITY_ID) REFERENCES XSA_PERSON_IDENTITY (ID)
;

ALTER TABLE XSA_PERSON ADD FOREIGN KEY (PARENT_RELATIONSHIP_ID) REFERENCES XSA_RELATIONSHIP (ID)
;

ALTER TABLE XSA_PERSON_NAME ADD FOREIGN KEY (PERSON_IDENTITY_ID) REFERENCES XSA_PERSON_IDENTITY (ID)
;

ALTER TABLE XSA_RELATIONSHIP_EVENT ADD FOREIGN KEY (RELATIONSHIP_ID) REFERENCES XSA_RELATIONSHIP (ID)
;

ALTER TABLE XSA_PERSON_EVENT ADD FOREIGN KEY (PERSON_ID) REFERENCES XSA_PERSON (ID)
;

CREATE TABLE AUTO_PK_SUPPORT (TABLE_NAME CHAR(100) NOT NULL, NEXT_ID BIGINT NOT NULL, PRIMARY KEY(TABLE_NAME))
;

DELETE FROM AUTO_PK_SUPPORT WHERE TABLE_NAME IN ('XSA_PERSON_EVENT', 'XSA_PERSON_NAME', 'XSA_RELATIONSHIP_EVENT', 'XSA_RELATIONSHIP', 'XSA_PERSON_IDENTITY', 'XSA_PERSON')
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_PERSON_EVENT', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_PERSON_NAME', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_RELATIONSHIP_EVENT', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_RELATIONSHIP', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_PERSON_IDENTITY', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('XSA_PERSON', 200)
;

