begin;

alter table post_tags
    drop constraint FKc9w0wcpokniw86jioo5rrb37s;

alter table post_tags
    drop constraint FKkifam22p4s1nm3bkmp1igcn5w;

alter table post_tags
   add constraint FKc9w0wcpokniw86jioo5rrb37s
   foreign key (tags_id)
   references tags
   on update cascade;

alter table post_tags
   add constraint FKkifam22p4s1nm3bkmp1igcn5w
   foreign key (post_id)
   references posts
   on update cascade;
commit;