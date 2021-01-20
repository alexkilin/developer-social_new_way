begin;

alter table album_video
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (album_id)
   references albums
on update cascade;

commit;