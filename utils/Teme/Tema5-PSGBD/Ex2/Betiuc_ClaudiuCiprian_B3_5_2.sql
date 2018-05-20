CREATE OR REPLACE PACKAGE collegeManagement IS

    PROCEDURE increaseScholarship(v_IDstudent IN studenti.id%TYPE, v_scholarshipIncreaseAmount IN NUMBER);
    PROCEDURE initialiseScholarshipHistory;
    
    scholarshipLargerThan3000 EXCEPTION;
    PRAGMA EXCEPTION_INIT(scholarshipLargerThan3000,-20001);
    
    v_currentScholarship studenti.bursa%TYPE;
    copyOfScholarship studenti.ScholarshipHistory%TYPE;
    
END collegeManagement;
/ 
    ALTER TABLE studenti drop (ScholarshipHistory);
/
   CREATE OR REPLACE TYPE ScholarshipHistoryVarray IS VARRAY(100) OF NUMBER;
 /
    ALTER table studenti add (ScholarshipHistory ScholarshipHistoryVarray);
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
CREATE OR REPLACE PACKAGE BODY collegeManagement IS 
    PROCEDURE increaseScholarship(v_IDstudent IN studenti.id%TYPE, v_scholarshipIncreaseAmount IN NUMBER)IS
    
    v_initialScholarhip NUMBER;
    v_increasedScholarship NUMBER:=0;
    
    BEGIN
        SELECT bursa INTO v_initialScholarhip FROM studenti WHERE id=v_IDstudent;
        v_increasedScholarship:=v_initialScholarhip+v_scholarshipIncreaseAmount;
        
        IF(v_increasedScholarship>3000)THEN
            v_increasedScholarship:=3000;
            UPDATE studenti SET bursa=v_increasedScholarship WHERE id=v_IDstudent;
            SELECT ScholarshipHistory INTO copyOfScholarship FROM studenti WHERE id=v_IDstudent;
            copyOfScholarship.extend;
            copyOfScholarship(copyOfScholarship.LAST):=v_increasedScholarship;
            UPDATE studenti SET ScholarshipHistory=copyOfScholarship WHERE  id=v_IDstudent;
            RAISE scholarshipLargerThan3000;
        END IF;
        
        UPDATE studenti SET bursa=v_increasedScholarship WHERE id=v_IDstudent;
        
        SELECT ScholarshipHistory INTO copyOfScholarship FROM studenti WHERE id=v_IDstudent;
        copyOfScholarship.extend;
        copyOfScholarship(copyOfScholarship.LAST):=v_increasedScholarship;
        
        UPDATE studenti SET ScholarshipHistory=copyOfScholarship WHERE  id=v_IDstudent;
    END increaseScholarship;
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --    
        PROCEDURE initialiseScholarshipHistory IS
    
    CURSOR initialise IS 
    SELECT bursa FROM studenti FOR UPDATE OF scholarshiphistory;
    
    BEGIN

    OPEN initialise;
    
    LOOP    
        FETCH initialise INTO v_currentScholarship;
        
        EXIT WHEN initialise%NOTFOUND;
    
        copyOfScholarship:=ScholarshipHistoryVarray(v_currentScholarship);
        UPDATE studenti SET ScholarshipHistory=copyOfScholarship WHERE CURRENT OF initialise;
        
    END LOOP;
    
    CLOSE initialise;
        
    END initialiseScholarshipHistory;

END collegeManagement;
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --

set serveroutput on;

DECLARE
    
    v_minimumID NUMBER;
    v_maximumID NUMBER;
    v_randomID NUMBER;
    v_finished NUMBER:=0;
    v_counter NUMBER;
    
    scholarshipLargerThan3000 EXCEPTION;
    PRAGMA EXCEPTION_INIT(scholarshipLargerThan3000,-20001);

BEGIN

     collegeManagement.initialiseScholarshipHistory;

     SELECT MIN(ID) INTO v_minimumID FROM studenti;
     SELECT MAX(ID) INTO v_maximumID FROM studenti; 
     
     LOOP
            BEGIN
            EXIT WHEN v_finished=100;
            v_randomID:=ROUND(DBMS_RANDOM.VALUE(v_minimumID,v_maximumID));
            SELECT COUNT(*) INTO v_counter FROM studenti WHERE id=v_randomID;
            IF v_counter>0 THEN
                collegeManagement.increaseScholarship(v_randomID,2000);
                v_finished:=v_finished+1;
            END IF;
            
            EXCEPTION
            WHEN scholarshipLargerThan3000 THEN
                DBMS_OUTPUT.PUT_LINE('The scholarship for the student with the ID '||v_randomID||' has been modified to 3000 because it was larger than 3000.');
             END;
    END LOOP;
    
    DBMS_OUTPUT.PUT_LINE('');
    DBMS_OUTPUT.PUT_LINE('=====================================================');
    
    v_counter:=0;
    FOR cursor1 IN (SELECT * FROM studenti ORDER BY ID)LOOP
        EXIT WHEN v_counter=10;
            IF cursor1.bursa IS NOT NULL THEN
                v_counter:=v_counter+1;
                IF cursor1.ScholarshipHistory.COUNT=1 THEN
                    DBMS_OUTPUT.PUT_LINE('For the student with the ID '||cursor1.ID||' the scholarship was not increased. Current value: '||cursor1.ScholarshipHistory(cursor1.ScholarshipHistory.LAST));
                ELSIF cursor1.ScholarshipHistory.COUNT>1 THEN
                    DBMS_OUTPUT.PUT_LINE('For the student with the ID '||cursor1.ID||' the new scholarship is ' ||cursor1.bursa||'. Previous scholarhip: '||cursor1.ScholarshipHistory(cursor1.ScholarshipHistory.LAST-1));
                    DBMS_OUTPUT.PUT_LINE('Increase:'||(cursor1.ScholarshipHistory(cursor1.ScholarshipHistory.LAST)-cursor1.ScholarshipHistory(cursor1.ScholarshipHistory.LAST-1)));
                END IF;
            END IF;
        END LOOP;
    
END;