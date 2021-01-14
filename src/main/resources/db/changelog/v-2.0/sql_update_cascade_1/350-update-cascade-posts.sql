begin;

alter table posts
    drop constraint FK5lidm6cqbc7u4xhqpxm898qme;

alter table posts
    drop constraint FKrfchr8dax0kfngvvkbteh5n7h;

alter table posts
   add constraint FK5lidm6cqbc7u4xhqpxm898qme
   foreign key (user_id)
   references users
   on update cascade;

alter table posts
    add constraint FKrfchr8dax0kfngvvkbteh5n7h
    foreign key (topic_id)
    references topics
    on update cascade;
commit;