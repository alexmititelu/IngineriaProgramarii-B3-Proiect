CREATE OR REPLACE PACKAGE collegeManagement IS

    v_randomID NUMBER;
    v_minimumID NUMBER;
    v_maximumID NUMBER;
    v_counter NUMBER;
    
    TYPE failedToInsertIDs IS VARRAY(100) OF studenti.id%TYPE;
    
    PROCEDURE createErasmusTableAndIndex;
    PROCEDURE populateErasmusTable;
    
END collegeManagement;
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
CREATE OR REPLACE PACKAGE BODY collegeManagement IS
    PROCEDURE createErasmusTableAndIndex IS
    BEGIN
        EXECUTE IMMEDIATE 'DROP TABLE ERASMUS CASCADE CONSTRAINTS';
        EXECUTE IMMEDIATE 'CREATE TABLE ERASMUS (NR_MATRICOL VARCHAR2(6 BYTE), NUME VARCHAR2(15 BYTE), PRENUME VARCHAR2(30 BYTE), TARA NUMBER(38,0))';
        EXECUTE IMMEDIATE 'CREATE UNIQUE INDEX erasmusNR_MATRICOLindex ON ERASMUS (NR_MATRICOL)';
    END createErasmusTableAndIndex;
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --    
    PROCEDURE populateErasmusTable IS
    
        v_countryID NUMBER;
        v_NR_MATRICOL studenti.NR_MATRICOL%TYPE;
        v_NUME studenti.NUME%TYPE;
        v_PRENUME studenti.PRENUME%TYPE;
        v_notInsertedIDs failedToInsertIDs;
        
        BEGIN 
        
        SELECT MIN(ID) INTO v_minimumID FROM studenti;
        SELECT MAX(ID) INTO v_maximumID FROM studenti;
        
        v_notInsertedIDs:=failedToInsertIDs(NULL);       

        FOR i IN 1 .. 100 LOOP
        BEGIN
            v_randomID:=ROUND(DBMS_RANDOM.VALUE(v_minimumID,v_maximumID));
            
            SELECT NR_MATRICOL INTO v_NR_MATRICOL FROM studenti WHERE ID=v_randomID;
            SELECT NUME INTO v_NUME FROM studenti WHERE ID=v_randomID;
            SELECT PRENUME INTO v_PRENUME FROM studenti WHERE ID=v_randomID;
            v_countryID:=ROUND(DBMS_RANDOM.VALUE(1,10));
            
            INSERT INTO ERASMUS (NR_MATRICOL, NUME, PRENUME, TARA) SELECT v_NR_MATRICOL,v_NUME,v_PRENUME,v_countryID FROM DUAL;     
        
        EXCEPTION
        WHEN no_data_found THEN
            SELECT COUNT(*) INTO v_counter FROM studenti WHERE ID=v_randomID;
            IF v_counter=0 THEN
                DBMS_OUTPUT.PUT_LINE('There is no student in the STUDENTI table with ID='||v_randomID);
            END IF; 
        WHEN DUP_VAL_ON_INDEX THEN 
            v_notInsertedIDs.extend;
            v_notInsertedIDs(v_notInsertedIDs.LAST):=v_randomID;
        END;
        END LOOP;
        
        DBMS_OUTPUT.PUT_LINE('=============================================================================');
        DBMS_OUTPUT.PUT_LINE('The students from the STUDENTI table with the following IDs could not be inserted in the ERASMUS table due to UNIQUE NR_MATRICOL index constraint:');
        
        FOR i IN v_notInsertedIDs.FIRST .. v_notInsertedIDs.LAST LOOP
            IF v_notInsertedIDs.EXISTS(i) THEN
                DBMS_OUTPUT.PUT_LINE(v_notInsertedIDs(i));
            END IF;
        END LOOP;
        
        DBMS_OUTPUT.PUT_LINE('================================================');
        DBMS_OUTPUT.PUT_LINE('Students that have been in the Erasmus program and the IDs of the countries where they studied:');
        DBMS_OUTPUT.PUT_LINE('');
        FOR cursor1 IN (SELECT * FROM ERASMUS)
        LOOP
            DBMS_OUTPUT.PUT_LINE(cursor1.PRENUME||' '||cursor1.NUME||' studied in the country with ID= '||cursor1.TARA);
        END LOOP;
    END populateErasmusTable;
END collegeManagement;
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --

set serveroutput on;
    
-- FIRST EXERCISE:

DECLARE
    
    v_minimumID NUMBER;
    v_maximumID NUMBER;
    v_randomID NUMBER;
    v_finished NUMBER:=0;
    v_counter NUMBER;

BEGIN

    collegeManagement.createErasmusTableAndIndex;
    collegeManagement.populateErasmusTable;
    
END;