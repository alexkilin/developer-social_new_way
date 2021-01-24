create table users_group_chats (
   user_id int8 not null,
    chat_id int8 not null,
    primary key (user_id, chat_id)
)

next

alter table users_group_chats
   add constraint FK6q5yt8kersl8ejr63nfi53314
   foreign key (chat_id)
   references group_chats

next

alter table users_group_chats
   add constraint FKnkf7ei1oneo72n53x8rw6ht23
   foreign key (user_id)
   references users

next