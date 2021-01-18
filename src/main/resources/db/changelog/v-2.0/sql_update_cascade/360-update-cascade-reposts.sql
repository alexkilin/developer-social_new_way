begin;

alter table reposts
    drop constraint FKersix1dfhot0h5xp4umnh2mgr;

alter table reposts
    drop constraint FKdtkd7swbi33b8w536k29yjbom;


alter table reposts
   add constraint FKersix1dfhot0h5xp4umnh2mgr
   foreign key (post_id)
   references posts
   on update cascade;

alter table reposts
   add constraint FKdtkd7swbi33b8w536k29yjbom
   foreign key (user_id)
   references users
   on update cascade;
commit;