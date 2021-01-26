 begin;

-- alter table group_chats_messages
--     drop constraint UK_qov15g85hkl2n52yptlivf7c8;

alter table chats_messages
    drop constraint FK824s2valk1yftney5dl4kufm2;
alter table chats_messages
    drop constraint FKnwmy2hk0iobmwapmaikccu15i;

-- alter table group_chats_messages
--    add constraint UK_qov15g85hkl2n52yptlivf7c8 unique (message_id)
-- on update cascade;

alter table chats_messages
   add constraint FK824s2valk1yftney5dl4kufm2
   foreign key (message_id)
   references messages
    on update cascade;

alter table chats_messages
   add constraint FKnwmy2hk0iobmwapmaikccu15i
   foreign key (chat_id)
   references chats
   on update cascade;

commit;

