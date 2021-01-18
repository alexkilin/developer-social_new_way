begin;

alter table "like"
    drop constraint FKn6yllf2sb7ttvbrwya4lxkx60;

alter table "like"
   add constraint FKn6yllf2sb7ttvbrwya4lxkx60
   foreign key (user_id)
   references users
   on update cascade;
commit;