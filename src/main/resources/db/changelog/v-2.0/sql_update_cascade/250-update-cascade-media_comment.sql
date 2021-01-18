begin;

alter table media_comment
    drop constraint FKqddyww0crb9h5943ecanke9p2;

alter table media_comment
    drop constraint FKc4g13ic2kajrl02anuikf5bao;


alter table media_comment
   add constraint FKqddyww0crb9h5943ecanke9p2
   foreign key (comment_id)
   references comments
   on update cascade;

alter table media_comment
   add constraint FKc4g13ic2kajrl02anuikf5bao
   foreign key (media_id)
   references media
   on update cascade;
commit;
