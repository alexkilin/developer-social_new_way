begin;

alter table media
    drop constraint FKkqrdr00pw2v5k3q4tqv76cy6i;

alter table media
    drop constraint FKnd8hh0yn7qvv4pqyk8mg7l1ox;

alter table media
   add constraint FKkqrdr00pw2v5k3q4tqv76cy6i
   foreign key (album_id)
   references albums
   on update cascade;


alter table media
   add constraint FKnd8hh0yn7qvv4pqyk8mg7l1ox
   foreign key (user_id)
   references users
   on update cascade;
commit;
