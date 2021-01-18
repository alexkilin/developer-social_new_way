begin;

alter table users_videos_collections
    drop constraint FKjic2imsrvl3jumk8fcbcafydo;

alter table users_videos_collections
    drop constraint FK1ijcokiwo0s5wsl2jidbv071v;

alter table users_videos_collections
   add constraint FKjic2imsrvl3jumk8fcbcafydo
   foreign key (video_id)
   references videos
   on update cascade;

alter table users_videos_collections
   add constraint FK1ijcokiwo0s5wsl2jidbv071v
   foreign key (user_id)
   references users
    on update cascade;
commit;