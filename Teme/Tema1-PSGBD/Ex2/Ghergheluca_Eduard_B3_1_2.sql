set serveroutput on;

DECLARE
bthDay varchar2(30):='13-05-1997';
bth1 date:=to_date(bthDay, 'dd-mm-yyyy');
bth2 date:=to_date('13-05-2018', 'dd-mm-yyyy');
rslt number(30);
rslt2 number(30);
rslt3 varchar2(30);
rslt4 date;
rslt5 number(30);


BEGIN

select months_between(sysdate, bth1) into rslt2 from dual;
dbms_output.put_line('Numarul de luni este:'||' '||rslt2);

select to_char(bth1,'day', 'nls_date_language=romanian') into rslt3 from dual;
dbms_output.put_line('Ziua din saptamana este:'||' '||rslt3);

select add_months(bth1,rslt2) into rslt4 from dual;
select abs(sysdate-rslt4) into rslt5 from dual;
dbms_output.put_line('Zile pana la nastere:'||' '||rslt5);

END;
