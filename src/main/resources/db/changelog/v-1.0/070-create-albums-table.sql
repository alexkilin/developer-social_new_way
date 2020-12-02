create table albums (
    id int8 not null,
    icon varchar(255),
    update_date timestamp,
    media_type int4 not null,
    name varchar(255),
    persist_date timestamp not null,
    user_owner_id_user_id int8,
    primary key (id)
)

next

alter table albums
   add constraint FKnbt8mlh802xtbdf5oue3gs1oj
   foreign key (user_owner_id_user_id)
   references users

next