create table "like" (
    id int8 not null,
    like_type int4 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table "like"
    add constraint FKn6yllf2sb7ttvbrwya4lxkx60
    foreign key (user_id)
    references users

next