# Документация JM Developer Social

##Как настроить запуск

- В проекте реализованно два профиля: **local** и **dev**. Для выбора профиля, добавьте в program arguments **--spring.profiles.active=local** или **--spring.profiles.active=dev** соответственно. 


## Сущности

### Users

#### Поля:

- **id** - уникальный идентификационный номер пользователя;
- **first_name** - имя пользователя;
- **last_name** - фамилия пользователя;
- **date_of_birth** - дата рождения пользователя;
- **education** - образование пользователя;
- **about_me** - информация о пользователе;
- **status_id** - идентификатор статуса пользователя;
- **active_id** - идентификатор состояния пользователя;
- **avatarka** - аватарка пользователя;
- **email** - адрес электронной почты пользователя;
- **password** - пароль пользователя;
- **persist_date** - время регистрации пользователя;
- **last_redaction_date** - дата последней редакции;
- **is_enable** - подтверждение почты пользователя;
- **role_id** - идентификационный номер пользователя;
- **city** - город рождения/проживания пользователя;
- **link_site** - ссылка на сайт пользователя;

```
Таблица, которая описывает основной пользовательский функционал, который соответствует стандартному
функционалу социальных сетей: переписка с другими пользователями, посты, добавление различных медиа документов. 
Наделен ролью. Связан с остальными сущностями через уникальный идентификационный номер.
```

### Roles

#### Поля:

- **id** - уникальный идентификационный номер роли;
- **name** - наименование роли;
```
Определяет порядок прав действий пользователя в системе.
```

### Languages

#### Поля:

- **id** - уникальный идентификационный номер языка;
- **name** - наименование языка;
```
Определяет набор языков пользователей в системе.
```

### UserLanguages

#### Поля:

- **user_id** - уникальный идентификационный номер пользователя;
- **language_id** - уникальный идентификационный номер языка;
```
Производная таблица связи many-to-many сущностей пользователей и языков.
```

### Friends

#### Поля:

- **id** - уникальный идентификационный номера связи сущностей пользователя и друга;
- **user_id** - уникальный идентификационный номер пользователя;
- **friend_id** - уникальный идентификационный номер друга пользователя;
```
Таблица, которая связывает поле пользователя с полем его друга через связь many-to-one.
```

### Followers

#### Поля:

- **id** - уникальный идентификационный номера связи сущностей пользователя и его подписчиков;
- **user_id** - уникальный идентификационный номер пользователя;
- **follower_id** - уникальный идентификационный номер подписчика пользователя;
```
Таблица, которая связывает поле пользователя с полями его друзей через связь many-to-one.
```

### Status

#### Поля:

- **id** - уникальный идентификационный номер статуса;
- **name** - наименование статуса;
```
Определяет набор статусов пользователей в системе.
```

### Active

#### Поля:

- **id** - уникальный идентификационный номер активности;
- **name** - наименование активности;
```
Определяет набор статусов активности пользователей в системе.
```

### Groups

#### Поля:

- **id** - уникальный идентификационный номер группы;
- **name** - наименование группы;
- **persist_date** - дата создания группы;
- **last_redaction_date** - дата последней редакции группы;
- **link_site** - ссылка на сайт;
- **category_id** - уникальный идентификационный номер категории группы;
- **owner_id** - уникальный идентификационный номер владельца группы;
```
Таблица, которая содержит в себе записи обо всех существующих группах с информацией о ней и ее владельце.
У группы есть связанная сущность "Стена", на которой пользователи могут выкладывать посты, комментировать.
их, лайкать.  
```

### GroupHasUser

#### Поля

- **id** - уникальный идентификационный номера связи сущностей группы и ее подписчиков;
- **group_id** - уникальный идентификационный номер группы;
- **user_id** - уникальный идентификационный номер пользователя;
- **persist_date** - дата подписки пользователя;
```
Таблица, которая связывает информацию об уникальных идентификационных номерах
группы и пользователя и дату образования этой связи.
```

### GroupCategory

#### Поля:

- **id** - уникальный идентификационный номер категории;
- **category** - наименование категории;
```
Сущность, которая связывает уникальный идентификационный номер категории и ее наименование.
```

### GroupWall

#### Поля:

- **group_id** - уникальный идентификационный номер группы;
- **post_id** - уникальный идентификационный номер поста;
```
Производная таблица, которая связывает поле номера группы с полями номеров постов через связь one-to-many.
```

### BookMarks

#### Поля:

- **user_saver_id** - уникальный идентификационный номер сохранившего пользователя;
- **post_id** - уникальный идентификационный номер поста;
```
Производная таблица, которая связывает поле номера сохранившего пользователя с полями номеров 
постов через связь one-to-many.
```

### Posts

#### Поля:

- **id** - уникальный идентификационный номер поста;
- **title** - наименование поста;
- **text** - текстовая информация в посте;
- **user_id** - уникальный идентификационный номер пользователя-владельца поста;
- **persist_date** - время создания поста;
- **last_redaction_date** - время последней редакции поста;
```
Таблица, которая инициализирует информацию о посте.
```

### PostMedia

#### Поля:

