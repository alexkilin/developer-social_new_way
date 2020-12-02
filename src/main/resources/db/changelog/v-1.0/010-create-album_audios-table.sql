create table album_audios (
    album_id int8 not null,
    primary key (album_id)
)

next

alter table album_audios
   add constraint FKn8fkl5522npp79gun310v08gc
   foreign key (album_id)
   references albums

next