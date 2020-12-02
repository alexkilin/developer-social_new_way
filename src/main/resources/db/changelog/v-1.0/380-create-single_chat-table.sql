create table single_chat (
   id int8 not null,
    image varchar(255),
    persist_date timestamp not null,
    title varchar(255),
    user_one_id int8,
    user_two_id int8,
    primary key (id)
)

next

alter table single_chat
   add constraint FK38uct6yu5d7ny6uxeuk3afmte
   foreign key (user_one_id)
   references users

next

alter table single_chat
   add constraint FKlixke8t3kyjbirn6xxfs58s35
   foreign key (user_two_id)
   references users

next
