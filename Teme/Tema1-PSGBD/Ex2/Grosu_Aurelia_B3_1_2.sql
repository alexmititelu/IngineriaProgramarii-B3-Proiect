set serveroutput on;
DECLARE
  data_mea_de_nastere varchar2(30) := '05-09-1997';
  
BEGIN
  DBMS_OUTPUT.PUT_LINE('Numarul de zile este: '||abs(extract(day from sysdate)-extract(day from to_date(data_mea_de_nastere,'DD-MM-YYYY')))||', iar numarul de luni este: '||round(months_between(sysdate,to_date(data_mea_de_nastere,'DD-MM-YYYY')))||'.');
  DBMS_OUTPUT.PUT_LINE('Ziua in care m-am nascut este: '||to_char(to_date(data_mea_de_nastere,'DD-MM-YYYY'),'Day')||'.');
END;