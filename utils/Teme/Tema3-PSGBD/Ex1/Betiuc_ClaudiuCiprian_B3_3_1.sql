CREATE OR REPLACE PACKAGE collegeManagement IS
    PROCEDURE printStudentInfo(idStudent IN studenti.id%TYPE);
    PROCEDURE removeStudent(idStudent IN studenti.id%TYPE);
    PROCEDURE addStudent(firstName IN studenti.prenume%TYPE, lastName IN studenti.nume%TYPE);
END collegeManagement;
/

-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --

CREATE OR REPLACE PACKAGE BODY collegeManagement IS
    
    PROCEDURE calculateAge(birthDate IN DATE) IS

    v_sysDate DATE:=SYSDATE;
    v_years NUMBER;
    v_months NUMBER;
    v_days NUMBER;
    
    BEGIN
        v_years:=FLOOR((v_sysDate-birthDate)/365);
        v_months:=FLOOR(MONTHS_BETWEEN(v_sysDate,ADD_MONTHS(birthDate,12*v_years)));
        v_days:=FLOOR(v_sysDate-ADD_MONTHS(birthDate,12*v_years+v_months));
        v_days:=v_days+1; -- current day included
        
       DBMS_OUTPUT.PUT_LINE(v_years||' years, '||v_months||' months and '||v_days||' days.');
    END calculateAge;
    
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --    
    
    PROCEDURE printStudentInfo(idStudent IN studenti.id%TYPE) IS
        v_nrMatricol studenti.nr_matricol %TYPE;
        v_average NUMBER;
        v_studentBirthDate DATE;
        v_years NUMBER;
        v_months NUMBER;
        v_days NUMBER;
        v_rank NUMBER:=1;
        v_fetchAverage NUMBER;
        v_friend prieteni.id %TYPE;
        
        v_courseName VARCHAR2(51);
        v_courseGrade NUMBER;
       
        CURSOR courseANDgrade IS 
        SELECT valoare, titlu_curs FROM note JOIN cursuri ON
        note.id_curs=cursuri.id WHERE note.id_student=idStudent;
        
        CURSOR average IS 
        SELECT AVG(n1.valoare) FROM studenti s1 JOIN note n1 ON
        s1.id=n1.id_student WHERE s1.id<>idStudent 
        AND s1.an=(SELECT an FROM studenti WHERE id=idStudent)
        AND s1.grupa=(SELECT grupa FROM studenti WHERE id=idStudent)
        GROUP BY id_student;
        
        CURSOR prieteni1 IS 
        SELECT id_student1 FROM prieteni WHERE id_student2=idStudent;
        
        CURSOR prieteni2 IS 
        SELECT id_student2 FROM prieteni WHERE id_student1=idStudent;
        
    BEGIN
        DBMS_OUTPUT.PUT_LINE('Infos about the student with the ID '||idStudent||':');
        
        SELECT nr_matricol INTO v_nrMatricol FROM studenti WHERE id=idStudent;
        DBMS_OUTPUT.PUT_LINE('Registration number: '||v_nrMatricol);
        
        SELECT AVG(valoare) INTO v_average FROM note WHERE note.id_student=idStudent;
        DBMS_OUTPUT.PUT_LINE('Average: '||v_average);
        
        SELECT data_nastere INTO v_studentBirthDate FROM studenti WHERE id=idStudent;
        calculateAge(v_studentBirthDate);    

-- ~ ~ ~ ~ ~ ~ ~ ~ ~  Printing student's grades:
        
        DBMS_OUTPUT.PUT_LINE('Student''s grades:');
        DBMS_OUTPUT.PUT_LINE('');
        
        OPEN courseANDgrade;
        
        LOOP
            FETCH courseANDgrade INTO v_courseGrade, v_courseName;
            EXIT WHEN courseANDgrade%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE(v_courseName||'- - ->'||v_courseGrade);
        END LOOP;
        
        CLOSE courseANDgrade;
      
        OPEN average;
        
            LOOP
            
                FETCH average INTO v_fetchAverage;
            
                EXIT WHEN average%NOTFOUND;
            
                IF v_fetchAverage>v_average THEN
                    v_rank:=v_rank+1;
                END IF;
                
            END LOOP;
                 
        CLOSE average;
        
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Student''s rank:'||v_rank);
        
