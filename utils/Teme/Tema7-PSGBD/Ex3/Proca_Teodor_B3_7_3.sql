set serveroutput on;
--drop table tabel_useri;
create table tabel_useri(nume varchar2(30), ora timestamp);
--drop trigger trigg;
CREATE OR REPLACE TRIGGER trigg
   BEFORE ALTER OR TRUNCATE OR DROP ON DATABASE
DECLARE
v_nume VARCHAR2(30);
BEGIN
  v_nume := ora_login_user;
  INSERT INTO tabel_useri VALUES(v_nume, CURRENT_TIMESTAMP);
  RAISE_APPLICATION_ERROR (
      num => -20000,
      msg => 'can''t touch this');
END;

--drop table note;
--select * from tabel_useri;
--select * from note;
