set serveroutput on;

declare
  v_data_nastere varchar(30) := '14-06-1998';
  v_numar_luni number := 0;
  v_numar_zile number := 0;
  v_ziua_nastere varchar(20);
  
begin

  select months_between(sysdate, to_date(v_data_nastere,'dd-mm-yyyy')) into v_numar_luni from sys.dual; 
  select  sysdate - to_date(v_data_nastere,'dd-mm-yyyy') into v_numar_zile from sys.dual;

  dbms_output.put_line('Numar zile: ' || v_numar_zile );
  dbms_output.put_line('Numar luni: ' || v_numar_luni);
  
  select to_char( to_date(v_data_nastere,'dd-mm-yyyy'), 'day', 'nls_language=english') into v_ziua_nastere from sys.dual;
  dbms_output.put_line('Ziua nasterii: ' || v_ziua_nastere);
  

end;
  