-- ~ ~ ~ ~ ~ ~ ~ ~ ~  Printing student's friends:            
        
        DBMS_OUTPUT.PUT_LINE('');
        DBMS_OUTPUT.PUT_LINE('Student''s friends:');

        OPEN prieteni1;
        OPEN prieteni2;
        
        LOOP
            FETCH prieteni1 INTO v_friend;
        
            EXIT WHEN prieteni1%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE(v_friend);
        END LOOP;
        
        LOOP
            FETCH prieteni2 INTO v_friend;
        
            EXIT WHEN prieteni2%NOTFOUND;
            DBMS_OUTPUT.PUT_LINE(v_friend);
        END LOOP;
        
        CLOSE prieteni1;
        CLOSE prieteni2;
        
    END printStudentInfo;
    
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --   
    
    PROCEDURE removeStudent(idStudent IN studenti.id%TYPE) IS
    BEGIN
        DELETE FROM prieteni WHERE (id_student1=idStudent OR id_student2=idStudent);
        DELETE FROM note WHERE id_student=idStudent;
        DELETE  FROM studenti WHERE id=idStudent;
    END removeStudent;
    
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --    
    
    PROCEDURE addStudent(firstName IN studenti.prenume%TYPE, lastName IN studenti.nume%TYPE) IS 

        v_currentMaxID NUMBER;
        v_nrMatricol VARCHAR2(20);
        v_nrMatricol1 VARCHAR2(20);
        v_nrMatricol2 VARCHAR2(20);
        v_nrMatricol3 VARCHAR2(20);
        v_nrMatricol4 VARCHAR2(20);
        v_an NUMBER;
        v_nota NUMBER;
        v_gradeID NUMBER;
        
        BEGIN 

            SELECT MAX(id) INTO v_currentMaxID FROM studenti;
            v_currentMaxID:=v_currentMaxID+1;      -- ID for the new student
            
            SELECT  v_nrMatricol||TO_CHAR(ROUND(dbms_random.value(100,999)))||CHR(ROUND(dbms_random.value(65,90)))|| CHR(ROUND(dbms_random.value(65,90)))||TO_CHAR(ROUND(dbms_random.value(0,9)))INTO v_nrMatricol4  FROM DUAL ;
            SELECT ROUND(dbms_random.value(1,3)) INTO v_an FROM DUAL;

            INSERT INTO studenti(id,nr_matricol,nume, prenume,an) VALUES(v_currentMaxID,v_nrMatricol4,lastName,firstName,v_an);
            
            SELECT MAX(id) INTO v_gradeID FROM note;
            
            v_gradeID:=v_gradeID+1;
            
             IF v_an=1 THEN
                FOR myCursor IN (SELECT id FROM cursuri WHERE an IN (1))LOOP
                    v_nota:=ROUND(dbms_random.value(1,10));
                  
                    INSERT INTO note (id,id_student,id_curs,valoare)VALUES (v_gradeID,v_currentMaxID,myCursor.id, v_nota);
                     v_gradeID:=v_gradeID+1;
                END LOOP;
            END IF;
            
            IF v_an=2 THEN
                FOR myCursor IN (SELECT id FROM cursuri WHERE an IN (1,2))LOOP
                    v_nota:=ROUND(dbms_random.value(1,10));
                  
                    INSERT INTO note (id,id_student,id_curs,valoare)VALUES (v_gradeID,v_currentMaxID,myCursor.id, v_nota);
                    v_gradeID:=v_gradeID+1;
                END LOOP;
            END IF;
--            
            IF v_an=3 THEN
                FOR myCursor IN (SELECT id FROM cursuri WHERE an IN(1,2,3))LOOP
                    v_nota:=ROUND(dbms_random.value(1,10));
                  
                    INSERT INTO note (id,id_student,id_curs,valoare)VALUES (v_gradeID,v_currentMaxID,myCursor.id, v_nota);
                     v_gradeID:=v_gradeID+1;
                END LOOP;
            END IF;
            
    END addStudent;
    
END collegeManagement;
/

-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --
-- ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ --

set serveroutput on;
    
BEGIN

-- EXERCISE 1:

--calculateAge(TO_DATE('24/07/1969','dd/mm/yyyy'));

-- EXERCISE 2:

--collegeManagement.addStudent('Gabriel', 'Cristea');
   
-- EXERCISE 3:

--collegeManagement.removeStudent(1050);
    
-- EXERCISE 4:

collegeManagement.printStudentInfo(846);

END;