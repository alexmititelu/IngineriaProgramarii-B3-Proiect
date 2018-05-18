set serveroutput on;
CREATE OR REPLACE TRIGGER note_modificate
  INSTEAD OF CREATE ON SCHEMA
  BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE update_c (number_update NUMBER)';
  END;
/
create table q(b number); 
/
CREATE OR REPLACE TRIGGER logare
FOR UPDATE ON NOTE
COMPOUND TRIGGER
  v_contor number:=0;
  v_index int :=0;
  BEFORE EACH ROW IS 
  BEGIN
    FOR v_index in 1..16200 LOOP
    IF (:NEW.valoare<:OLD.valoare) THEN 
     :NEW.valoare := :OLD.valoare;
     dbms_output.put_line('Actulizam nota cu ID: '|| :OLD.id);
     ELSE 
     v_contor:=v_contor+1;
     end if;
  END LOOP;
  END BEFORE EACH ROW;
  
  AFTER STATEMENT IS BEGIN
     INSERT INTO update_c VALUES(v_contor/16200);
     dbms_output.put_line('Am actualizat: '||v_contor/16200||' note.');
  END AFTER STATEMENT ;
END logare;
/
UPDATE note SET valoare=10 WHERE id BETWEEN 16 AND 18;