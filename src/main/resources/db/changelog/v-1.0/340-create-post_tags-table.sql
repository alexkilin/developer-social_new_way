create table post_tags (
   post_id int8 not null,
    tags_id int8 not null,
    primary key (post_id, tags_id)
)

next

alter table post_tags
   add constraint FKc9w0wcpokniw86jioo5rrb37s
   foreign key (tags_id)
   references tags

next

alter table post_tags
   add constraint FKkifam22p4s1nm3bkmp1igcn5w
   foreign key (post_id)
   references posts

next