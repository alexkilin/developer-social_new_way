create table playlists (
    playlist_id int8 not null,
    image varchar(255),
    name varchar(255),
    persist_date timestamp not null,
    user_id int8 not null,
    primary key (playlist_id)
)

next

alter table playlists
   add constraint FKtgjwvfg23v990xk7k0idmqbrj
   foreign key (user_id)
   references users

next