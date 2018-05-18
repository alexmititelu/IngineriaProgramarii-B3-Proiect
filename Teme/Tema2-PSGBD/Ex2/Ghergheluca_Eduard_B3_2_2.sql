set serveroutput on;
--DBMS_OUTPUT.PUT_LINE()
DECLARE
CURSOR update_B IS select* from Tabel FOR UPDATE OF B NOWAIT;
updated_rows integer;
suma integer:=0;
BEGIN
    for v_linie in update_B loop
        if trunc(sqrt(5*v_linie.A*v_linie.A+4))=sqrt(5*v_linie.A*v_linie.A+4)
        or trunc(sqrt(5*v_linie.A*v_linie.A-4))=sqrt(5*v_linie.A*v_linie.A-4)
        then 
            if v_linie.B <>1 then
            UPDATE Tabel SET B=1 WHERE CURRENT OF update_B;
            suma := suma+1;
            end if;
            else 
            if v_linie.B <> 0 then
            suma := suma +1;
            UPDATE Tabel SET B=0 WHERE CURRENT OF update_B;
            end if;
        end if;
    end loop; 
    
   DBMS_OUTPUT.PUT_LINE('numarul de linii actualizate este:'||' '||suma);
    

END;