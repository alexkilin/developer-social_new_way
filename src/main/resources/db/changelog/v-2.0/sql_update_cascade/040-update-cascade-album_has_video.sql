begin;

-- alter table album_has_video
-- drop constraint UK_a5rhnqpxtg6ghuopt3857jsa1;

alter table album_has_video
drop constraint FKh3snslp51d756ghktmpxn7vo4;

alter table album_has_video
drop constraint FKk4bl9yx13be7tq4r8d069m6hu;

-- alter table album_has_video
--    add constraint UK_a5rhnqpxtg6ghuopt3857jsa1 unique (videos_id)
--    on update cascade;

alter table album_has_video
   add constraint FKh3snslp51d756ghktmpxn7vo4
   foreign key (videos_id)
   references videos
    on update cascade;

alter table album_has_video
   add constraint FKk4bl9yx13be7tq4r8d069m6hu
   foreign key (album_id)
   references album_video
   on update cascade;
commit;