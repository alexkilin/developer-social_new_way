create table post_like (
   "like_id" int8 not null,
    post_id int8,
    primary key ("like_id")
)

next

alter table post_like
   add constraint FK41sp7f5q4lce3olirjrdwkn8g
   foreign key ("like_id")
   references "like"

next

alter table post_like
   add constraint FKcf8kqsucxsmplv3xw9gubrql0
   foreign key (post_id)
   references posts

next