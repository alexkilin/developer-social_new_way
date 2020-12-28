create table single_chat_messages (
   chat_id int8 not null,
    message_id int8 not null,
    primary key (chat_id, message_id)
)

next

alter table single_chat_messages
   add constraint UK_bluj7qqrm26dvmp2col268j29 unique (message_id)

next

alter table single_chat_messages
   add constraint FK95wmxcm6neq6tudf9rapnc6m8
   foreign key (message_id)
   references messages

next

alter table single_chat_messages
   add constraint FKnwmy2hk0iobmwapmaikccu15i
   foreign key (chat_id)
   references single_chat

next