begin;

alter table album_image
drop constraint FKktle04uj7x3exwin0gfq0xdtd;

alter table album_image
   add constraint FKktle04uj7x3exwin0gfq0xdtd
   foreign key (album_id)
   references albums
   on update cascade;
commit;