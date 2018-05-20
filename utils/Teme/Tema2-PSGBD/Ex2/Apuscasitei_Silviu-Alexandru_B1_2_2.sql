SET SERVEROUTPUT ON;
DECLARE 
   CURSOR update_numere IS
      SELECT * FROM numere FOR UPDATE OF prim NOWAIT;
      
   v_fibo_a number;
   v_fibo_b number;
   v_fibo_c number;
   v_numar_update number := 0;
BEGIN
   FOR v_linie IN update_numere LOOP
      v_fibo_a := -1;
      v_fibo_b := 1;
      v_fibo_c := 0;
      WHILE (v_fibo_c < v_linie.numar) LOOP
       v_fibo_a := v_fibo_b;
       v_fibo_b := v_fibo_c;
       v_fibo_c := v_fibo_a + v_fibo_b;
      END LOOP; 
       IF (v_linie.numar = v_fibo_c) 
            THEN 
              IF (v_linie.prim = 0)
                THEN
                 UPDATE numere SET prim = 1 WHERE CURRENT OF update_numere;
                 v_numar_update := v_numar_update + 1;
                END IF;
            ELSE
                IF (v_linie.prim = 1)
                  THEN
                    UPDATE numere SET prim = 0 WHERE CURRENT OF update_numere;
                    v_numar_update := v_numar_update + 1;
                  END IF;
       END IF;
   END LOOP; 
   DBMS_OUTPUT.PUT_LINE('Numar de update-uri: ' || v_numar_update);
END;


