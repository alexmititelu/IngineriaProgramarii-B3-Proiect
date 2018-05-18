DROP TABLE numere CASCADE CONSTRAINTS
/

CREATE TABLE numere (
 numar number,
 prim number(1)
)
/

SET SERVEROUTPUT ON;
DECLARE
  v_contor INTEGER;
  v_suma number;
  v_aux number;  
  v_aux_pentru_suma number;
  c_valoare_start constant number := 7;
  v_deimpartit number;
BEGIN
  FOR v_contor IN 1..10000 LOOP
      v_aux := v_contor;
      v_suma := 0;
      WHILE (v_aux > 0) LOOP       
       v_suma := v_suma + mod(v_aux,10);  
       v_aux := FLOOR(v_aux/10);
      END LOOP; 
      IF (mod(v_suma,9) = c_valoare_start) 
            THEN 
                v_aux := v_contor;
                v_deimpartit := 2;
                WHILE(v_deimpartit < v_aux) LOOP
                  IF (mod(v_aux,v_deimpartit) = 0)
                     THEN 
                         v_aux := FLOOR(v_aux/v_deimpartit);
                     ELSE
                         v_deimpartit := v_deimpartit + 1;
                     END IF;
                END LOOP;
                  IF (v_contor = v_deimpartit)
                     THEN 
                         INSERT INTO NUMERE values(v_contor, 1);
                     ELSE
                         INSERT INTO NUMERE values(v_contor, 0);
                     END IF;
       END IF;
   END LOOP;

END;

select *from numere;
