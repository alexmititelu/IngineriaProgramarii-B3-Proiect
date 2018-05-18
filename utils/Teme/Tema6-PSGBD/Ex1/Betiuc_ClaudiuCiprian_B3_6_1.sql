CREATE  OR REPLACE TYPE MusicalInstrument AS OBJECT
(price NUMBER,
manufacturer VARCHAR2(50),
dateOfFabrication DATE,
instrumentModel VARCHAR2(50),
MEMBER PROCEDURE printMusicalInstrumentInfo,
FINAL MAP  MEMBER FUNCTION printMusicalInstrumentAge RETURN NUMBER, -- THIRD POINT EXAMPLE OF METHOD THAT CANNOT BE OVERRIDEN
CONSTRUCTOR FUNCTION MusicalInstrument (instrumentModel VARCHAR2) RETURN SELF AS RESULT,
CONSTRUCTOR FUNCTION MusicalInstrument (price NUMBER) RETURN SELF AS RESULT
)NOT FINAL;
/

CREATE  OR REPLACE TYPE BODY MusicalInstrument AS

       MEMBER PROCEDURE printMusicalInstrumentInfo IS
       BEGIN
            DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
            DBMS_OUTPUT.PUT_LINE('Price: '||price);
            DBMS_OUTPUT.PUT_LINE('Date of fabrication: '||dateOfFabrication);
            DBMS_OUTPUT.PUT_LINE('Manufacturer: '||manufacturer);
            DBMS_OUTPUT.PUT_LINE('Model: '||instrumentModel);         
       END printMusicalInstrumentInfo; 
       
       FINAL MAP MEMBER FUNCTION printMusicalInstrumentAge RETURN NUMBER IS
       BEGIN
            RETURN (SYSDATE-dateOfFabrication);
       END printMusicalInstrumentAge;
       
       CONSTRUCTOR FUNCTION MusicalInstrument (instrumentModel VARCHAR2) RETURN SELF AS RESULT AS
       BEGIN
            SELF.price:=NULL;
            SELF.manufacturer:=NULL;
            SELF.dateOfFabrication:=NULL;
            SELF.instrumentModel:=instrumentModel;
            RETURN;
       END MusicalInstrument;
       
       CONSTRUCTOR FUNCTION MusicalInstrument (price NUMBER) RETURN SELF AS RESULT AS
       BEGIN
            SELF.price:=price;
            SELF.manufacturer:=NULL;
            SELF.dateOfFabrication:=NULL;
            SELF.instrumentModel:=NULL;
            RETURN;
       END MusicalInstrument;
       
END;
/

CREATE OR REPLACE TYPE Guitar UNDER MusicalInstrument
(
    numberOfStrings NUMBER,
    OVERRIDING MEMBER PROCEDURE printMusicalInstrumentInfo
)
/

CREATE OR REPLACE TYPE BODY Guitar AS

    OVERRIDING MEMBER PROCEDURE printMusicalInstrumentInfo IS
    BEGIN 
        DBMS_OUTPUT.PUT_LINE('----------------------------------------------');
        DBMS_OUTPUT.PUT_LINE('Price: '||price);
        DBMS_OUTPUT.PUT_LINE('Date of fabrication: '||dateOfFabrication);
        DBMS_OUTPUT.PUT_LINE('Manufacturer: '||manufacturer);
        DBMS_OUTPUT.PUT_LINE('Model: '||instrumentModel);
        DBMS_OUTPUT.PUT_LINE('Number of strings: '||numberOfStrings);
    END printMusicalInstrumentInfo;
    
END;
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ 

DROP TABLE MUSICAL_INSTRUMENTS;
CREATE TABLE MUSICAL_INSTRUMENTS (obiect MusicalInstrument);

set serveroutput on;

DECLARE

     instrument1 MusicalInstrument;
     instrument2 MusicalInstrument;
     instrument3 MusicalInstrument;
     instrument4 MusicalInstrument;
     instrument5 MusicalInstrument;
     instrument6 MusicalInstrument;
     guitar1 Guitar;

BEGIN

    instrument1:=MusicalInstrument(4300, 'Ibanez',TO_DATE('11/04/2005','dd/mm/yyyy'),'RG550-WH');
    instrument2:=MusicalInstrument(1500, 'Vintage',TO_DATE('04/05/2004','dd/mm/yyyy'),'V6MSSB');
    instrument3:=MusicalInstrument(1584, 'Casio',TO_DATE('08/09/2017','dd/mm/yyyy'),'CDP-130 SR');
    instrument4:=MusicalInstrument(5040, 'Casio',TO_DATE('15/05/2015','dd/mm/yyyy'),'AP-470- Black Celviano');
    instrument5:=MusicalInstrument(4500);
    instrument6:=MusicalInstrument('KingKORK Black');
    guitar1:=Guitar(5200, 'Ibanez',TO_DATE('15/05/2015','dd/mm/yyyy'),'AZ242FM-TSG-Premium',6);
    
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument1);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument2);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument3);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument4);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument5);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (instrument6);
    INSERT INTO MUSICAL_INSTRUMENTS VALUES (guitar1);
    
    FOR cursor1 IN (SELECT * FROM MUSICAL_INSTRUMENTS ORDER BY 1 DESC)LOOP
        cursor1.obiect.printMusicalInstrumentInfo();
    END LOOP;
    
END;