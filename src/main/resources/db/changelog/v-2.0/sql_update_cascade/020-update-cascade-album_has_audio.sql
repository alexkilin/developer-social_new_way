begin;

alter table album_has_audio
drop constraint FK4wutmsbgisseb2mxxe8nutnqe;

alter table album_has_audio
drop constraint FK8v9jpoytyn00yhy2wd4vvjg1y;


alter table album_has_audio
   add constraint FK4wutmsbgisseb2mxxe8nutnqe
   foreign key (audios_id)
   references audios
    on update cascade;

alter table album_has_audio
   add constraint FK8v9jpoytyn00yhy2wd4vvjg1y
   foreign key (album_id)
   references album_audios
   on update cascade;

commit;