create table message_like (
   "like_id" int8 not null,
    message_id int8 not null,
    primary key ("like_id")
)

next

alter table message_like
   add constraint FKo77h7ssfcsg4u2d3p4xyxj6w2
   foreign key ("like_id")
   references "like"

next

alter table message_like
   add constraint FK6us66cd4998bp9nbyqyg9jp4e
   foreign key (message_id)
   references messages

next