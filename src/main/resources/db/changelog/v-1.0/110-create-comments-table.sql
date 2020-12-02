create table comments (
   id int8 not null,
    comment varchar(255),
    comment_type int4 not null,
    persist_date timestamp,
    last_redaction_date timestamp not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table comments
   add constraint FK8omq0tc18jd43bu5tjh6jvraq
   foreign key (user_id)
   references users

next