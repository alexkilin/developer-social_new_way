begin;

alter table playlist_has_audios
    drop constraint FKifxgpubnspdey7x7876eb5j2o;

alter table playlist_has_audios
    drop constraint FKf94m5c5nwywjn4brktc6f5k2s;

alter table playlist_has_audios
   add constraint FKifxgpubnspdey7x7876eb5j2o
   foreign key (audios_id)
   references audios
   on update cascade;

alter table playlist_has_audios
   add constraint FKf94m5c5nwywjn4brktc6f5k2s
   foreign key (playlist_id)
   references playlists
   on update cascade;
commit;
