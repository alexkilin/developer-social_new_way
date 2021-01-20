begin;

-- alter table media_messages
--     drop constraint UK_ol390bknn4lfrscx6hs5rxgxh;

alter table media_messages
    drop constraint FK9711cqt4f4c9r8myvpxxaxdj5;

alter table media_messages
    drop constraint FK638dd51v9jqsoqk64yc0bvyts;

-- alter table media_messages
--    add constraint UK_ol390bknn4lfrscx6hs5rxgxh unique (media_id)
--    on update cascade;

alter table media_messages
   add constraint FK9711cqt4f4c9r8myvpxxaxdj5
   foreign key (media_id)
   references media
   on update cascade;

alter table media_messages
   add constraint FK638dd51v9jqsoqk64yc0bvyts
   foreign key (message_id)
   references messages
   on update cascade;
commit;