create table album_has_audio (
    album_id int8 not null,
    audios_id int8 not null,
    primary key (album_id, audios_id)
)

next

alter table album_has_audio
   add constraint FK4wutmsbgisseb2mxxe8nutnqe
   foreign key (audios_id)
   references audios

next

alter table album_has_audio
   add constraint FK8v9jpoytyn00yhy2wd4vvjg1y
   foreign key (album_id)
   references album_audios

next