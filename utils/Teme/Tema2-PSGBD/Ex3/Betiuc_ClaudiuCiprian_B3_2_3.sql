SET SERVEROUTPUT ON;

DECLARE

    v_fetchId NUMBER;
    v_currentId NUMBER;
    v_idOfTheCurrentBest NUMBER;
    v_currentYearOfStudy NUMBER;
    v_fetchYearOfStudy NUMBER;
    v_fetchGrade NUMBER;
    v_gradeSum NUMBER;
    v_gradeCounter NUMBER;
    v_maxAverage NUMBER:=0;
    v_firstTurn BOOLEAN:=TRUE;
    v_needToExit BOOLEAN:=FALSE;
    v_needToInitializeAverage BOOLEAN:=TRUE;   -- it means that we haven't found the first student with at least three grades
    v_currentIdName VARCHAR2(20);
    v_fetchIdName VARCHAR2(20);
    v_bestStudentFirstName VARCHAR2(30);
    v_bestStudentLastName VARCHAR2(20);
    v_courseIdGrades NUMBER;
    v_courseTitle VARCHAR2(51);
    v_courseIdTopics NUMBER;
                                   
    CURSOR idStudentANDvaloare IS 
    SELECT id_student,valoare FROM note ORDER BY id_student;
    
    CURSOR gradesOfTheBestStudent IS
    SELECT valoare, id_curs FROM note WHERE id_student=v_idOfTheCurrentBest;
    
    CURSOR topicsOfTheBestStudent IS
    SELECT id, titlu_curs FROM cursuri;

BEGIN

    OPEN idStudentANDvaloare;
        
    FETCH idStudentANDvaloare INTO v_fetchId, v_fetchGrade;   
    
    LOOP
        EXIT WHEN v_needToExit=TRUE;
        
        IF v_firstTurn=TRUE THEN    -- will run just one time
            v_currentId:=v_fetchId;
            v_gradeSum:=v_fetchGrade;
            v_gradeCounter:=1;
        END IF;
        
        LOOP
            FETCH idStudentANDvaloare INTO v_fetchId, v_fetchGrade;
            
            IF idStudentANDvaloare%NOTFOUND THEN 
                v_needToExit:=TRUE;
                EXIT;
            END IF;
            
            v_gradeSum:=v_gradeSum+v_fetchGrade;
            v_gradeCounter:=v_gradeCounter+1;
            
            EXIT WHEN v_fetchId !=v_currentId;
        END LOOP;
        
        IF v_gradeCounter>=3 AND v_needToInitializeAverage=TRUE THEN
            v_maxAverage:=v_gradeSum/v_gradeCounter;
            v_needToInitializeAverage:=FALSE;
            v_idOfTheCurrentBest:=v_currentId;
        END IF;
        
        IF v_gradeSum/v_gradeCounter>v_maxAverage AND v_gradeCounter>=3 THEN
            v_maxAverage:=v_gradeSum/v_gradeCounter;
            v_idOfTheCurrentBest:=v_currentId;
        ELSIF v_gradeSum/v_gradeCounter=v_maxAverage AND v_gradeCounter>=3 THEN
            SELECT an INTO v_currentYearOfStudy FROM studenti WHERE id=v_idOfTheCurrentBest;
            SELECT an INTO v_fetchYearOfStudy FROM studenti WHERE id=v_currentId;
            IF v_fetchYearOfStudy>v_currentYearOfStudy THEN
                v_idOfTheCurrentBest:=v_currentId;
            ELSIF v_fetchYearOfStudy=v_currentYearOfStudy THEN
                SELECT nume INTO v_currentIdName FROM studenti WHERE id=v_idOfTheCurrentBest;
                SELECT nume INTO v_fetchIdName FROM studenti WHERE id=v_currentId;
                IF v_fetchIdName<v_currentIdName THEN 
                    v_idOfTheCurrentBest:=v_currentId;
                END IF;
            END IF;
        END IF;
        
        v_currentId:=v_fetchId;
        v_gradeSum:=v_fetchGrade;
        v_gradeCounter:=1;
        
        IF v_firstTurn=TRUE THEN
            v_firstTurn:=FALSE;
        END IF;
    
    END LOOP; 
    
    CLOSE idStudentANDvaloare;
    
    SELECT prenume, nume INTO v_bestStudentFirstName,v_bestStudentLastName FROM studenti WHERE id=v_idOfTheCurrentBest;
    
    DBMS_OUTPUT.PUT_LINE('Having compared all the students with at least three grades, the best average is '||TRUNC(v_maxAverage,2));
    DBMS_OUTPUT.PUT_LINE('and it is the result of ' ||v_bestStudentFirstName||' '||v_bestStudentLastName||' with the ID: '||v_idOfTheCurrentBest||'.');
    DBMS_OUTPUT.PUT_LINE('The grades of '||v_bestStudentFirstName||' '||v_bestStudentLastName||':');
    DBMS_OUTPUT.PUT_LINE('');
    
    OPEN gradesOfTheBestStudent;
    OPEN topicsOfTheBestStudent;
    
    LOOP
        FETCH topicsOfTheBestStudent INTO v_courseIdTopics, v_courseTitle;
        EXIT WHEN topicsOfTheBestStudent%NOTFOUND;
        
        LOOP 
            FETCH gradesOfTheBestStudent INTO v_fetchGrade, v_courseIdGrades;
            EXIT WHEN gradesOfTheBestStudent%NOTFOUND;
            
            IF v_courseIdGrades=v_courseIdTopics THEN
                DBMS_OUTPUT.PUT_LINE(v_courseTitle||' --> '||v_fetchGrade);
                EXIT;
            END IF;
        
        END LOOP;
        
    END LOOP;
        
    CLOSE topicsOfTheBestStudent;
    CLOSE gradesOfTheBestStudent;
END;

--Interrogation used to test the result of the code:

--select T1.id, T1.nume, T1.prenume, T1.an, c.titlu_curs, n.valoare, T1."avg" from ( select * from (select  s.id, s.nume, s.prenume, s.an, 
--avg(n.valoare) as "avg" from studenti s join note n on n.id_student=s.id group by s.id, s.nume, s.prenume, s.an, s.nume, s.prenume
--having count(n.valoare) >= 3 order by avg(n.valoare) desc, s.an desc, s.nume asc, s.prenume asc ) where rownum = 1) T1 join note n on
--n.id_student = T1.id join cursuri c on c.id = n.id_curs;
