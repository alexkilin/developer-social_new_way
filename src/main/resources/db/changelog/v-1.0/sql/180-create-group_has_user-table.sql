create table group_has_user (
   id int8 not null,
    persist_date timestamp not null,
    group_id int8 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table group_has_user
   add constraint FKhcr1kxm4l7myig424250g8i63
   foreign key (group_id)
   references groups

next

alter table group_has_user
   add constraint FKo5foqw5fct5ji0601eeg3tm3q
   foreign key (user_id)
   references users

next