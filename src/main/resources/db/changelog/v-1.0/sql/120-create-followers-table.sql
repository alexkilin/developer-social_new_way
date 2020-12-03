create table followers (
   id int8 not null,
    follower_user_id int8 not null,
    user_user_id int8 not null,
    primary key (id)
)

next

alter table followers
   add constraint FKgl2oa2hkhtgh7s8vf7dnpm0vt
   foreign key (follower_user_id)
   references users

next

alter table followers
   add constraint FKbpy0ybtaj5k196w6hbon3krt8
   foreign key (user_user_id)
   references users

next