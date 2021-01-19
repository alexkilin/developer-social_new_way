begin;

-- alter table single_chat_messages
--     drop constraint UK_bluj7qqrm26dvmp2col268j29;

alter table single_chat_messages
    drop constraint FK95wmxcm6neq6tudf9rapnc6m8;

alter table single_chat_messages
    drop constraint FKnwmy2hk0iobmwapmaikccu15i;

-- alter table single_chat_messages
--    add constraint UK_bluj7qqrm26dvmp2col268j29 unique (message_id)
--    on update cascade;

alter table single_chat_messages
   add constraint FK95wmxcm6neq6tudf9rapnc6m8
   foreign key (message_id)
   references messages
      on update cascade;


alter table single_chat_messages
   add constraint FKnwmy2hk0iobmwapmaikccu15i
   foreign key (chat_id)
   references single_chat
   on update cascade;
commit;