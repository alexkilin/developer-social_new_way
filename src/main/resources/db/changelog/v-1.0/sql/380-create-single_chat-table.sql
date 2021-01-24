create table single_chat (
    chat_id int8 not null,
    user_one_id int8,
    user_two_id int8,
    primary key (chat_id)
)

next

alter table single_chat
   add constraint FK38uct6yu5d7ny6uxeuk3afmte
   foreign key (user_one_id)
   references users

next

alter table single_chat
   add constraint FKlixke8t3kyjbirn6xxfs58s35
   foreign key (user_two_id)
   references users

next

alter table single_chat
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (chat_id)
   references chats

next