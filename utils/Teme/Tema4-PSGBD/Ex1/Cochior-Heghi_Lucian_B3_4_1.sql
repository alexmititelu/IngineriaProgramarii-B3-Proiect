create or replace package pkg_tema4 IS
    type bursa_marire is record (id studenti.id%type, procentaj int);
    type list_marire is varray(20) of bursa_marire;
    procedure marireBursa (p_lista_marire IN list_marire );
    procedure afisare(p in int);
end pkg_tema4;


create or replace package body pkg_tema4 is

 procedure marireBursa (p_lista_marire IN list_marire )AS
    v_bursa number;
    v_bursa_history studenti.istorie_burse%type;
    v_aux studenti.istorie_burse%type;

    begin
     for i in p_lista_marire.first..p_lista_marire.last loop
        
        if p_lista_marire.exists(i) then
        
          select bursa into v_bursa
               from studenti
                where id = p_lista_marire(i).id;
            
          if (v_bursa is null) then
             v_bursa := 100;
          end if;
          
           DBMS_OUTPUT.put_line('Bursa: '|| v_bursa);
           v_bursa := trunc(v_bursa + (p_lista_marire(i).procentaj*v_bursa)/100);
          
           DBMS_OUTPUT.put_line('Bursa marita: '||  v_bursa);
           
        update studenti
        set bursa = v_bursa
        where id = p_lista_marire(i).id;
           
        select istorie_burse into v_bursa_history
         from studenti
            where id = p_lista_marire(i).id;
            
        if(v_bursa_history is null)then
            
            v_bursa_history := burse(v_bursa);
            
        else
            v_bursa_history.extend;
            v_bursa_history(v_bursa_history.last) := v_bursa;
            
        end if;
        
       
        update studenti
        set istorie_burse = v_bursa_history
        where id = p_lista_marire(i).id;
               
            
        end if;

     end loop;
     
     
    end marireBursa;
    
    ---------------------------------------------------------------------
    
    procedure afisare (p in int) as
    cursor linii_student is select * from studenti;
    begin
        for linie_student in linii_student loop
            if(linie_student.istorie_burse is not null)then
                DBMS_OUTPUT.PUT_LINE('Nume-prenume: '||linie_student.nume||' '||linie_student.prenume);
                for i in linie_student.istorie_burse.first..linie_student.istorie_burse.last loop
                     DBMS_OUTPUT.PUT_LINE(linie_student.istorie_burse(i));
                end loop;
            end if;
        end loop;
    end afisare;

    
end pkg_tema4;


create or replace type burse as varray(50) of number;
/
alter table studenti
add istorie_burse burse;


drop package pkg_tema4;


set serveroutput on;

declare

 v_marire_burse pkg_tema4.list_marire := pkg_tema4.list_marire();

begin
 
        v_marire_burse.extend;
        v_marire_burse(1).id:=2;
        v_marire_burse(1).procentaj:=50;
        
        v_marire_burse.extend;
        v_marire_burse(2).id:=3;
        v_marire_burse(2).procentaj:=10;
        
        v_marire_burse.extend;
        v_marire_burse(3).id:=4;
        v_marire_burse(3).procentaj:=20;
        
        v_marire_burse.extend;
        v_marire_burse(4).id:=5;
        v_marire_burse(4).procentaj:=30;
        
        v_marire_burse.extend;
        v_marire_burse(5).id:=6;
        v_marire_burse(5).procentaj:=40;
        
 
     pkg_tema4.marireBursa(v_marire_burse);
     pkg_tema4.afisare(100);

end;