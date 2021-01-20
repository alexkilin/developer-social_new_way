alter sequence albums_seq restart with 1;
update albums set id = nextval('albums_seq');


