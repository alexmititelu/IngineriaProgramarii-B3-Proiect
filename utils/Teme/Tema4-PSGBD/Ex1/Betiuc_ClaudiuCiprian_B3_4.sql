CREATE OR REPLACE PACKAGE collegeManagement IS
    
     v_currentScholarship studenti.bursa%TYPE;
     copyOfScholarship studenti.ScholarshipHistory%TYPE;
     v_newScholarship NUMBER;
     
    TYPE studId_scholarshipModification IS RECORD(studentID studenti.id%TYPE, modificationPercentage NUMBER);
    TYPE modificationsRecordVarray IS VARRAY(5) OF studId_scholarshipModification;
    
    PROCEDURE modifiyScholarships(modificationsList IN modificationsRecordVarray);
    PROCEDURE initialiseScholarshipHistory;
    PROCEDURE advancedModifyScholarship(modificationsList IN modificationsRecordVarray);
    PROCEDURE printScholarshipHistory;
    
END collegeManagement;
/
   CREATE OR REPLACE TYPE ScholarshipHistoryVarray IS VARRAY(10) OF NUMBER;
/
    ALTER TABLE studenti drop (ScholarshipHistory);
/
    ALTER table studenti add (ScholarshipHistory ScholarshipHistoryVarray);
/

-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --

CREATE OR REPLACE PACKAGE BODY collegeManagement IS
    
    PROCEDURE modifiyScholarships(modificationsList IN modificationsRecordVarray) IS

    BEGIN
        FOR i IN modificationsList.FIRST..modificationsList.LAST LOOP
            IF modificationsList.EXISTS(i) THEN
               
                SELECT bursa INTO v_currentScholarship FROM studenti WHERE id=modificationsList(i).studentID;
               
                IF v_currentScholarship IS NULL THEN 
                        SELECT 100+100*modificationsList(i).modificationPercentage INTO v_newScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                ELSE
                        SELECT v_currentScholarship+v_currentScholarship*modificationsList(i).modificationPercentage INTO v_newScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                END IF;
                
                 UPDATE studenti SET bursa=v_newScholarship WHERE id=modificationsList(i).studentID;
                
                ELSE DBMS_OUTPUT.PUT_LINE('Skipped empty varray element.');
                
            END IF;
        END LOOP;
    END modifiyScholarships;
    
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
    
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
    
     PROCEDURE advancedModifyScholarship(modificationsList IN modificationsRecordVarray) IS
    
    BEGIN
        FOR i IN modificationsList.FIRST..modificationsList.LAST LOOP
            IF modificationsList.EXISTS(i) THEN
                
                SELECT bursa INTO v_currentScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                
                IF v_currentScholarship IS NULL THEN 
                    SELECT 100+100*modificationsList(i).modificationPercentage INTO v_newScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                ELSE 
                    SELECT bursa+bursa*modificationsList(i).modificationPercentage INTO v_newScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                END IF;
                
                UPDATE studenti SET bursa=v_newScholarship WHERE id=modificationsList(i).studentID;
                
                SELECT ScholarshipHistory INTO copyOfScholarship FROM studenti WHERE id=modificationsList(i).studentID;
                copyOfScholarship.extend;
                copyOfScholarship(copyOfScholarship.LAST):=v_newScholarship;
                
                UPDATE studenti SET ScholarshipHistory=copyOfScholarship WHERE  id=modificationsList(i).studentID;
            
            ELSE DBMS_OUTPUT.PUT_LINE('Skipped empty varray element.');
            
            END IF;
        END LOOP;
    END advancedModifyScholarship;
    
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
    
    PROCEDURE printScholarshipHistory IS
    
    v_studentID NUMBER;
    
    CURSOR printHistory IS 
    SELECT ScholarshipHistory,id FROM studenti;
    
    BEGIN
    
    OPEN printHistory;
    
    LOOP
        FETCH printHistory INTO copyOfScholarship,v_studentID;
        EXIT WHEN printHistory%NOTFOUND;
        IF copyOfScholarship.COUNT>=2 THEN
            DBMS_OUTPUT.PUT_LINE('======================');
            DBMS_OUTPUT.PUT_LINE('Scholarship History for student with ID '||v_studentID);
            FOR i IN copyOfScholarship.FIRST..copyOfScholarship.LAST LOOP
                DBMS_OUTPUT.PUT_LINE(copyOfScholarship(i));
            END LOOP;
        END IF;
    END LOOP;
    
    CLOSE printHistory;
    
    END printScholarshipHistory;
    
END collegeManagement;
/
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
set serveroutput on;    
        
DECLARE 
    
        modifications collegeManagement.modificationsRecordVarray := collegeManagement.modificationsRecordVarray();
        
BEGIN
        
--FIRST EXERCISE:

        modifications.extend;
        modifications(1).studentID:=1013;
        modifications(1).modificationPercentage:=1;
        modifications.extend;
        modifications(2).studentID:=1014;
        modifications(2).modificationPercentage:=1;
        modifications.extend;
        modifications(3).studentID:=1015;
        modifications(3).modificationPercentage:=1;
        modifications.extend;
        modifications(4).studentID:=1016;
        modifications(4).modificationPercentage:=1;
        modifications.extend;
        modifications(5).studentID:=1017;
        modifications(5).modificationPercentage:=1;
 
        collegeManagement.modifiyScholarships(modifications);

--SECOND EXERCISE:
        
--        collegeManagement.initialiseScholarshipHistory;
--        collegeManagement.advancedModifyScholarship(modifications);

--THIRD EXERCISE:

--        collegeManagement.printScholarshipHistory;

END;     