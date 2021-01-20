begin;

alter table images
    drop constraint FKd1n6j98c6ebkpm1qmlfjrg4jf;

alter table images
   add constraint FKd1n6j98c6ebkpm1qmlfjrg4jf
   foreign key (media_id)
   references media
   on update cascade;
commit;