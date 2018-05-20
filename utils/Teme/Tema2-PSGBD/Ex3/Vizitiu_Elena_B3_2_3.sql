set serveroutput on;
DECLARE
v_1 INT:=0; 
v_2 INT:=1;
v_f INT;
v_valoare int;
v_nr_schimbari int:=0;
   CURSOR update_indice IS
      SELECT * FROM numere FOR UPDATE OF B NOWAIT;
BEGIN
   FOR v_linie IN update_indice LOOP
   v_valoare:=v_linie.A;
   v_1:=0;
   v_2:=1;
    loop
      v_f:=v_1+v_2;
      v_1:=v_2; 
      v_2:=v_f;
      exit when v_f>=v_valoare;
    end loop;
    if v_valoare=v_f then
      if v_linie.B=0 then
        UPDATE numere SET B=1 WHERE CURRENT OF update_indice;
        v_nr_schimbari:=v_nr_schimbari+1;
      end if;
    else
        if v_linie.B=1 then
          UPDATE numere SET B=0 WHERE CURRENT OF update_indice;
          v_nr_schimbari:=v_nr_schimbari+1;
     END IF;
    end if;
   END LOOP;
   dbms_output.put_line('Numar schimbari '||v_nr_schimbari);
END;