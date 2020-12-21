create table media_messages (
   message_id int8 not null,
    media_id int8 not null,
    primary key (message_id, media_id)
)

next

alter table media_messages
   add constraint UK_ol390bknn4lfrscx6hs5rxgxh unique (media_id)

next

alter table media_messages
   add constraint FK9711cqt4f4c9r8myvpxxaxdj5
   foreign key (media_id)
   references media

next

alter table media_messages
   add constraint FK638dd51v9jqsoqk64yc0bvyts
   foreign key (message_id)
   references messages

next

