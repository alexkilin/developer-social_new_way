begin;

alter table messages
    drop constraint FKpsmh6clh3csorw43eaodlqvkn;

alter table messages
   add constraint FKpsmh6clh3csorw43eaodlqvkn
   foreign key (user_id)
   references users
    on update cascade;
commit;