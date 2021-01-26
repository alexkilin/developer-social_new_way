begin;

alter table group_chats
drop constraint FKn8fkl5522npp79gun310v08gc;

alter table group_chats
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (chat_id)
   references chats
   on update cascade;
commit;