create table audios (
   album varchar(255),
    author varchar(255),
    icon varchar(255),
    length int4,
    name varchar(255),
    media_id int8 not null,
    primary key (media_id)
)

next

alter table audios
   add constraint FKmbwry03s0jccvgqefrlv3ln1j
   foreign key (media_id)
   references media

next