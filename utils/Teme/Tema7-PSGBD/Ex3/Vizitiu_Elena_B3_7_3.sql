/*drop table elevi;
create table elevi(id int not null primary key, nume varchar2(10), prenume varchar2(10));
insert into elevi values(12,'Amariei','Oana');*/

drop table ex3;
create table ex3(nume varchar2(30), ora timestamp);
/
  CREATE OR REPLACE TRIGGER lab7ex3
  BEFORE DROP OR TRUNCATE OR ALTER ON database
  DECLARE
  v_stud VARCHAR2(30);
  pragma autonomous_transaction;
  BEGIN
    v_stud := ora_login_user;
  INSERT INTO ex3 VALUES(v_stud, CURRENT_TIMESTAMP);
    commit;
    RAISE_APPLICATION_ERROR (
      num => -20000,
      msg => 'nepermis');
  END;
/
--drop table elevi;
--drop table studenti;
--alter table studenti drop column nume;
--truncate table elevi;
--select *from elevi;
