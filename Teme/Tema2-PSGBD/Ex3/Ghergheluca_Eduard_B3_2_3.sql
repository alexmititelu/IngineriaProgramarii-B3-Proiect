set serveroutput on;
DECLARE
type arrayId IS VARRAY(16312) OF integer;
vectorCount arrayId;
cpyIdStud integer;
suma integer:=0;
counter integer;
media float;
mediaMax float:=0;
studMax integer;
an1 integer;
an2 integer;
contor integer:=1;
gradeContor integer;
nameCpy1 studenti.nume%type;
nameCpy2 studenti.nume%type;
materieCpy cursuri.titlu_curs%type;


BEGIN

    vectorCount := arrayID();
    vectorCount.extend(16312);
    for v_line in (select* from note) loop
        suma := 0;
        gradeContor := 0;
        vectorCount(contor) := v_line.id_student;
        contor := contor+1;
        cpyIdStud := v_line.id_student;
        for v_line2 in (select* from note where id_student = cpyIdStud) loop
            suma := suma + v_line2.valoare;
            gradeContor := gradeContor + 1;
        end loop;
        
        if gradeContor >= 3 then
            media := suma / gradeContor;
            if media > mediaMax then
                mediaMax := media;
                studMax := cpyIdStud;
            end if;
            if media = mediaMax then
                select an into an1 from studenti where id = cpyIdStud;
                select an into an2 from studenti where id = vectorCount(contor-1);
            if an1 > an2 then
                studMax := vectorCount(contor-1);
                vectorCount(contor) := studMax;
                    else
                        if an2 = an1 then 
                            select nume into nameCpy1 from studenti where id =  cpyIdStud;
                            select nume into nameCpy2 from studenti where id = vectorCount(contor-1);
                            if nameCpy1 < nameCpy2 then
                            studMax := vectorCount(contor-1);
                            vectorCount(contor) := studMax;
                            end if;
                        end if;
            end if;
            end if;
        end if;
        
   end loop;
   select nume into nameCpy1 from studenti where id = studMax;
   dbms_output.put_line ('studentul cel mai smecher este' ||' '|| nameCpy1||' si are media '||mediaMax|| ' '||studMax);
   for v_contor3 in (select* from note where id_student = studMax) loop
        select titlu_curs into materieCpy from cursuri where id = v_contor3.id_curs;
        dbms_output.put_line(materieCpy||' '||v_contor3.valoare);
   end loop;
    
END;

select T1.id, T1.nume, T1.prenume, T1.an, c.titlu_curs, n.valoare, T1."avg" from ( select * from (select  s.id, s.nume, s.prenume, s.an, 
avg(n.valoare) as "avg" from studenti s join note n on n.id_student=s.id group by s.id, s.nume, s.prenume, s.an, s.nume, s.prenume
having count(n.valoare) >= 3 order by avg(n.valoare) desc, s.an desc, s.nume asc, s.prenume asc ) where rownum = 1) T1 join note n on
n.id_student = T1.id join cursuri c on c.id = n.id_curs;