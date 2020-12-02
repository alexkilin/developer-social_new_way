create table post_media (
   post_id int8 not null,
    media_id int8 not null,
    primary key (post_id, media_id)
)

next

alter table post_media
   add constraint UK_cbh3kwx9ocobb3y3jn93nth0o unique (media_id)

next

alter table post_media
   add constraint FKedvegnxhyt4dke852jfbbq1wp
   foreign key (media_id)
   references media

next

alter table post_media
   add constraint FK1urcum9dtf0vgul7k405f4r2d
   foreign key (post_id)
   references posts

next