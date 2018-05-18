set SERVEROUTPUT ON;

declare

  data_nastere varchar2(30) := '11-09-1997';
  
begin

DBMS_OUTPUT.PUT_LINE('Numarul de zile este ' || trunc((sysdate - to_date(data_nastere, 'DD-MM-YYYY'))/365) || ' si numarul de luni ' || trunc(months_between(sysdate, to_date(data_nastere, 'DD-MM-YYYY'))));
DBMS_OUTPUT.PUT_LINE(to_char(to_date(data_nastere, 'DD-MM-YYYY'), 'day'));

end;
