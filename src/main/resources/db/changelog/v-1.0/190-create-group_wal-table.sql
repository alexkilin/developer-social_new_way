create table group_wal (
   group_id int8 not null,
    post_id int8 not null,
    primary key (group_id, post_id)
)

next

alter table group_wal
   add constraint UK_qk3qxbrms2ns6201obdp9ap3o unique (post_id)

next

alter table group_wal
   add constraint FKnve304lx290f7fxpf5tcsaqaj
   foreign key (group_id)
   references groups

next

alter table group_wal
   add constraint FKg4c2uw50umb9fwvama4poy65d
   foreign key (post_id)
   references posts

next