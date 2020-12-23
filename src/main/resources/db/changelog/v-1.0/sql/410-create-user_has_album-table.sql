create table user_has_album (
   id int8 not null,
    persist_date timestamp not null,
    album_id int8 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table user_has_album
   add constraint FKr45vx07d7vjayb6xr2x4ekirf
   foreign key (album_id)
   references albums

next

alter table user_has_album
   add constraint FK1biix5wmnyeps5p7ddvad6rwc
   foreign key (user_id)
   references users

next