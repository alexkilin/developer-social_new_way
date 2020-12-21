create table user_tabs (
   id int8 not null,
    persist_date timestamp not null,
    post_id int8 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table user_tabs
   add constraint FKmaajj3ua3djfl3tj52db22ypm
   foreign key (post_id)
   references posts

next

alter table user_tabs
   add constraint FKgo6h5c1wskrntjm2rkcrgmvih
   foreign key (user_id)
   references users

next