create table post_comment (
   comment_id int8 not null,
    post_id int8 not null,
    primary key (comment_id)
)

next

alter table post_comment
   add constraint FKb5nt73rr3r7qun4cihgwpyh9s
   foreign key (comment_id)
   references comments

next

alter table post_comment
   add constraint FKl6wxpohlclbjykgpkjdqphmfg
   foreign key (post_id)
   references posts

next