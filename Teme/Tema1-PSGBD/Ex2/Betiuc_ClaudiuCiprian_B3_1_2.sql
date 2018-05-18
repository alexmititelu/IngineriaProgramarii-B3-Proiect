set serveroutput on;

DECLARE

myBirthDate CONSTANT VARCHAR2(30):='09-08-1989';
dayOfBirth VARCHAR(30);
dateOfBirth_Today_PassedDays number;
dateOfBirth_Today_PassedMonths number;

BEGIN
 
 SELECT FLOOR(MONTHS_BETWEEN(SYSDATE,TO_DATE(myBirthDate,'DD-MM-YYYY'))) INTO dateOfBirth_Today_PassedMonths FROM DUAL;
 SELECT CEIL((ADD_MONTHS(SYSDATE,-FLOOR(MONTHS_BETWEEN(SYSDATE,TO_DATE(myBirthDate,'DD-MM-YYYY')))))-TO_DATE(myBirthDate,'DD-MM-YYYY')) INTO dateOfBirth_Today_PassedDays FROM DUAL;
 
 DBMS_OUTPUT.PUT_LINE('Since you were born, '||dateOfBirth_Today_PassedMonths||' months and '||dateOfBirth_Today_PassedDays||' days have passed.'); 
 
 SELECT TO_CHAR(TO_DATE(myBirthDate,'DD-MM-YYYY'),'Day')INTO dayOfBirth FROM DUAL;
 
 DBMS_OUTPUT.PUT_LINE('You were born in a '||dayOfBirth||'.');
 
END;