begin;

alter table post_comment
    drop constraint FKb5nt73rr3r7qun4cihgwpyh9s;

alter table post_comment
    drop constraint FKl6wxpohlclbjykgpkjdqphmfg;

alter table post_comment
   add constraint FKb5nt73rr3r7qun4cihgwpyh9s
   foreign key (comment_id)
   references comments
    on update cascade;

alter table post_comment
   add constraint FKl6wxpohlclbjykgpkjdqphmfg
   foreign key (post_id)
   references posts
   on update cascade;
commit;