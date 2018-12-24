BEGIN 
  dbms_scheduler.create_job ( 
   job_name => 'wait_time_job', 
   job_type => 'PLSQL_BLOCK',
   job_action => 'wait_time;', 
   start_date => SYSTIMESTAMP, 
   enabled => true, 
   repeat_interval => 'FREQ=MINUTELY;INTERVAL=1'
  ); 
END;

create or replace PROCEDURE wait_time                                              
IS 
v_time int;
begin
select cast(to_char(LOCALTIMESTAMP, 'hh24') as int) into v_time from dual;
if v_time <= 18 and v_time > 8 then
update DOCTOR_QUEUE_USERS_WAIT_TIMES set WAIT_TIME = WAIT_TIME - 1 WHERE WAIT_TIME > 0;
end if;
end;