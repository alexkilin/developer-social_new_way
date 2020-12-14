create table reposts (
   id  bigserial not null,
    post_id int8 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table reposts
   add constraint FKersix1dfhot0h5xp4umnh2mgr
   foreign key (post_id)
   references posts

next

alter table reposts
   add constraint FKdtkd7swbi33b8w536k29yjbom
   foreign key (user_id)
   references users

next