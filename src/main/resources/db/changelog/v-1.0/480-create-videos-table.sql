create table videos (
   author varchar(255),
    icon varchar(255),
    name varchar(255),
    media_id int8 not null,
    primary key (media_id)
)

next

alter table videos
   add constraint FKap12qkmsguts1paap4tvqv57k
   foreign key (media_id)
   references media

next