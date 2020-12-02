create table album_image (
   album_id int8 not null,
    primary key (album_id)
)

next

alter table album_image
   add constraint FKktle04uj7x3exwin0gfq0xdtd
   foreign key (album_id)
   references albums

next