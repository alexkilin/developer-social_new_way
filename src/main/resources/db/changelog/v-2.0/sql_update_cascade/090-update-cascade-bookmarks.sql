begin;

alter table bookmarks
    drop constraint FK7nbb4ldgek7ux7y6lu0y4g826;

alter table bookmarks
    drop constraint FKdbsho2e05w5r13fkjqfjmge5f;



alter table bookmarks
   add constraint FK7nbb4ldgek7ux7y6lu0y4g826
   foreign key (post_id)
   references posts
   on update cascade;


alter table bookmarks
   add constraint FKdbsho2e05w5r13fkjqfjmge5f
   foreign key (user_id)
   references users
   on update cascade;
   commit;
