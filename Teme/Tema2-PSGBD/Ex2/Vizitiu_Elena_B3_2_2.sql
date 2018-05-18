set serveroutput on;
declare
  v_avg double;
  v_count int:=0;
  v_media_max double;
  cursor lista_id is
    select* from studenti;
  cursor lista_note is
    select* from note;
  cursor lista_cursuri is
    select* from cursuri;
  v_std_linie lista_id%ROWTYPE;
begin
    for v_studenti in lista_id loop
      select count(valoare) into v_count from note where id_student=v_studenti.id;
    if(v_count>=3) then
      select avg(valoare) into v_avg from note where id_student=v_studenti.id;
    end if;
    if(v_avg>v_media_max)then
      v_std_linie:=v_studenti;
      v_avg:=v_media_max;
    end if;
    end loop;
end;