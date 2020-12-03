create table group_chats_users (
   group_chat_id int8 not null,
    users_user_id int8 not null,
    primary key (group_chat_id, users_user_id)
)

next

alter table group_chats_users
   add constraint FKqqkoekcp5r89cy7yvn1m6bhme
   foreign key (users_user_id)
   references users

next

alter table group_chats_users
   add constraint FKmwcc7ecifc38biduqtg4relam
   foreign key (group_chat_id)
   references group_chats

next

