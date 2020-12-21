create table media_comment (
   comment_id int8 not null,
    media_id int8 not null,
    primary key (comment_id)
)

next

alter table media_comment
   add constraint FKqddyww0crb9h5943ecanke9p2
   foreign key (comment_id)
   references comments

next

alter table media_comment
   add constraint FKc4g13ic2kajrl02anuikf5bao
   foreign key (media_id)
   references media

next