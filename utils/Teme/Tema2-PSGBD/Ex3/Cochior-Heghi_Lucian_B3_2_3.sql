set serveroutput on;
declare
cursor v_studenti is select  * from studenti order by nume;
cursor v_note is select * from note;
v_medieMaxima float := 0;
v_medie float := 0;
v_idMedieMaxima studenti.id%type;
v_anMedieMaxima studenti.an%type;
v_sumaNote float :=0 ;
v_nrNote int := 0;
v_nume studenti.nume%type;
v_prenume studenti.prenume%type;
begin

    for v_student in v_studenti loop
        v_nrNote := 0;
        v_medie := 0;
        v_sumaNote := 0;
        for v_nota in v_note loop
            if(v_student.id = v_nota.id_student)then
                v_sumaNote := v_sumaNote + v_nota.valoare;
                v_nrNote := v_nrNote + 1;
            end if;
        end loop;
        if(v_nrNote >= 3)then
            v_medie := v_sumaNote/ v_nrNote;
            if(v_medie > v_medieMaxima)then
                v_medieMaxima := v_medie;
                v_idMedieMaxima := v_student.id;
                v_anMedieMaxima := v_student.an;
            elsif (v_medie = v_medieMaxima)then
                if(v_anMedieMaxima < v_student.an)then
                     v_medieMaxima := v_medie;
                     v_idMedieMaxima := v_student.id;
                     v_anMedieMaxima := v_student.an;
                end if;
            end if;
        
        
        end if;
        
        
    end loop;
    
    select nume into v_nume from studenti where id = v_idMedieMaxima;
     select prenume into v_prenume from studenti where id = v_idMedieMaxima;
    DBMS_OUTPUT.put_line('Nume-Prenume medie maxima: '||v_nume||' '|| v_prenume);
    DBMS_OUTPUT.put_line('Medie: '||v_medieMaxima);
    DBMS_OUTPUT.put_line('ID:: '||v_idMedieMaxima);
    
    for v_nota in (select * from note where id_student = v_idMedieMaxima) loop
        for v_materie in (select * from cursuri where id = v_nota.id_curs)loop
             DBMS_OUTPUT.put_line(v_materie.titlu_curs||' '||v_nota.valoare);
        end loop;
    end loop;

end;

select *from lab2tema;
select T1.id, T1.nume, T1.prenume, T1.an, c.titlu_curs, n.valoare, T1."avg" from ( select * from (select  s.id, s.nume, s.prenume, s.an, 
avg(n.valoare) as "avg" from studenti s join note n on n.id_student=s.id group by s.id, s.nume, s.prenume, s.an, s.nume, s.prenume
having count(n.valoare) >= 3 order by avg(n.valoare) desc, s.an desc, s.nume asc, s.prenume asc ) where rownum = 1) T1 join note n on
n.id_student = T1.id join cursuri c on c.id = n.id_curs;