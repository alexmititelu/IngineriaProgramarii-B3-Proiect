--DROP TABLE PRIETENI CASCADE CONSTRAINTS;
--DROP TABLE STUDENTI CASCADE CONSTRAINTS;

--CREATE TABLE studenti (
--  id INT NOT NULL PRIMARY KEY,
--  nr_matricol VARCHAR2(6) NOT NULL,
--  nume VARCHAR2(15) NOT NULL,
--  prenume VARCHAR2(30) NOT NULL,
--  an NUMBER(1),
--  grupa CHAR(2),
--  bursa NUMBER(6,2),
--  data_nastere DATE,
--  email VARCHAR2(40),
--  created_at DATE,
--  updated_at DATE
--)
--
--CREATE TABLE prieteni (
--  id INT PRIMARY KEY,
--  id_student1 INT NOT NULL, 
--  id_student2 INT NOT NULL, 
--  created_at DATE,
--  updated_at DATE,  
--  CONSTRAINT fk_prieteni_id_student1 FOREIGN KEY (id_student1) REFERENCES studenti(id),
--  CONSTRAINT fk_prieteni_id_student2 FOREIGN KEY (id_student2) REFERENCES studenti(id),
--  CONSTRAINT no_duplicates UNIQUE (id_student1, id_student2)
--)

CREATE OR REPLACE PROCEDURE populateTable(tableName VARCHAR2) IS 

    v_importFileName UTL_FILE.FILE_TYPE;
    v_buffer VARCHAR2(1000);
    
    v_ID NUMBER;
    v_NR_MATRICOL VARCHAR2(6);
    v_nume VARCHAR2(30);
    v_prenume VARCHAR2(30);
    v_an NUMBER(1);
    v_grupa CHAR(2);
    v_bursa NUMBER(6,2);
    v_data_nastere DATE;
    v_email VARCHAR2(40);
    v_created_at DATE;
    v_updated_at DATE;

BEGIN
    
    IF(tableName='STUDENTI')THEN
         v_importFileName:=UTL_FILE.FOPEN('MYDIR','STUDENTI1','R');
    ELSIF(tableName='PRIETENI')THEN
        v_importFileName:=UTL_FILE.FOPEN('MYDIR','PRIETENI','R');
    END IF;
    
    LOOP
        
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_ID:=TO_NUMBER(v_buffer);
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_NR_MATRICOL:=v_buffer;
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_nume:=v_buffer;
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_prenume:=v_buffer;
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_an:=TO_NUMBER(v_buffer);
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_grupa:=v_buffer;
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_bursa:=TO_NUMBER(v_buffer);
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_data_nastere:=TO_DATE(v_buffer,'dd-mm-yyyy');
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_email:=v_buffer;
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_created_at:=TO_DATE(v_buffer,'dd-mm-yyyy');
        UTL_FILE.GET_LINE(v_importFileName,v_buffer); 
        v_updated_at:=TO_DATE(v_buffer,'dd-mm-yyyy');
        
        INSERT INTO STUDENTI (id,nr_matricol,nume,prenume,an,grupa,bursa,data_nastere,email,created_at,updated_at) VALUES (v_ID,v_NR_MATRICOL,v_nume,v_prenume,v_an,v_grupa,v_bursa,v_data_nastere,v_email,v_created_at,v_updated_at);

    END LOOP;
    
    EXCEPTION WHEN NO_DATA_FOUND THEN 
            UTL_FILE.FCLOSE(v_importFileName);
            DBMS_OUTPUT.PUT_LINE('Input file has been read entirely.');

    IF(tableName='STUDENTI')THEN
         v_importFileName:=UTL_FILE.FOPEN('MYDIR','STUDENTI1','R');
    ELSIF(tableName='PRIETENI')THEN
        v_importFileName:=UTL_FILE.FOPEN('MYDIR','PRIETENI','R');
    END IF;

END;

SET SERVEROUTPUT ON;

BEGIN

    populateTable('STUDENTI');

END;