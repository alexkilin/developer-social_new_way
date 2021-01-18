begin;

-- alter table users
--     drop constraint UK_6dotkott2kjsp8vw4d0m25fb7;

alter table users
    drop constraint FKl7ve5em3l5axquvrlf5e39dpl;

alter table users
    drop constraint FK4qu1gr772nnf6ve5af002rwya;

-- alter table users
--    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
-- on update cascade;

alter table users
   add constraint FKl7ve5em3l5axquvrlf5e39dpl
   foreign key (active_id)
   references active
on update cascade;

alter table users
   add constraint FK4qu1gr772nnf6ve5af002rwya
   foreign key (role_id)
   references role
   on update cascade;
commit;