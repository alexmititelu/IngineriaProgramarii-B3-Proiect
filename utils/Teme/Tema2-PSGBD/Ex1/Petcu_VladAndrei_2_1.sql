set serveroutput on;
drop table tabel;
create table tabel( A integer, B integer); 
declare 
  v_numar integer := 8;
  v_contor integer := 1; 
  v_copie_contor integer := 0;
  v_cifra integer := 0;
  v_suma_cifrelor integer := 0;
  v_contor_primalitate integer := 0;
  v_numar_divizori integer :=0;
  v_valoare_b integer := 0;

begin
 while (v_contor < 10000) loop
    v_copie_contor := v_contor;
  while ( v_copie_contor > 0 ) loop
    v_cifra := mod(v_copie_contor, 10);
    v_copie_contor := floor(v_copie_contor / 10);
    v_suma_cifrelor := v_suma_cifrelor + v_cifra;
  end loop;
    if ( mod(v_suma_cifrelor ,9) = v_numar ) then
      v_numar_divizori := 0 ;
      for v_contor_primalitate in 2..v_contor/2 loop
        if ( mod(v_contor,v_contor_primalitate) = 0 ) then
          v_numar_divizori:= v_numar_divizori + 1;
          end if;
      end loop;
        if (v_numar_divizori = 0) then
            insert into tabel (A,B) values ( v_contor, 1 ); 
        else insert into tabel (A,B) values ( v_contor, 0 ); 
        end if;
    end if;
    v_suma_cifrelor := 0;
    v_copie_contor := 0;
    v_contor := v_contor + 1;
  end loop;
end;




