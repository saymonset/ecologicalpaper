SPOOL C:\orabd10g\data\ECOLOGICAL\LOGS\CREAR10G_USUARIO.LOG


PROMPT BORRANDO ROL_JR2K
DROP ROLE ROL_JR2K;

PROMPT CREANDO ROL_JR2K
CREATE ROLE ROL_JR2K;

GRANT CREATE PROCEDURE      TO ROL_JR2K;
GRANT CREATE SEQUENCE 	    TO ROL_JR2K;
GRANT CREATE SYNONYM        TO ROL_JR2K;
GRANT CREATE TABLE          TO ROL_JR2K;
GRANT CREATE TRIGGER        TO ROL_JR2K;
GRANT CREATE VIEW           TO ROL_JR2K;
GRANT EXECUTE ANY PROCEDURE TO ROL_JR2K;
GRANT ANALYZE ANY           TO ROL_JR2K;

PROMPT BORRANDO ECOLOGICAL
DROP USER ECOLOGICAL CASCADE;

PROMPT CREANDO ECOLOGICAL
CREATE USER ECOLOGICAL IDENTIFIED BY "ECOLOGICAL"
    DEFAULT TABLESPACE USERS 
    TEMPORARY TABLESPACE TEMP;

GRANT CONNECT  TO ECOLOGICAL;
GRANT RESOURCE TO ECOLOGICAL;
GRANT ROL_JR2K TO ECOLOGICAL;
GRANT UNLIMITED TABLESPACE TO ECOLOGICAL;

PROMPT BORRANDO EXPJURIS
DROP USER EXPJURIS CASCADE;

PROMPT CREANDO EXPJURIS
CREATE USER EXPJURIS IDENTIFIED BY "EXPJURIS" 
    DEFAULT TABLESPACE TBS_USERS
    TEMPORARY TABLESPACE TEMP ;

GRANT CONNECT           TO EXPJURIS;
GRANT EXP_FULL_DATABASE TO EXPJURIS;
GRANT IMP_FULL_DATABASE TO EXPJURIS;
GRANT ANALYZE ANY       TO EXPJURIS;

PROMPT CREANDO PROC_JR2K
DROP USER PROC_JR2K CASCADE;

PROMPT CREANDO PROC_JR2K
CREATE USER PROC_JR2K IDENTIFIED BY "PROC_JR2K" 
    DEFAULT TABLESPACE TBS_USERS
    TEMPORARY TABLESPACE TEMP;

GRANT CONNECT TO PROC_JR2K;

ALTER USER CTXSYS IDENTIFIED BY "CTXSYS"
   ACCOUNT UNLOCK;

SPOOL OFF