begin;

alter table message_like
    drop constraint FKo77h7ssfcsg4u2d3p4xyxj6w2;

alter table message_like
    drop constraint FK6us66cd4998bp9nbyqyg9jp4e;

alter table message_like
   add constraint FKo77h7ssfcsg4u2d3p4xyxj6w2
   foreign key ("like_id")
   references "like"
    on update cascade;

alter table message_like
   add constraint FK6us66cd4998bp9nbyqyg9jp4e
   foreign key (message_id)
   references messages
   on update cascade;
commit;