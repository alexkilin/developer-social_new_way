begin;

-- alter table media_like
--     drop constraint FK2p0b7gpbngslllwcnlovjrhwu;
--
-- alter table media_like
--     drop constraint FKdq31g0obh9yexo3jy1lx4jvs6;

alter table media_like
   add constraint FK2p0b7gpbngslllwcnlovjrhwu
   foreign key ("like_id")
   references "like"
   on update cascade;

alter table media_like
   add constraint FKdq31g0obh9yexo3jy1lx4jvs6
   foreign key (media_id)
   references media
   on update cascade;
commit;