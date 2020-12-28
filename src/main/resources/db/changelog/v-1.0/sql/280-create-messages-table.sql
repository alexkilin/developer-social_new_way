create table messages (
   id int8 not null,
    is_unread int4 not null,
    persist_date timestamp,
    message varchar(255),
    last_redaction_date timestamp not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table messages
   add constraint FKpsmh6clh3csorw43eaodlqvkn
   foreign key (user_id)
   references users

next