- **post_id** - уникальный идентификационный номер поста;
- **media_id** - уникальный идентификационный номер медиа документа;
```
Производная таблица, которая связывает поле номера поста с полями номеров медиа документов через связь one-to-many.
```

### Comment

#### Поля:

- **id** - уникальный идентификационный номер комментария;
- **user_id** - уникальный идентификационный номер пользователя;
- **comment_type** - тип комментария;
- **comment** - текстовая информация в комментарии;
- **persist_date** - время создания комментария;
- **last_redaction_date** - время последней редакции комментария;
```
Таблица, которая содержит информацию о созданном комментарии.
```

### CommentLike

#### Поля:

- **like_id** - уникальный идентификационный номер поставленного лайка;
- **comment_id** - уникальный идентификационный номер комментария;
```
Таблица, которая связывает информацию о лайке и номере комментария.
```

### PostComments

#### Поля:

- **post_id** - уникальный идентификационный номер поста;
- **comment_id** - уникальный идентификационный номер комментария;
```
Таблица, которая связывает информацию о номере поста и комментария.
```

### PostLike

#### Поля:

- **like_id** - уникальный идентификационный номер поставленного лайка;
- **post_id** - уникальный идентификационный номер поста;
```
Таблица, которая связывает информацию о номере лайка и поста.
```

### Likes

#### Поля:

- **id** - уникальный идентификационный номер лайка;
- **user_id** - уникальный идентификационный номер пользователя;
- **like_type** - тип лайка;
- **persist_date** - время создания лайка;
```
Таблица, которая содержит информацию о созданном лайке.
```

### MediaLikes

#### Поля:

- **like_id** - уникальный идентификационный номер лайка;
- **media_id** - уникальный идентификационный номер медиа документа;
```
Производная таблица, которая связывает поле номера лайка с полями номеров медиа документов через связь one-to-many.
```

### MediaComments

#### Поля:

- **comment_id** - уникальный идентификационный номер комментария;
- **media_id** - уникальный идентификационный номер медиа документа;
```
Производная таблица, которая связывает поле номера комментария с полями номеров медиа 
документов через связь one-to-many.
```

### Messages

#### Поля:

- **id** - уникальный идентификационный номер сообщения;
- **message** - вложенная в сообщение информация;
- **is_unread** - статус прочтения посланного сообщения;
- **persist_date** - время создания сообщения;
- **last_redaction_date** - время последней редакции сообщения;
```
Таблица, которая содержит информацию о созданном сообщении. 
```

### Chats

#### Поля:

- **id** - уникальный идентификационный номер чата;
- **title** - наименование чата;
- **persist_date** - время создания чата;
- **user_sender_id** - уникальный идентификационный номер пользователя-отправителя;
- **user_receiver_id** - уникальный идентификационный номер пользователя-получателя;
- **message_id** - уникальный идентификационный номер отправленного сообщения;
```
Таблица, которая содержит информацию о чате, в том числе one-to-one связь с уникальным 
идентификационным номером пользователя-отправителя и пользователя-получателя, а также
связь one-to-many c уникальным идентификационным номером пользователя. В чате происходит
обмен данными различного типа между пользователями.
```

### StarredMessages

#### Поля:

- **user_id** - уникальный идентификационный номер пользователя;
- **message_id** - уникальный идентификационный номер сообщения;
```
Производная таблица связи one-to-many сущностей пользователя и сообщений.
```

### Media

#### Поля:

- **id** - уникальный идентификационный медиа документа;
- **user_id** - наименование медиа документа;
- **url** - ссылка на медиа документ;
- **type_id** - тип медиа документа;
- **persist_date** - время создания медиа документа;
```
Таблица, которая содержит информацию о медиа документе для возможности предоставления его самого
и информации о нем пользователю. Медиа документ может быть представлен в виде видое, изображения или
аудиозаписи.
```

### MediaMessages

#### Поля:

- **message_id** - уникальный идентификационный номер сообщения;
- **media_id** - уникальный идентификационный номер медиа документа;
```
Производная таблица, которая связывает поле номера сообщения с полями номеров 
медиа документов через связь one-to-many.
```

### Albums

#### Поля:

- **id** - уникальный идентификационный номер альбома;
- **name** - наименование альбома;
- **persist_date** - время создания альбома;
- **media_id** - уникальный идентификационный номер медиа документа;
```
Таблица, которая содержит информацию об альбоме.
```

### Audios

#### Поля:

- **media_id** - уникальный идентификационный номер медиа документа;
- **icon** - иконка для аудио файла;
```
Таблица, которая связывает информацию о номере медиа документа и его иконки
```

### Videos

#### Поля:

- **media_id** - уникальный идентификационный номер медиа документа;
- **icon** - иконка для видео файла;
```
Таблица, которая связывает информацию о номере медиа документа и его иконки
```

### Images

#### Поля:

- **media_id** - уникальный идентификационный номер медиа документа;
- **description** - иконка для аудио файла;
```
Таблица, которая связывает информацию о номере медиа документа и его описании 
```

[Схема](https://dbdiagram.io/d/5f0bd4fe0425da461f04931b)
