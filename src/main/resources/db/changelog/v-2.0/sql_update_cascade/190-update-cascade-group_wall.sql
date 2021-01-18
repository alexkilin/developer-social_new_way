begin;
--
-- alter table group_wall
--     drop constraint UK_k0mxoo8evldcjcq1iva6o7bvq;

alter table group_wall
    drop constraint FKhde1qpuipv4ouvqw02gxruc2h;

alter table group_wall
    drop constraint FKqkll3lqcxuse5558nxawjgmp4;

-- alter table group_wall
--    add constraint UK_k0mxoo8evldcjcq1iva6o7bvq unique (post_id)
--    on update cascade;


alter table group_wall
   add constraint FKhde1qpuipv4ouvqw02gxruc2h
   foreign key (group_id)
   references groups
on update cascade;

alter table group_wall
   add constraint FKqkll3lqcxuse5558nxawjgmp4
   foreign key (post_id)
   references posts
   on update cascade;
   commit;