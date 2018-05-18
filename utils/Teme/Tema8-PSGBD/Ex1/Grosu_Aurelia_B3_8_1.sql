create or replace procedure export_tabele as
   CURSOR lista_studenti  IS SELECT id,nr_matricol,nume,prenume,an,grupa,bursa,data_nastere,email,created_at,updated_at FROM studenti order by id asc;
   CURSOR lista_prieteni IS SELECT id,id_student1,id_student2,created_at,updated_at FROM prieteni order by id asc;
   v_tabel_studenti UTL_FILE.FILE_TYPE;
   v_std_linie lista_studenti%ROWTYPE;
   v_tabel_prieteni UTL_FILE.FILE_TYPE;
   v_std_linie2 lista_prieteni%ROWTYPE;
BEGIN
    v_tabel_studenti:=UTL_FILE.FOPEN('MYDIR','inserare_studenti.txt','W');
    dbms_output.put_line('Exportam studentii in fisierul inserare_studenti.txt .');
    UTL_FILE.PUTF(v_tabel_studenti,'Primul student este:\n');
    FOR v_std_linie IN lista_studenti LOOP 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.id||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.nr_matricol||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.nume||'\n');  
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.prenume||'\n');  
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.an||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.grupa||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.bursa||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.data_nastere||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.email||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.created_at||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,v_std_linie.updated_at||'\n'); 
      UTL_FILE.PUTF(v_tabel_studenti,'\n');
      UTL_FILE.PUTF(v_tabel_studenti,'Urmatorul student este:\n');
    END LOOP;
    dbms_output.put_line('Exportarea studentilor s-a finalizat!');
    UTL_FILE.FCLOSE(v_tabel_studenti);
    v_tabel_prieteni:=UTL_FILE.FOPEN('MYDIR','inserare_prieteni.txt','W');
    dbms_output.put_line('Exportam prietenii in fisierul inserare_prieteni.txt .');
    UTL_FILE.PUTF(v_tabel_prieteni,'Primii prieteni sunt:\n');
    FOR v_std_linie2 IN lista_prieteni LOOP 
      UTL_FILE.PUTF(v_tabel_prieteni,v_std_linie2.id||'\n'); 
      UTL_FILE.PUTF(v_tabel_prieteni,v_std_linie2.id_student1||'\n'); 
      UTL_FILE.PUTF(v_tabel_prieteni,v_std_linie2.id_student2||'\n');  
      UTL_FILE.PUTF(v_tabel_prieteni,v_std_linie2.created_at||'\n'); 
      UTL_FILE.PUTF(v_tabel_prieteni,v_std_linie2.updated_at||'\n');
      UTL_FILE.PUTF(v_tabel_prieteni,'\n');
      UTL_FILE.PUTF(v_tabel_prieteni,'Urmatorii prieteni sunt:\n');
    END LOOP;
    dbms_output.put_line('Exportarea prieteniilor s-a finalizat!');
    UTL_FILE.FCLOSE(v_tabel_prieteni);
    
END export_tabele;
/
set serveroutput on;
begin
  export_tabele();
end;
