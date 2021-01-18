begin;

alter table friends
    drop constraint FKglgwqjy5yrqnkoupjt738o12c;

alter table friends
    drop constraint FKnnacc56g8l3e7hk26qse3l1g7;

alter table friends
   add constraint FKglgwqjy5yrqnkoupjt738o12c
   foreign key (friend_id)
   references users
   on update cascade;

alter table friends
   add constraint FKnnacc56g8l3e7hk26qse3l1g7
   foreign key (user_id)
   references users
   on update cascade;
   commit;