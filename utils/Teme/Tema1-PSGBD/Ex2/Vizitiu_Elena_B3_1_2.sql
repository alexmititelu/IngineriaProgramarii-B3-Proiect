set serveroutput on;
declare
v_data_nastere varchar2(30):='09-05-1997';
begin
  dbms_output.put_line('Numarul de luni trecute pana in prezent: '||floor(months_between(sysdate, to_date(v_data_nastere,'DD-MM-YYYY'))));
  dbms_output.put_line('Si numarul de zile trecute pana in prezent: '||floor((sysdate-to_date(v_data_nastere,'DD-MM-YYYY'))/365));
  dbms_output.put_line('Ziua saptamanii din data nasterii: ' || to_char(to_date(v_data_nastere,'DD-MM-YYYY'),'DAY','nls_date_language=romanian'));
end;