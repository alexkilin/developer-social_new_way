create table users (
   user_id int8 not null,
    about_me varchar(255),
    image varchar(255),
    city varchar(255),
    date_of_birth timestamp,
    education varchar(255),
    email varchar(255),
    first_name varchar(255) not null,
    is_enable int4,
    last_name varchar(255) not null,
    last_redaction_date timestamp not null,
    link_site varchar(255),
    password varchar(255),
    persist_date timestamp not null,
    profession varchar(255),
    status varchar(255),
    active_id int8 not null,
    role_id int8 not null,
    primary key (user_id)
)

next

alter table users
   add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)

next

alter table users
   add constraint FKl7ve5em3l5axquvrlf5e39dpl
   foreign key (active_id)
   references active

next

alter table users
   add constraint FK4qu1gr772nnf6ve5af002rwya
   foreign key (role_id)
   references role

next