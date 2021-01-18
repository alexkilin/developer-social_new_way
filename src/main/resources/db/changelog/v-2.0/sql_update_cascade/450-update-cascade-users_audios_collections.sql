begin;

alter table users_audios_collections
    drop constraint FK53yqrthjucjf6vqvm26s2il4k;

alter table users_audios_collections
    drop constraint FK94u8ir5f3j7wfwbhyddx826jj;

alter table users_audios_collections
   add constraint FK53yqrthjucjf6vqvm26s2il4k
   foreign key (audio_id)
   references audios
    on update cascade;


alter table users_audios_collections
   add constraint FK94u8ir5f3j7wfwbhyddx826jj
   foreign key (user_id)
   references users
   on update cascade;

commit;