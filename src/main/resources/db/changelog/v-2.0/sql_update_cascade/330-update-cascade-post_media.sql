begin;

-- alter table post_media
--     drop constraint UK_cbh3kwx9ocobb3y3jn93nth0o;

alter table post_media
    drop constraint FKedvegnxhyt4dke852jfbbq1wp;

alter table post_media
    drop constraint FK1urcum9dtf0vgul7k405f4r2d;

-- alter table post_media
--    add constraint UK_cbh3kwx9ocobb3y3jn93nth0o unique (media_id)
--    on update cascade;

alter table post_media
   add constraint FKedvegnxhyt4dke852jfbbq1wp
   foreign key (media_id)
   references media
   on update cascade;

alter table post_media
   add constraint FK1urcum9dtf0vgul7k405f4r2d
   foreign key (post_id)
   references posts
    on update cascade;
commit;