create table groups (
   id int8 not null,
    address_image_group varchar(255),
    description varchar(255),
    last_redaction_date timestamp not null,
    link_site varchar(255),
    name varchar(255),
    persist_date timestamp not null,
    group_category_id int8,
    owner_id int8 not null,
    primary key (id)
)

next

alter table groups
   add constraint FKb9i5f3jw8rrurcge9anwl1o9t
   foreign key (group_category_id)
   references group_category

next

alter table groups
   add constraint FKke9gpecgx7u1oef8lsd9tax3c
   foreign key (owner_id)
   references users

next