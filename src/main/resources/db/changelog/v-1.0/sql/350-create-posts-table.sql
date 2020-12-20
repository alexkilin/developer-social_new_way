create table posts (
   id int8 not null,
    last_redaction_date timestamp not null,
    persist_date timestamp not null,
    text varchar(1000) not null,
    title varchar(50) not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table posts
   add constraint FK5lidm6cqbc7u4xhqpxm898qme
   foreign key (user_id)
   references users

next

alter table posts
    add constraint FKrfchr8dax0kfngvvkbteh5n7h
    foreign key (topic_id)
    references topics

next