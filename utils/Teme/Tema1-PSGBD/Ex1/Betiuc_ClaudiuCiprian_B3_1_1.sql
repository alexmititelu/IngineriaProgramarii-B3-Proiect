set serveroutput on;

DECLARE

input_lastName studenti.nume%TYPE;
lastName_counter number;

BEGIN

DBMS_OUTPUT.PUT_LINE('Please enter the last name:');
input_lastName:='&label';

SELECT COUNT(nume) INTO lastName_counter FROM studenti WHERE nume=input_lastName;

IF lastName_counter=0
    THEN DBMS_OUTPUT.PUT_LINE('The entered surname was not found in the data base.');
ELSE 
    ---- First dot in the problem ----
    DBMS_OUTPUT.PUT_LINE('1. Result: '||lastName_counter ||' students were found with the same entered last name.');
    
    ---- Second dot in the problem ----
    
    DECLARE
    
    student_firstName studenti.prenume%TYPE;
    student_id studenti.id%TYPE;
    lowestGrade note.valoare%TYPE;
    largestGrade note.valoare%TYPE;
    
    BEGIN
    
    SELECT id,prenume INTO student_id, student_firstName FROM(SELECT id, prenume FROM studenti WHERE nume=input_lastName ORDER BY prenume) WHERE ROWNUM=1 ;
    DBMS_OUTPUT.PUT_LINE('2. ID/first name of the student which is the first (in lexicographic order) student with the entered last name: '||student_id||'/'||student_firstName); 
    
    ---- Third dot in the problem ----
    
    SELECT MAX(valoare) INTO largestGrade FROM note WHERE id_student=student_id;
    SELECT MIN(valoare) INTO lowestGrade FROM note WHERE id_student=student_id;
    
    DBMS_OUTPUT.PUT_LINE('3. The largest grade of student with the id no.'||student_id||' is '||largestGrade); 
    DBMS_OUTPUT.PUT_LINE('   The smallest grade of student with the id no.'||student_id||' is '||lowestGrade); 
    
    ---- Fourth dot in the problem ----
    
    DBMS_OUTPUT.PUT_LINE('4. largestGrade^lowestGrade='||POWER(largestGrade, lowestGrade));
    
    END;
    
END IF;  

END;     