  begin;

alter table followers
    drop constraint FKgl2oa2hkhtgh7s8vf7dnpm0vt;

alter table followers
    drop constraint FKbpy0ybtaj5k196w6hbon3krt8;

alter table followers
   add constraint FKgl2oa2hkhtgh7s8vf7dnpm0vt
   foreign key (follower_id)
   references users
   on update cascade;


alter table followers
   add constraint FKbpy0ybtaj5k196w6hbon3krt8
   foreign key (user_id)
   references users
   on update cascade;
commit;