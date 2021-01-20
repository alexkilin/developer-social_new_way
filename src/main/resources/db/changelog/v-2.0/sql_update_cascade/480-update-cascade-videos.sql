begin;

alter table videos
    drop constraint FKap12qkmsguts1paap4tvqv57k;

alter table videos
   add constraint FKap12qkmsguts1paap4tvqv57k
   foreign key (media_id)
   references media
    on update cascade;
commit;
