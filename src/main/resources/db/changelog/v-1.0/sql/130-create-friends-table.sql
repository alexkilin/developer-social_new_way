create table friends (
    id int8 not null,
    friend_user_id int8 not null,
    user_user_id int8 not null,
    primary key (id)
)

next

alter table friends
   add constraint FKglgwqjy5yrqnkoupjt738o12c
   foreign key (friend_user_id)
   references users

next

alter table friends
   add constraint FKnnacc56g8l3e7hk26qse3l1g7
   foreign key (user_user_id)
   references users

next