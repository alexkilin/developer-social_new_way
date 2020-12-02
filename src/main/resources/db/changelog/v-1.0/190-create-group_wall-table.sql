create table group_wall (
   group_id int8 not null,
    post_id int8 not null,
    primary key (group_id, post_id)
)

next

alter table group_wall
   add constraint UK_k0mxoo8evldcjcq1iva6o7bvq unique (post_id)

next

alter table group_wall
   add constraint FKhde1qpuipv4ouvqw02gxruc2h
   foreign key (group_id)
   references groups

next

alter table group_wall
   add constraint FKqkll3lqcxuse5558nxawjgmp4
   foreign key (post_id)
   references posts

next