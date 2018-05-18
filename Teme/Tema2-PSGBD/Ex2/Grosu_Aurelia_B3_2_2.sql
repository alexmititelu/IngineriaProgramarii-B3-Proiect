set serveroutput on;
DECLARE
v_numere_schimbate INT := 0;
CURSOR v_line IS SELECT * FROM EXERCITIU FOR UPDATE OF B NOWAIT;
BEGIN

      FOR v_cursor IN v_line LOOP
          IF (TRUNC(SQRT(5*v_cursor.A*v_cursor.A+4)) = SQRT(5*v_cursor.A*v_cursor.A+4) OR 
              TRUNC(SQRT(5*v_cursor.A*v_cursor.A-4)) = SQRT(5*v_cursor.A*v_cursor.A-4))
          THEN
            IF v_cursor.B <> 1 THEN
              UPDATE EXERCITIU SET B=1 WHERE CURRENT OF v_line;
              v_numere_schimbate:=v_numere_schimbate+1;
            END IF;
            ELSE
            IF v_cursor.B <> 0 THEN
               UPDATE EXERCITIU SET B=0 WHERE CURRENT OF v_line;
               v_numere_schimbate:=v_numere_schimbate+1;
             END IF; 
          END IF;
      END LOOP;
      
      DBMS_OUTPUT.PUT_LINE('Numarul de schimbari este: '||v_numere_schimbate||'.');
END;

