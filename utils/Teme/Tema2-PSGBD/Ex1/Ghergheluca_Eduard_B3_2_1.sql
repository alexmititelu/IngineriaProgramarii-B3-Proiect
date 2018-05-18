set serveroutput on;
--DBMS_OUTPUT.PUT_LINE
drop table Tabel;
create table Tabel(A integer, B integer);
DECLARE
contor int;
copieContor int;
sumaCifre int;
cifra int;
cnst int:=5;
contor2 int;
ok int;
BEGIN
    for contor in 1..10000 loop    
        ok:=0;
        sumaCifre:=0;
        copieContor:=contor;
        while copieContor <> 0  loop
            cifra:=copieContor mod 10;
            sumaCifre:=sumaCifre+cifra;
            copieContor:=trunc(copieContor/10);
        end loop; 
      if sumaCifre mod 9 = cnst then
            for contor2 in 2..trunc(contor/2) loop
                    if contor mod contor2 = 0 then
                        ok:=1;
                        exit;
                    end if;
            end loop;
            if ok = 1 then
            insert into Tabel (A,B) values (contor,0);
            else insert into Tabel (A,B) values (contor,1);
            end if;
        end if;
        
end loop;    
END;

