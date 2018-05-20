CREATE OR REPLACE PROCEDURE scriere as
  fis UTL_FILE.FILE_TYPE;
  s VARCHAR2(500);
BEGIN
     fis:=UTL_FILE.FOPEN('MYDIR','studenti.txt','W');
     
  for st in (SELECT * FROM studenti) loop 

  s:=(st.id|| ';' || st.nr_matricol || ';' || st.nume || ';' || st.prenume ||  ';'||st.an || ';' || st.grupa || ';'|| st.bursa || ';' || st.data_nastere|| ';' || st.email || ';' || st.created_at || ';' || st.updated_at);
  utl_file.put_line(fis, s);

  end LOOP;

  UTL_FILE.FCLOSE_all();
  
    fis:=UTL_FILE.FOPEN('MYDIR','prieteni.txt','W');
   
    for pr in (SELECT * FROM prieteni) loop 

  utl_file.put_line(fis, pr.id || ',' || pr.id_student1 || ',' || pr.id_student2 || ',' || pr.created_at || ','||pr.updated_at  );

  end LOOP;

  UTL_FILE.FCLOSE_all();
  

END scriere;

BEGIN
scriere();
END;