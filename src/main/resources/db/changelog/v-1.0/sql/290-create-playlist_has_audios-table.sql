create table playlist_has_audios (
   playlist_id int8 not null,
    audios_id int8 not null,
    primary key (playlist_id, audios_id)
)

next

alter table playlist_has_audios
   add constraint FKifxgpubnspdey7x7876eb5j2o
   foreign key (audios_id)
   references audios

next

alter table playlist_has_audios
   add constraint FKf94m5c5nwywjn4brktc6f5k2s
   foreign key (playlist_id)
   references playlists

next