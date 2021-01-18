begin;

alter table user_tabs
    drop constraint FKmaajj3ua3djfl3tj52db22ypm;

alter table user_tabs
    drop constraint FKgo6h5c1wskrntjm2rkcrgmvih;

alter table user_tabs
   add constraint FKmaajj3ua3djfl3tj52db22ypm
   foreign key (post_id)
   references posts
   on update cascade;

alter table user_tabs
   add constraint FKgo6h5c1wskrntjm2rkcrgmvih
   foreign key (user_id)
   references users
   on update cascade;
commit;