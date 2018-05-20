create or replace procedure majoreaza_bursa (v_id studenti.id%type) is
  bursa_exception exception;
  v_bursa studenti.bursa%type;
  PRAGMA EXCEPTION_INIT(bursa_exception, -20001);
begin
  update studenti
  set bursa=bursa+300
  where id=v_id;
  select bursa into v_bursa from studenti where id = v_id;
    if v_bursa > 3000 then
      dbms_output.put_line('Studentul cu id-ul: '||v_id||' are bursa'||v_bursa);
      update studenti
      set bursa=3000
      where id=v_id;
      raise bursa_exception;
    end if;
end majoreaza_bursa;
/
set serveroutput on;
DECLARE
  bursa_exception exception;
  v_a studenti.bursa%type;
  v_b studenti.bursa%type;
  PRAGMA EXCEPTION_INIT(bursa_exception, -20001);
begin
  for v_linie in(select* from(select*from studenti where bursa is not null)
  where rownum<102)loop
  begin
    majoreaza_bursa(v_linie.id);
  exception
  when bursa_exception then
          --v_a:=v_linie.bursa-3000;
          --v_b:=300-v_a;
      dbms_output.put_line('Studentul cu id-ul: '||v_linie.id||' are bursa prea mare');
  --raise_application_error (-20001,'Studentul cu id-ul: '||v_linie.id||' are bursa prea mare');
  end;
  end loop;
  
  for v_studenti in(select* from(select* from studenti where bursa is not null) where rownum<11) loop
  dbms_output.put_line('studentul cu id_ul: '||v_studenti.id||' are bursa de: '||v_studenti.bursa||' majorata cu 300');
  end loop;
end;

