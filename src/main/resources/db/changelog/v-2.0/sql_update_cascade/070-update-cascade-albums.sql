begin;

alter table albums
drop constraint FKnbt8mlh802xtbdf5oue3gs1oj;

alter table albums
   add constraint FKnbt8mlh802xtbdf5oue3gs1oj
   foreign key (user_owner_id)
   references users
   on update cascade;
   commit;