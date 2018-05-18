set serveroutput on;

create or replace procedure mareste_bursa(p_id studenti.id%type, p_valoare number) is
  v_bursa_initiala studenti.bursa%type;
  v_bursa_finala studenti.bursa%type;
  bursa_prea_mare exception;
  pragma exception_init(bursa_prea_mare, -20001);
  bursa_mare exception;
  pragma exception_init(bursa_mare, -20002);
begin
  select bursa into v_bursa_initiala  from studenti where id = p_id;
  if (v_bursa_initiala is null ) then
    v_bursa_initiala := 0;
  end if;
  v_bursa_finala := v_bursa_initiala + p_valoare;
  update studenti set bursa = v_bursa_finala where id = p_id;
  if (v_bursa_finala > 3000) then
    update studenti set bursa = 3000 where id = p_id;
    raise bursa_prea_mare;
  end if;
  exception
    when bursa_prea_mare then
      dbms_output.put_line('Bursa trunchiata la 3000 pt. studentul cu id = ' || p_id);

      
end mareste_bursa;
/
declare 
  id_student studenti.id%type := 15;
begin
  --for i in 300..400 loop
   -- select id into id_student from studenti where id=i;
    mareste_bursa(id_student, 3500);
 -- end loop;
end;

--update studenti set bursa = null where id<400; 