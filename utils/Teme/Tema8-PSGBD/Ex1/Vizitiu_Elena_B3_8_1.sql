CREATE OR REPLACE PROCEDURE export as
  v_fisier UTL_FILE.FILE_TYPE;
  cursor lista_studenti is select* from studenti;
  v_std_linie lista_studenti%ROWTYPE; 
  cursor lista_prieteni is select* from prieteni;
  v_std_prieteni lista_prieteni%ROWTYPE;
BEGIN
  v_fisier:=UTL_FILE.FOPEN('MYDIR','stud.txt','W');
  for v_std_linie in lista_studenti loop
    UTL_FILE.PUTF(v_fisier, v_std_linie.id||'\n'||v_std_linie.nr_matricol||'\n'||v_std_linie.nume||'\n'||v_std_linie.prenume||'\n'||v_std_linie.an||'\n'||v_std_linie.grupa||'\n'||v_std_linie.bursa||'\n'||v_std_linie.data_nastere||'\n'||v_std_linie.email||'\n'||v_std_linie.created_at||'\n'||v_std_linie.updated_at);
  end loop;
  UTL_FILE.FCLOSE(v_fisier);
  
  v_fisier:=UTL_FILE.FOPEN('MYDIR','prieteni.txt','W');
  for v_std_prieteni in lista_prieteni loop
    UTL_FILE.PUTF(v_fisier, v_std_prieteni.id_student1||'\n'||v_std_prieteni.id_student2||'\n'||v_std_prieteni.created_at||'\n'||v_std_prieteni.updated_at);
  end loop;
  UTL_FILE.FCLOSE(v_fisier);
END export;
/

set serveroutput on;
begin
  export;
end;