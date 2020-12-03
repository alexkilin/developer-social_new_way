create table bookmarks (
   id int8 not null,
    persist_date timestamp not null,
    post_id int8 not null,
    user_id int8 not null,
    primary key (id)
)

next

alter table bookmarks
   add constraint FK7nbb4ldgek7ux7y6lu0y4g826
   foreign key (post_id)
   references posts

next

alter table bookmarks
   add constraint FKdbsho2e05w5r13fkjqfjmge5f
   foreign key (user_id)
   references users

next