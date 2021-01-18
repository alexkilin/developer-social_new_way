begin;

alter table album_has_image
drop constraint FKcdy622l6cw7ls5chlfa0admtk;

alter table album_has_image
drop constraint FKtq22d73bviic6fhl3sk3w7x4s;

alter table album_has_image
   add constraint FKcdy622l6cw7ls5chlfa0admtk
   foreign key (image_id)
   references images
   on update cascade;



alter table album_has_image
   add constraint FKtq22d73bviic6fhl3sk3w7x4s
   foreign key (album_id)
   references album_image
   on update cascade;
commit;