set serveroutput on;

declare
  cursor lista_studenti is
    select * from studenti; 
  v_numar_note integer := 0;
  v_suma_note integer := 0;
  v_media float := 0;
  v_medie_maxima float := 0;
  v_save_id studenti.id%type;
  v_save_idcurs cursuri.id%type;
begin
   for v_std_linie in lista_studenti loop
      v_numar_note := 0;
      v_suma_note := 0;
      v_media := 0;
      for v_std_linie_note in (select * from note) loop
            if (v_std_linie_note.id_student = v_std_linie.id) then
              v_suma_note := v_suma_note + v_std_linie_note.valoare;
              v_numar_note := v_numar_note + 1;
            end if;
      end loop;
      if (v_numar_note >= 3) then
      v_media := v_suma_note / v_numar_note;
        if (v_media > v_medie_maxima) then
          v_medie_maxima := v_media;
          v_save_id := v_std_linie.id;
        end if;
        if (v_media = v_medie_maxima) then
          for v_std_linie_studs in (select * from studenti) loop
           if (v_std_linie_studs.id = v_save_id) then
            if ( v_std_linie_studs.an > v_std_linie.an ) then
              v_save_id := v_std_linie_studs.id;
            end if;
              if ( v_std_linie_studs.an = v_std_linie.an) then
                if (v_std_linie.nume > v_std_linie_studs.nume) then
                  v_save_id := v_std_linie_studs.id;
                end if;
                if ( v_std_linie.nume < v_std_linie_studs.nume )  then
                  v_save_id := v_std_linie.id;
                end if;
                if (v_std_linie.nume = v_std_linie_studs.nume) then
                  if (v_std_linie.prenume > v_std_linie_studs.prenume) then
                    v_save_id := v_std_linie_studs.id;
                  end if;
                  if ( v_std_linie.prenume < v_std_linie_studs.prenume )  then
                    v_save_id := v_std_linie.id;
                  end if; 
                end if;
              end if;
           end if;
          end loop;
        end if;
        end if;
   end loop;
   for v_std_linie in lista_studenti loop
      if(v_std_linie.id = v_save_id) then
        dbms_output.put_line('Nume student: ' || v_std_linie.nume);
      end if;
   end loop; 
   dbms_output.put_line('Media este: ' || trunc(v_medie_maxima,2));
   dbms_output.put_line('Notele sunt: ');
  for v_std_linie_note in (select * from note) loop
    if (v_save_id = v_std_linie_note.id_student) then
      v_save_idcurs := v_std_linie_note.id_curs;
      for v_std_linie_cursuri in (select * from cursuri) loop
        if (v_std_linie_cursuri.id = v_save_idcurs) then
          dbms_output.put_line(v_std_linie_note.valoare || ' la ' || v_std_linie_cursuri.titlu_curs);
        end if;
      end loop;
    end if;
  end loop;
end;