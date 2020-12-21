create table media (
   id int8 not null,
    media_type int4 not null,
    persist_date timestamp not null,
    url varchar(255) not null,
    album_id int8,
    user_id int8 not null,
    primary key (id)
)

next

alter table media
   add constraint FKkqrdr00pw2v5k3q4tqv76cy6i
   foreign key (album_id)
   references albums

next

alter table media
   add constraint FKnd8hh0yn7qvv4pqyk8mg7l1ox
   foreign key (user_id)
   references users

next