CREATE USER MG IDENTIFIED BY mg DEFAULT TABLESPACE USERS;
GRANT CONNECT, RESOURCE, DBA TO MG;
GRANT UNLIMITED TABLESPACE TO MG;
GRANT All PRIVILEGE TO MG;