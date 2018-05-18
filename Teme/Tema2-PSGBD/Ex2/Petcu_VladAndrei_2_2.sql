set serveroutput on;
declare 
  cursor update_b is
        select * from tabel for update of B nowait;    
  v_valori tabel.A%type;
  v_fibo_zero integer := 0;
  v_fibo_unu integer := 1;
  v_fibonacci integer := 0;
  v_auxiliar integer := 0;
  v_numar integer := 0;
begin 
  --open update_b;
    for v_valori in update_b loop
      v_fibo_zero := 0;
      v_fibo_unu := 1;
      v_fibonacci := 0;
      while (v_valori.A > v_fibonacci) loop
        v_fibonacci := v_fibo_zero + v_fibo_unu;
        if (v_fibonacci = v_valori.A) then
           if (v_valori.B = 0 ) then
            update tabel set B = 1 where current of update_b;
            v_numar := v_numar + 1;
           end if;
        end if;
        v_fibo_zero := v_fibo_unu;
        v_fibo_unu := v_fibonacci;
      end loop;
        if (v_fibonacci > v_valori.A) then
          if (v_valori.B = 1 ) then
          update tabel set B = 0 where current of update_b;
            v_numar := v_numar + 1;
           end if;
        end if;
    end loop;
  --close update_b;
  dbms_output.put_line('Numar de valori updatate: ' || v_numar);
end;