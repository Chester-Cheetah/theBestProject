USE alien_star_system_users;
create table users
(
    id int,
    username VARCHAR(50) not null,
    password VARCHAR(50) not null,
    email VARCHAR(50) not null,
    country VARCHAR(50) not null,
    city VARCHAR(50) not null
);

create unique index users_email_uindex
    on users (email);

create unique index users_id_uindex
    on users (id);

create unique index users_username_uindex
    on users (username);

alter table users
    add constraint users_pk
        primary key (id);

alter table users modify id int auto_increment;

create table roles
(
    roleName VARCHAR(50) not null,
    roleNameRUS VARCHAR(50) not null
);

create unique index roles_roleName_uindex
    on roles (roleName);

create unique index roles_roleNameRUS_uindex
    on roles (roleNameRUS);

alter table roles
    add constraint roles_pk
        primary key (roleName);

create table user_role
(
    user_id int not null,
    role_id VARCHAR(50) not null
);

alter table user_role
    add constraint role_id_fk
        foreign key (role_id) references roles (roleName);

alter table user_role
    add constraint user_id_fk
        foreign key (user_id) references users (id);

insert into users (id, username, password, email, country, city)
VALUES (1, 'ADMIN', 'admin', 'admin@admin.com', 'Россия', 'Москва');

insert into roles (roleName, roleNameRUS)
VALUES ('ROLE_ADMIN', 'Админ');

insert into roles (roleName, roleNameRUS)
VALUES ('ROLE_USER', 'Юзер');

insert into user_role (user_id, role_id)
VALUES (1, 'ROLE_ADMIN');





