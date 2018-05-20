--CREATE OR REPLACE DIRECTORY MYDIR as '/home/ciprian/MYTHINGS/Facultate/ANUL II/SEM II/PSGBD';

CREATE OR REPLACE PROCEDURE exportTable(tableName VARCHAR2) IS 

    CURSOR readStudenti IS SELECT * FROM STUDENTI;
    CURSOR readPrieteni IS SELECT * FROM PRIETENI;

    v_exportFileName UTL_FILE.FILE_TYPE;
    v_buffer VARCHAR2(1000);

BEGIN

    IF(tableName='STUDENTI')THEN
        v_exportFileName:=UTL_FILE.FOPEN('MYDIR','STUDENTI','w',1000);
    ELSIF(tableName='PRIETENI')THEN
        v_exportFileName:=UTL_FILE.FOPEN('MYDIR','PRIETENI','w',1000);
    END IF;
    
    IF(tableName='STUDENTI')THEN
        FOR i IN readStudenti
            LOOP
                v_buffer:=i.ID;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.NR_MATRICOL;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.NUME;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.PRENUME;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.AN;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.GRUPA;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.BURSA;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.DATA_NASTERE;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.EMAIL;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.CREATED_AT;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.UPDATED_AT;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:='=======================';
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
            END LOOP;
    
    ELSIF(tableName='PRIETENI')THEN
        FOR i IN readPrieteni
            LOOP
                v_buffer:=i.ID;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.ID_STUDENT1;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.ID_STUDENT2;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.CREATED_AT;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:=i.UPDATED_AT;
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
                v_buffer:='==========';
                UTL_FILE.PUT_LINE(v_exportFileName,v_buffer);
            END LOOP;
    END IF;
    
    UTL_FILE.FCLOSE(v_exportFileName);

END;
/

SET SERVEROUTPUT ON;

BEGIN
    
    exportTable('STUDENTI');
    exportTable('PRIETENI');
    
END;