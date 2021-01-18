begin;

alter table group_has_user
    drop constraint FKhcr1kxm4l7myig424250g8i63;

alter table group_has_user
    drop constraint FKo5foqw5fct5ji0601eeg3tm3q;

alter table group_has_user
   add constraint FKhcr1kxm4l7myig424250g8i63
   foreign key (group_id)
   references groups
   on update cascade;

alter table group_has_user
   add constraint FKo5foqw5fct5ji0601eeg3tm3q
   foreign key (user_id)
   references users
   on update cascade;
   commit;