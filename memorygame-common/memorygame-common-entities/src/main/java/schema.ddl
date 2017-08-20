
    create table HR.RESULT (
        ID number(19,0) not null,
        RESULT_DATE date not null,
        SECONDS number(22,0) not null,
        USER_ID number(19,0) not null,
        primary key (ID)
    );

    create table HR.USERS (
        ID number(19,0) not null,
        EMAIL varchar2(50 char) not null,
        PASSWORD varchar2(64 char) not null,
        USERNAME varchar2(50 char) not null,
        primary key (ID)
    );

    alter table HR.USERS 
        add constraint UK_81nqioeq3njjrwqaltk2mcobj unique (EMAIL);

    alter table HR.USERS 
        add constraint UK_h6k33r31i2nvrri9lok4r163j unique (USERNAME);

    alter table HR.RESULT 
        add constraint FK_7kpy40xqtkygmrh55hjct9y9h 
        foreign key (USER_ID) 
        references HR.USERS;

    create sequence HR.RESULT_SEQ;

    create sequence HR.USERS_SEQ;
