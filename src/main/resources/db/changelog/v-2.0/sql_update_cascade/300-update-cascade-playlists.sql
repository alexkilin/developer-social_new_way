begin;

alter table playlists
    drop constraint FKtgjwvfg23v990xk7k0idmqbrj;

alter table playlists
   add constraint FKtgjwvfg23v990xk7k0idmqbrj
   foreign key (user_id)
   references users
    on update cascade;
commit;