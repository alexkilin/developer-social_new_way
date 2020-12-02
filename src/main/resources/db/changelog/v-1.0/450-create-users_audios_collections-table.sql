create table users_audios_collections (
   user_id int8 not null,
    audio_id int8 not null,
    primary key (user_id, audio_id)
)

next

alter table users_audios_collections
   add constraint FK53yqrthjucjf6vqvm26s2il4k
   foreign key (audio_id)
   references audios

next

alter table users_audios_collections
   add constraint FK94u8ir5f3j7wfwbhyddx826jj
   foreign key (user_id)
   references users

next