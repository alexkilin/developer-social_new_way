begin;

alter table post_like
    drop constraint FK41sp7f5q4lce3olirjrdwkn8g;

alter table post_like
    drop constraint FKcf8kqsucxsmplv3xw9gubrql0;

alter table post_like
   add constraint FK41sp7f5q4lce3olirjrdwkn8g
   foreign key ("like_id")
   references "like"
    on update cascade;

alter table post_like
   add constraint FKcf8kqsucxsmplv3xw9gubrql0
   foreign key (post_id)
   references posts
   on update cascade;
commit;