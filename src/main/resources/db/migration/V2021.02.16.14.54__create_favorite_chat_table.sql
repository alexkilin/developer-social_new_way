create sequence favorite_chat_seq as bigint start 1 increment 1;

create table favorite_chat
(
    id           bigint    not null
        constraint favorite_chat_pkey
        primary key,
    persist_date timestamp not null,
    chat_id      bigint    not null
        constraint favorite_chat_chats_id_fk
        references chats
        on update cascade,
    user_id      bigint    not null
        constraint favorite_chat_users_user_id_fk
        references users
        on update cascade
);

alter table favorite_chat
    owner to postgres;

insert into favorite_chat (id, persist_date, chat_id, user_id) values
(1,'2020-12-02 15:36:04.693556', 5,1),(2,'2020-12-02 15:36:04.693826', 3,1),(3,'2020-12-02 15:36:04.693910', 2,41),
(4,'2020-12-02 15:36:04.693991', 1,41),(5,'2020-12-02 15:36:04.694080', 4,42),(6,'2020-12-02 15:36:04.694163', 7,42),
(7,'2020-12-02 15:36:04.694239', 3,43),(8,'2020-12-02 15:36:04.694320', 9,43),(9,'2020-12-02 15:36:04.694396', 1,44),
(10,'2020-12-02 15:36:04.694472', 10,44),(11,'2020-12-02 15:36:04.694548', 7,45),(12,'2020-12-02 15:36:04.694628', 6,45),
(13,'2020-12-02 15:36:04.694707', 9,46),(14,'2020-12-02 15:36:04.694786', 8,46),(15,'2020-12-02 15:36:04.694866', 10,47),
(16,'2020-12-02 15:36:04.694945', 15,47),(17,'2020-12-02 15:36:04.695024', 6,48),(18,'2020-12-02 15:36:04.695103', 12,48),
(19,'2020-12-02 15:36:04.695183', 8,49),(20,'2020-12-02 15:36:04.695262', 13,49),(21,'2020-12-02 15:36:04.699269', 15,50);