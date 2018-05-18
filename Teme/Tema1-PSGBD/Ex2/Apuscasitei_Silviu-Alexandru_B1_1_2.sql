set serveroutput on;
DECLARE
   c_data_nastere VARCHAR2(30) := &data_nastere;
   v_numar_de_luni NUMBER;
   v_data_nastere_aux VARCHAR2(30) := c_data_nastere;
   v_numar_de_zile NUMBER;
BEGIN
   DBMS_OUTPUT.PUT_LINE('Data nastere: '||c_data_nastere);
   SELECT FLOOR(MONTHS_BETWEEN(sysdate, to_date(c_data_nastere,'DD-MM-YYYY'))) INTO v_numar_de_luni FROM DUAL;
   SELECT FLOOR(sysdate - ADD_MONTHS(to_date(v_data_nastere_aux,'DD-MM-YYYY'), v_numar_de_luni)) INTO v_numar_de_zile FROM DUAL;
   DBMS_OUTPUT.PUT_LINE('Nr de zile: ' || v_numar_de_zile);
   DBMS_OUTPUT.PUT_LINE('Nr de luni: '|| v_numar_de_luni);
   DBMS_OUTPUT.PUT_LINE('Ziua respectiva: '|| to_char(to_date(c_data_nastere, 'DD-MM-YYYY'), 'Day'));
END;
