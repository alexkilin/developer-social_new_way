create table album_has_image (
   album_id int8 not null,
    image_id int8 not null,
    primary key (album_id, image_id)
)

next

alter table album_has_image
   add constraint FKcdy622l6cw7ls5chlfa0admtk
   foreign key (image_id)
   references images

next

alter table album_has_image
   add constraint FKtq22d73bviic6fhl3sk3w7x4s
   foreign key (album_id)
   references album_image

next