create table media_like (
   "like_id" int8 not null,
    media_id int8 not null,
    primary key ("like_id")
)

next

alter table media_like
   add constraint FK2p0b7gpbngslllwcnlovjrhwu
   foreign key ("like_id")
   references "like"

next

alter table media_like
   add constraint FKdq31g0obh9yexo3jy1lx4jvs6
   foreign key (media_id)
   references media

next

alter table media_like
   drop constraint FK2p0b7gpbngslllwcnlovjrhwu

next

alter table media_like
   drop constraint FKdq31g0obh9yexo3jy1lx4jvs6

next