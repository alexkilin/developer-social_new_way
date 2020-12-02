create table users_videos_collections (
   user_id int8 not null,
    video_id int8 not null,
    primary key (user_id, video_id)
)

next

alter table users_videos_collections
   add constraint FKjic2imsrvl3jumk8fcbcafydo
   foreign key (video_id)
   references videos

next

alter table users_videos_collections
   add constraint FK1ijcokiwo0s5wsl2jidbv071v
   foreign key (user_id)
   references users

next