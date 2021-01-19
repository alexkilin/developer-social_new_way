begin;

alter table user_has_album
    drop constraint FKr45vx07d7vjayb6xr2x4ekirf;

alter table user_has_album
    drop constraint FK1biix5wmnyeps5p7ddvad6rwc;

alter table user_has_album
   add constraint FKr45vx07d7vjayb6xr2x4ekirf
   foreign key (album_id)
   references albums
    on update cascade;

alter table user_has_album
   add constraint FK1biix5wmnyeps5p7ddvad6rwc
   foreign key (user_id)
   references users
   on update cascade;
commit;