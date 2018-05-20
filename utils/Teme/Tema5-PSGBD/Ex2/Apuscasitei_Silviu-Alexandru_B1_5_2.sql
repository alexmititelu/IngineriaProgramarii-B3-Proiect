CREATE OR REPLACE FUNCTION update_bursa(p_id IN CHAR) RETURN NUMBER AS

  CURSOR update_bursa IS
    SELECT * FROM studenti FOR UPDATE OF bursa NOWAIT;
    
  majorant_bursa NUMBER := 2000;
  bursa_student studenti.bursa%type;
  bursa_noua NUMBER;
  message_failed VARCHAR2(100);
  bursa_prea_mare EXCEPTION;
  PRAGMA EXCEPTION_INIT(bursa_prea_mare, -20001);

BEGIN

  SELECT bursa INTO bursa_student FROM studenti WHERE id = p_id;
  bursa_noua := bursa_student + majorant_bursa;
  
  IF (bursa_noua > 3000) 
    THEN
      bursa_noua := 3000;
  END IF;
    
  FOR v_linie IN update_bursa LOOP
    IF (v_linie.id = p_id) 
        THEN 
          UPDATE studenti SET bursa=bursa_noua WHERE CURRENT OF update_bursa;
    END IF; 
  END LOOP;
  COMMIT;
  
  bursa_noua := bursa_student + majorant_bursa;
  IF(bursa_noua > 3000)
   then
    raise bursa_prea_mare;
    end if;
RETURN bursa_student;
END update_bursa;


SET SERVEROUTPUT ON;  
DECLARE
    rand_n pls_integer;
    v_functie NUMBER;
    bursa_prea_mare EXCEPTION;
    message_failed VARCHAR2(100);
    PRAGMA EXCEPTION_INIT(bursa_prea_mare, -20001);
BEGIN
  FOR i IN 1..100 LOOP
  BEGIN
    rand_n := dbms_random.value(1,1025);
    v_functie := update_bursa(rand_n);
    DBMS_OUTPUT.PUT_LINE(i ||' '||rand_n);
  EXCEPTION
  WHEN bursa_prea_mare THEN
    message_failed := 'bursa mai mare de 3000';
    DBMS_OUTPUT.PUT_LINE(message_failed);
  END;
  END LOOP;
END;

select bursa from studenti where bursa > 1999;

select * from studenti where bursa is not null;


