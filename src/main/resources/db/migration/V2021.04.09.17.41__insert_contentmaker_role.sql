insert into role (id, name) values (3, 'CONTENT_MAKER');

insert into users (user_id,about_me,image,city,date_of_birth,
                   education,email,first_name,is_enable,last_name,
                   last_redaction_date,link_site,password,persist_date,
                   profession,status,active_id,role_id)
values(
101,
'My description about life - Contentmaker',
'www.myavatar53.ru/9090',
'SPb',
'2008-05-30 08:20:12.121000',
'MIT University',
'user53@user.ru',
'User53',
1,
'Contentmaker',
'2020-12-03 02:44:11.523455',
'www.mysite.ru',
'contentmaker',
'2020-12-03 02:44:10.329421',
'Contentmaker',
'free',
2,
3
);
