begin;

alter table audios
drop constraint FKmbwry03s0jccvgqefrlv3ln1j;


alter table audios
   add constraint FKmbwry03s0jccvgqefrlv3ln1j
   foreign key (media_id)
   references media
   on update cascade;
   commit;