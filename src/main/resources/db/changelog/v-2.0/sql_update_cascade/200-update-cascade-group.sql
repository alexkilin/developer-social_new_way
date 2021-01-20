begin;

alter table "groups"
    drop constraint FKb9i5f3jw8rrurcge9anwl1o9t;

alter table "groups"
    drop constraint FKke9gpecgx7u1oef8lsd9tax3c;


alter table "groups"
   add constraint FKb9i5f3jw8rrurcge9anwl1o9t
   foreign key (group_category_id)
   references group_category
   on update cascade;

alter table "groups"
   add constraint FKke9gpecgx7u1oef8lsd9tax3c
   foreign key (owner_id)
   references users
    on update cascade;
commit;