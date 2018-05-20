set serveroutput on;
CREATE OR REPLACE TRIGGER conect
  INSTEAD OF CREATE ON SCHEMA
  BEGIN
    EXECUTE IMMEDIATE 'CREATE TABLE conectat (conectat VARCHAR2(50), timp TIMESTAMP)';
  END;
/
create table z(v varchar2(50),w TIMESTAMP);
/
CREATE OR REPLACE TRIGGER steregere_tabele
   BEFORE DROP ON DATABASE
DECLARE
v_conectat1 VARCHAR2(50);
BEGIN
  v_conectat1 := ora_login_user;
  INSERT INTO conectat VALUES(v_conectat1, CURRENT_TIMESTAMP);
  DBMS_OUTPUT.PUT('S-a conectat persoana cu numele '||v_conectat1||', la ora '||CURRENT_TIMESTAMP);
  RAISE_APPLICATION_ERROR (
      num => -20000,
      msg => 'can`t touch this');
END;

drop table note;
/

CREATE OR REPLACE TRIGGER alterare_tabele
   BEFORE ALTER ON DATABASE
DECLARE
v_conectat2 VARCHAR2(50);
BEGIN
  v_conectat2 := ora_login_user;
  INSERT INTO conectat VALUES(v_conectat2, CURRENT_TIMESTAMP);
  DBMS_OUTPUT.PUT('S-a conectat persoana cu numele '||v_conectat2||', la ora '||CURRENT_TIMESTAMP);
  RAISE_APPLICATION_ERROR (
      num => -20000,
      msg => 'can`t touch this');
END;

alter table studenti drop nume varchar2(15 byte);
alter table studenti add burse_studenti number;
/
CREATE OR REPLACE TRIGGER trunchiere_tabele
   BEFORE TRUNCATE ON DATABASE
DECLARE
v_conectat3 VARCHAR2(50);
BEGIN
  v_conectat3 := ora_login_user;
  INSERT INTO conectat VALUES(v_conectat3, CURRENT_TIMESTAMP);
  DBMS_OUTPUT.PUT('S-a conectat persoana cu numele '||v_conectat3||', la ora '||CURRENT_TIMESTAMP);
  RAISE_APPLICATION_ERROR (
      num => -20000,
      msg => 'can`t touch this');
END;

truncate table cursuri;
