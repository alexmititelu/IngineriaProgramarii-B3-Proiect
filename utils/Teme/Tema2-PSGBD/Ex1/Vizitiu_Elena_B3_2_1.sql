set serveroutput on;
drop table numere;
create table numere(A int, B int);
declare
  c_valoare constant integer:=5;
  v_valoare numere.A%type:=1;
  v_indice numere.B%type:=1;
  v_sum int;
  v_copie numere.A%type;
begin
  while v_valoare < 10000 loop
    v_sum:=0;
    v_copie:=v_valoare;
    While v_copie!=0 loop
      v_sum:=v_sum+mod(v_copie,10);
      v_copie:=floor(v_copie/10);
    end loop;
    v_indice:=1;
      if mod(v_sum,9)=c_valoare then
        For i in 2..v_valoare/2 loop
          if(mod(v_valoare,i)=0) then
            v_indice:=0;
          end if;
        end loop;
      insert into numere values(v_valoare,v_indice);
      end if;
  v_valoare:=v_valoare+1;
  end loop;
end; 

  
