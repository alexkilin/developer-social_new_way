Insert into active(id, name) values(3, 'test'), (200, 'ACTIVE'), (201, 'Offline');
Insert into role(id, name) values(1, 'USER'),(200, 'USER'), (201, 'ADMIN');


Insert into Users
(user_id, first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id, password)
values (200,'Admin1','LastNameAdmin1', 'admin2@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:02.437378', 200, 200, 'userpass0');


Insert into media (id, url, persist_date, media_type, user_id)
values(200, 'www.myaudio7.ru', '2020-08-04 16:47:23.940175', 1, 200);

Insert into audios ( album, author, icon, name, length, listening, media_id)
values(  'AlbumTestName 7', 'Test Author 7', 'TestIcon7', 'AudioTestName 7', '365', '20', 200);


Insert into Users(user_id,first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id, password) values (666,'user666','user666', 'admin666@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:03.157535', 3, 1, 'user666');


Insert into Users
(user_id, first_name, last_name, date_of_birth, about_me, education, status, active_id, image, email, password,
persist_date, last_redaction_date, is_enable, role_id, city, profession, link_site)
values (201,'Admin2', 'LastNameAdmin2', '2008-05-30 15:20:12.121000','My description about life - Admin2',
'MIT University', 'Pureness and perfection', 200, 'www.myavatar0.ru/9090', 'admin3@user.ru', 'userpass0',
 '2020-08-04 16:42:02.437378', '2020-08-04 16:42:03.157535', '1', 200, 'SPb', 'Plumber', 'www.mys.ru');


UPDATE users SET audio_last_played=200;