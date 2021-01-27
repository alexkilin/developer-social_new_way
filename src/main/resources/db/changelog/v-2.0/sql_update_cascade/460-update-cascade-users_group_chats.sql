begin;

alter table users_group_chats
    drop constraint FK6q5yt8kersl8ejr63nfi53314;

alter table users_group_chats
    drop constraint FKnkf7ei1oneo72n53x8rw6ht23;

alter table users_group_chats
   add constraint FK6q5yt8kersl8ejr63nfi53314
   foreign key (chat_id)
   references group_chats
    on update cascade;

alter table users_group_chats
   add constraint FKnkf7ei1oneo72n53x8rw6ht23
   foreign key (user_id)
   references users
    on update cascade;

commit;