create table user_languages (
   user_id int8 not null,
    language_id int8 not null,
    primary key (user_id, language_id)
)

next

alter table user_languages
   add constraint FK85eswkit45d5iy2oeuajp6ms5
   foreign key (language_id)
   references language

next

alter table user_languages
   add constraint FKt3sjkb7b30p03i378qdcr2s9k
   foreign key (user_id)
   references users

next