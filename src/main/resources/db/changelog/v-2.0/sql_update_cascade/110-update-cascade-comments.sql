 begin;

alter table comments
    drop constraint FK8omq0tc18jd43bu5tjh6jvraq;

alter table comments
   add constraint FK8omq0tc18jd43bu5tjh6jvraq
   foreign key (user_id)
   references users
on update cascade;
commit;