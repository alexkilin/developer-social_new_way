create sequence hotfix_seq as bigint start 98 increment by 1;
update album_video set album_id = nextval('hotfix_seq');
drop sequence if exists hotfix_seq;

begin;

alter table album_video
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (album_id)
   references albums
on update cascade;

commit;

