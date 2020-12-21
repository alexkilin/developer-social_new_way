create table images (
   description varchar(255),
    media_id int8 not null,
    primary key (media_id)
)

next

alter table images
   add constraint FKd1n6j98c6ebkpm1qmlfjrg4jf
   foreign key (media_id)
   references media

next