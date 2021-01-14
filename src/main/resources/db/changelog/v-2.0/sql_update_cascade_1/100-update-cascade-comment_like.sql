 begin;

alter table comment_like
    drop constraint FKl1mu2ryf4k4ps93b5b52q2m8i;

alter table comment_like
    drop constraint FKkldgc9u7sbqv0djmeeusvisue;


alter table comment_like
   add constraint FKl1mu2ryf4k4ps93b5b52q2m8i
   foreign key (comment_id)
   references comments
on update cascade;

alter table comment_like
   add constraint FKkldgc9u7sbqv0djmeeusvisue
   foreign key ("like_id")
   references "like"
on update cascade;
commit;