begin;

alter table single_chat
    drop constraint FK38uct6yu5d7ny6uxeuk3afmte;

alter table single_chat
    drop constraint FKlixke8t3kyjbirn6xxfs58s35;

alter table single_chat
drop constraint FKn8fkl5522npp79gun310v08gc;

alter table single_chat
   add constraint FK38uct6yu5d7ny6uxeuk3afmte
   foreign key (user_one_id)
   references users
   on update cascade;

alter table single_chat
   add constraint FKlixke8t3kyjbirn6xxfs58s35
   foreign key (user_two_id)
   references users
      on update cascade;

alter table single_chat
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (chat_id)
   references chats
   on update cascade;
commit;