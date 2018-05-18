set serveroutput on;
drop table lab7ex2;
create table lab7ex2(
  valoare int 
);
/
CREATE OR REPLACE TRIGGER update_note 
FOR UPDATE ON note
COMPOUND TRIGGER
  v_note INT:=0;
  BEFORE EACH ROW IS 
  BEGIN
    v_note:=v_note+1;
    IF (:OLD.valoare>:NEW.valoare) THEN
      BEGIN
      :NEW.valoare := :OLD.valoare;
      v_note:=v_note-1;
      END;
    end if;  
    dbms_output.put_line('Update nota cu ID: '|| :OLD.id);
  END BEFORE EACH ROW;
  AFTER STATEMENT IS BEGIN
     insert into lab7ex2 values(v_note);
  END AFTER STATEMENT ;
END update_note;
/
update note set valoare=10 where id in(373,378,390);