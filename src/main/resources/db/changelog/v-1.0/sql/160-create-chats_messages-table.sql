create table chats_messages (
   chat_id int8 not null,
    message_id int8 not null,
    primary key (chat_id, message_id)
)

next

alter table chats_messages
   add constraint UK_qov15g85hkl2n52yptlivf7c8 unique (message_id)

next

alter table chats_messages
   add constraint FK824s2valk1yftney5dl4kufm2
   foreign key (message_id)
   references messages

next

alter table chats_messages
   add constraint FKnwmy2hk0iobmwapmaikccu15i
   foreign key (chat_id)
   references chats

next