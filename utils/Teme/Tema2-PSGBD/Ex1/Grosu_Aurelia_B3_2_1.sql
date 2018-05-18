DROP TABLE EXERCITIU CASCADE CONSTRAINTS
/

CREATE TABLE EXERCITIU(
A INT,
B INT
)
/
commit

set serveroutput on;

DECLARE
  c_constanta INT := 5;
  v_i INT := 0;
  suma INT := 0;
  v_i1 INT := 0;
  v_prim INT;
  v_nr INT;
  
BEGIN 
  FOR v_i IN 1..10000 LOOP 
  v_i1 := v_i;
  suma := 0;
  v_prim := 1;
  WHILE (v_i1 > 0) LOOP
  suma := suma + v_i1 MOD 10;
  v_i1 := TRUNC(v_i1/10);
  END LOOP;
 IF (suma MOD 9 = c_constanta) THEN 
  FOR v_nr IN 2..TRUNC(v_i/2) LOOP
    
   IF (v_i MOD v_nr=0) THEN 
    v_prim := 0;
    EXIT;
  
   END IF;
  END LOOP;
  INSERT INTO EXERCITIU (A,B) VALUES (v_i,v_prim);
END IF;
  END LOOP;
END;
