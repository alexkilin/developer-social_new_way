create table comment_like (
   "like_id" int8 not null,
    comment_id int8 not null,
    primary key ("like_id")
)

next

alter table comment_like
   add constraint FKl1mu2ryf4k4ps93b5b52q2m8i
   foreign key (comment_id)
   references comments

next

alter table comment_like
   add constraint FKkldgc9u7sbqv0djmeeusvisue
   foreign key ("like_id")
   references "like"

next