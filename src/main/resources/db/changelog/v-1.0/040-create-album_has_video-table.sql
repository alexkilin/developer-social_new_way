create table album_has_video (
   album_id int8 not null,
    videos_id int8 not null,
    primary key (album_id, videos_id)
)

next

alter table album_has_video
   add constraint UK_a5rhnqpxtg6ghuopt3857jsa1 unique (videos_id)

next

alter table album_has_video
   add constraint FKh3snslp51d756ghktmpxn7vo4
   foreign key (videos_id)
   references videos

next

alter table album_has_video
   add constraint FKk4bl9yx13be7tq4r8d069m6hu
   foreign key (album_id)
   references album_video

next