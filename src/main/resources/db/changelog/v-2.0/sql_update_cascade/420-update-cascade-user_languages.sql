begin;

alter table user_languages
    drop constraint FK85eswkit45d5iy2oeuajp6ms5;

alter table user_languages
    drop constraint FKt3sjkb7b30p03i378qdcr2s9k;

alter table user_languages
   add constraint FK85eswkit45d5iy2oeuajp6ms5
   foreign key (language_id)
   references "language"
   on update cascade;


alter table user_languages
   add constraint FKt3sjkb7b30p03i378qdcr2s9k
   foreign key (user_id)
   references users
on update cascade;
commit;