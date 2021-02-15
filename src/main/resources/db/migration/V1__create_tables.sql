create sequence active_seq start 1 increment 1;
create sequence albums_seq start 1 increment 1;
create sequence bookmarks_seq start 1 increment 1;
create sequence chats_seq start 1 increment 1;
create sequence comments_seq start 1 increment 1;
create sequence followers_seq start 1 increment 1;
create sequence friends_seq start 1 increment 1;
create sequence group_category_seq start 1 increment 1;
create sequence group_has_user_seq start 1 increment 1;
create sequence groups_seq start 1 increment 1;
create sequence language_seq start 1 increment 1;
create sequence like_seq start 1 increment 1;
create sequence media_seq start 1 increment 1;
create sequence messages_seq start 1 increment 1;
create sequence playlists_seq start 1 increment 1;
create sequence posts_seq start 1 increment 1;
create sequence reposts_seq start 1 increment 1;
create sequence role_seq start 1 increment 1;
create sequence tags_seq start 1 increment 1;
create sequence topics_seq start 1 increment 1;
create sequence user_has_album_seq start 1 increment 1;
create sequence user_tabs_seq start 1 increment 1;
create sequence users_seq start 1 increment 1;


-- auto-generated definition
create table active
(
    id   bigint not null
        constraint active_pkey
            primary key,
    name varchar(255)
);

alter table active
    owner to postgres;

-- auto-generated definition
create table role
(
    id   bigint not null
        constraint role_pkey
            primary key,
    name varchar(255)
);

alter table role
    owner to postgres;

-- auto-generated definition
create table users
(
    user_id             bigint       not null
        constraint users_pkey
            primary key,
    about_me            varchar(255),
    image               varchar(255),
    city                varchar(255),
    date_of_birth       date,
    education           varchar(255),
    email               varchar(255)
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    first_name          varchar(255) not null,
    is_enable           integer,
    last_name           varchar(255) not null,
    last_redaction_date timestamp    not null,
    link_site           varchar(255),
    password            varchar(255),
    persist_date        timestamp    not null,
    profession          varchar(255),
    status              varchar(255),
    active_id           bigint       not null
        constraint fkl7ve5em3l5axquvrlf5e39dpl
            references active
            on update cascade,
    role_id             bigint       not null
        constraint fk4qu1gr772nnf6ve5af002rwya
            references role
            on update cascade
);

alter table users
    owner to postgres;

-- auto-generated definition
create table albums
(
    id            bigint    not null
        constraint albums_pkey
            primary key,
    icon          varchar(255),
    update_date   timestamp,
    media_type    integer   not null,
    name          varchar(255),
    persist_date  timestamp not null,
    user_owner_id bigint
        constraint fknbt8mlh802xtbdf5oue3gs1oj
            references users
            on update cascade
);

alter table albums
    owner to postgres;


-- auto-generated definition
create table media
(
    id           bigint       not null
        constraint media_pkey
            primary key,
    media_type   integer      not null,
    persist_date timestamp    not null,
    url          varchar(255) not null,
    album_id     bigint
        constraint fkkqrdr00pw2v5k3q4tqv76cy6i
            references albums
            on update cascade,
    user_id      bigint       not null
        constraint fknd8hh0yn7qvv4pqyk8mg7l1ox
            references users
            on update cascade
);

alter table media
    owner to postgres;

-- auto-generated definition
create table audios
(
    album    varchar(255),
    author   varchar(255),
    icon     varchar(255),
    length   integer,
    name     varchar(255),
    media_id bigint not null
        constraint audios_pkey
            primary key
        constraint fkmbwry03s0jccvgqefrlv3ln1j
            references media
            on update cascade
);

alter table audios
    owner to postgres;

-- auto-generated definition
create table chats
(
    id           bigint    not null
        constraint chats_pkey
            primary key,
    title        varchar(255),
    image        varchar(255),
    persist_date timestamp not null
);

alter table chats
    owner to postgres;

-- auto-generated definition
create table group_category
(
    id       bigint not null
        constraint group_category_pkey
            primary key,
    category varchar(255)
);

alter table group_category
    owner to postgres;

-- auto-generated definition
create table groups
(
    id                  bigint    not null
        constraint groups_pkey
            primary key,
    address_image_group varchar(255),
    description         varchar(255),
    last_redaction_date timestamp not null,
    link_site           varchar(255),
    name                varchar(255),
    persist_date        timestamp not null,
    group_category_id   bigint
        constraint fkb9i5f3jw8rrurcge9anwl1o9t
            references group_category
            on update cascade,
    owner_id            bigint    not null
        constraint fkke9gpecgx7u1oef8lsd9tax3c
            references users
            on update cascade
);

alter table groups
    owner to postgres;

-- auto-generated definition
create table images
(
    description varchar(255),
    media_id    bigint not null
        constraint images_pkey
            primary key
        constraint fkd1n6j98c6ebkpm1qmlfjrg4jf
            references media
            on update cascade
);

alter table images
    owner to postgres;

-- auto-generated definition
create table language
(
    id   bigint not null
        constraint language_pkey
            primary key,
    name varchar(255)
);

alter table language
    owner to postgres;

-- auto-generated definition
create table videos
(
    author   varchar(255),
    icon     varchar(255),
    name     varchar(255),
    media_id bigint not null
        constraint videos_pkey
            primary key
        constraint fkap12qkmsguts1paap4tvqv57k
            references media
            on update cascade
);

alter table videos
    owner to postgres;

-- auto-generated definition
create table topics
(
    id    bigint        not null
        constraint topics_pkey
            primary key,
    topic varchar(1000) not null
);

alter table topics
    owner to postgres;

-- auto-generated definition
create table posts
(
    id                  bigint        not null
        constraint posts_pkey
            primary key,
    last_redaction_date timestamp     not null,
    persist_date        timestamp     not null,
    text                varchar(1000) not null,
    title               varchar(50)   not null,
    user_id             bigint        not null
        constraint fk5lidm6cqbc7u4xhqpxm898qme
            references users
            on update cascade,
    topic_id            bigint        not null
        constraint fkrfchr8dax0kfngvvkbteh5n7h
            references topics
            on update cascade
);

alter table posts
    owner to postgres;



-- auto-generated definition
create table album_audios
(
    album_id bigint not null
        constraint album_audios_pkey
            primary key
        constraint fkn8fkl5522npp79gun310v08gc
            references albums
            on update cascade
);

alter table album_audios
    owner to postgres;

-- auto-generated definition
create table album_has_audio
(
    album_id  bigint not null
        constraint fk8v9jpoytyn00yhy2wd4vvjg1y
            references album_audios
            on update cascade,
    audios_id bigint not null
        constraint fk4wutmsbgisseb2mxxe8nutnqe
            references audios
            on update cascade,
    constraint album_has_audio_pkey
        primary key (album_id, audios_id)
);

alter table album_has_audio
    owner to postgres;

-- auto-generated definition
create table album_image
(
    album_id bigint not null
        constraint album_image_pkey
            primary key
        constraint fkktle04uj7x3exwin0gfq0xdtd
            references albums
            on update cascade
);

alter table album_image
    owner to postgres;

-- auto-generated definition
create table album_has_image
(
    album_id bigint not null
        constraint fktq22d73bviic6fhl3sk3w7x4s
            references album_image
            on update cascade,
    image_id bigint not null
        constraint fkcdy622l6cw7ls5chlfa0admtk
            references images
            on update cascade,
    constraint album_has_image_pkey
        primary key (album_id, image_id)
);

alter table album_has_image
    owner to postgres;

-- auto-generated definition
create table album_video
(
    album_id bigint not null
        constraint album_video_pkey
            primary key
        constraint fkn8fkl5522npp79gun310v08gc
            references albums
            on update cascade
);

alter table album_video
    owner to postgres;

-- auto-generated definition
create table album_has_video
(
    album_id  bigint not null
        constraint fkk4bl9yx13be7tq4r8d069m6hu
            references album_video
            on update cascade,
    videos_id bigint not null
        constraint uk_a5rhnqpxtg6ghuopt3857jsa1
            unique
        constraint fkh3snslp51d756ghktmpxn7vo4
            references videos
            on update cascade,
    constraint album_has_video_pkey
        primary key (album_id, videos_id)
);

alter table album_has_video
    owner to postgres;

-- auto-generated definition
create table bookmarks
(
    id           bigint    not null
        constraint bookmarks_pkey
            primary key,
    persist_date timestamp not null,
    post_id      bigint    not null
        constraint fk7nbb4ldgek7ux7y6lu0y4g826
            references posts
            on update cascade,
    user_id      bigint    not null
        constraint fkdbsho2e05w5r13fkjqfjmge5f
            references users
            on update cascade
);

alter table bookmarks
    owner to postgres;

-- auto-generated definition
create table messages
(
    id                  bigint    not null
        constraint messages_pkey
            primary key,
    is_unread           integer   not null,
    persist_date        timestamp,
    message             varchar(255),
    last_redaction_date timestamp not null,
    user_id             bigint    not null
        constraint fkpsmh6clh3csorw43eaodlqvkn
            references users
            on update cascade
);

alter table messages
    owner to postgres;

-- auto-generated definition
create table chats_messages
(
    chat_id    bigint not null
        constraint fknwmy2hk0iobmwapmaikccu15i
            references chats
            on update cascade,
    message_id bigint not null
        constraint uk_qov15g85hkl2n52yptlivf7c8
            unique
        constraint fk824s2valk1yftney5dl4kufm2
            references messages
            on update cascade,
    constraint chats_messages_pkey
        primary key (chat_id, message_id)
);

alter table chats_messages
    owner to postgres;


-- auto-generated definition
create table "like"
(
    id        bigint  not null
        constraint like_pkey
            primary key,
    like_type integer not null,
    user_id   bigint  not null
        constraint fkn6yllf2sb7ttvbrwya4lxkx60
            references users
            on update cascade
);

alter table "like"
    owner to postgres;

-- auto-generated definition
create table comments
(
    id                  bigint    not null
        constraint comments_pkey
            primary key,
    comment             varchar(255),
    comment_type        integer   not null,
    persist_date        timestamp,
    last_redaction_date timestamp not null,
    user_id             bigint    not null
        constraint fk8omq0tc18jd43bu5tjh6jvraq
            references users
            on update cascade
);

alter table comments
    owner to postgres;

-- auto-generated definition
create table comment_like
(
    like_id    bigint not null
        constraint comment_like_pkey
            primary key
        constraint fkkldgc9u7sbqv0djmeeusvisue
            references "like"
            on update cascade,
    comment_id bigint not null
        constraint fkl1mu2ryf4k4ps93b5b52q2m8i
            references comments
            on update cascade
);

alter table comment_like
    owner to postgres;

-- auto-generated definition
create table followers
(
    id          bigint not null
        constraint followers_pkey
            primary key,
    follower_id bigint not null
        constraint fkgl2oa2hkhtgh7s8vf7dnpm0vt
            references users
            on update cascade,
    user_id     bigint not null
        constraint fkbpy0ybtaj5k196w6hbon3krt8
            references users
            on update cascade
);

alter table followers
    owner to postgres;

-- auto-generated definition
create table friends
(
    id        bigint not null
        constraint friends_pkey
            primary key,
    friend_id bigint not null
        constraint fkglgwqjy5yrqnkoupjt738o12c
            references users
            on update cascade,
    user_id   bigint not null
        constraint fknnacc56g8l3e7hk26qse3l1g7
            references users
            on update cascade
);

alter table friends
    owner to postgres;

-- auto-generated definition
create table group_chats
(
    chat_id bigint not null
        constraint group_chats_pkey
            primary key
        constraint fkn8fkl5522npp79gun310v08gc
            references chats
            on update cascade
);

alter table group_chats
    owner to postgres;

-- auto-generated definition
create table group_has_user
(
    id           bigint    not null
        constraint group_has_user_pkey
            primary key,
    persist_date timestamp not null,
    group_id     bigint    not null
        constraint fkhcr1kxm4l7myig424250g8i63
            references groups
            on update cascade,
    user_id      bigint    not null
        constraint fko5foqw5fct5ji0601eeg3tm3q
            references users
            on update cascade
);

alter table group_has_user
    owner to postgres;

-- auto-generated definition
create table group_wall
(
    group_id bigint not null
        constraint fkhde1qpuipv4ouvqw02gxruc2h
            references groups
            on update cascade,
    post_id  bigint not null
        constraint uk_k0mxoo8evldcjcq1iva6o7bvq
            unique
        constraint fkqkll3lqcxuse5558nxawjgmp4
            references posts
            on update cascade,
    constraint group_wall_pkey
        primary key (group_id, post_id)
);

alter table group_wall
    owner to postgres;

-- auto-generated definition
create table media_comment
(
    comment_id bigint not null
        constraint media_comment_pkey
            primary key
        constraint fkqddyww0crb9h5943ecanke9p2
            references comments
            on update cascade,
    media_id   bigint not null
        constraint fkc4g13ic2kajrl02anuikf5bao
            references media
            on update cascade
);

alter table media_comment
    owner to postgres;

-- auto-generated definition
create table media_like
(
    like_id  bigint not null
        constraint media_like_pkey
            primary key
        constraint fk2p0b7gpbngslllwcnlovjrhwu
            references "like"
            on update cascade,
    media_id bigint not null
        constraint fkdq31g0obh9yexo3jy1lx4jvs6
            references media
            on update cascade
);

alter table media_like
    owner to postgres;

-- auto-generated definition
create table media_messages
(
    message_id bigint not null
        constraint fk638dd51v9jqsoqk64yc0bvyts
            references messages
            on update cascade,
    media_id   bigint not null
        constraint uk_ol390bknn4lfrscx6hs5rxgxh
            unique
        constraint fk9711cqt4f4c9r8myvpxxaxdj5
            references media
            on update cascade,
    constraint media_messages_pkey
        primary key (message_id, media_id)
);

alter table media_messages
    owner to postgres;

-- auto-generated definition
create table message_like
(
    like_id    bigint not null
        constraint message_like_pkey
            primary key
        constraint fko77h7ssfcsg4u2d3p4xyxj6w2
            references "like"
            on update cascade,
    message_id bigint not null
        constraint fk6us66cd4998bp9nbyqyg9jp4e
            references messages
            on update cascade
);

alter table message_like
    owner to postgres;

-- auto-generated definition
create table playlists
(
    playlist_id  bigint    not null
        constraint playlists_pkey
            primary key,
    image        varchar(255),
    name         varchar(255),
    persist_date timestamp not null,
    user_id      bigint    not null
        constraint fktgjwvfg23v990xk7k0idmqbrj
            references users
            on update cascade
);

alter table playlists
    owner to postgres;

-- auto-generated definition
create table playlist_has_audios
(
    playlist_id bigint not null
        constraint fkf94m5c5nwywjn4brktc6f5k2s
            references playlists
            on update cascade,
    audios_id   bigint not null
        constraint fkifxgpubnspdey7x7876eb5j2o
            references audios
            on update cascade,
    constraint playlist_has_audios_pkey
        primary key (playlist_id, audios_id)
);

alter table playlist_has_audios
    owner to postgres;

-- auto-generated definition
create table post_comment
(
    comment_id bigint not null
        constraint post_comment_pkey
            primary key
        constraint fkb5nt73rr3r7qun4cihgwpyh9s
            references comments
            on update cascade,
    post_id    bigint not null
        constraint fkl6wxpohlclbjykgpkjdqphmfg
            references posts
            on update cascade
);

alter table post_comment
    owner to postgres;

-- auto-generated definition
create table post_like
(
    like_id bigint not null
        constraint post_like_pkey
            primary key
        constraint fk41sp7f5q4lce3olirjrdwkn8g
            references "like"
            on update cascade,
    post_id bigint
        constraint fkcf8kqsucxsmplv3xw9gubrql0
            references posts
            on update cascade
);

alter table post_like
    owner to postgres;

-- auto-generated definition
create table post_media
(
    post_id  bigint not null
        constraint fk1urcum9dtf0vgul7k405f4r2d
            references posts
            on update cascade,
    media_id bigint not null
        constraint uk_cbh3kwx9ocobb3y3jn93nth0o
            unique
        constraint fkedvegnxhyt4dke852jfbbq1wp
            references media
            on update cascade,
    constraint post_media_pkey
        primary key (post_id, media_id)
);

alter table post_media
    owner to postgres;

-- auto-generated definition
create table tags
(
    id   bigint not null
        constraint tags_pkey
            primary key,
    text varchar(255)
);

alter table tags
    owner to postgres;

-- auto-generated definition
create table post_tags
(
    post_id bigint not null
        constraint fkkifam22p4s1nm3bkmp1igcn5w
            references posts
            on update cascade,
    tags_id bigint not null
        constraint fkc9w0wcpokniw86jioo5rrb37s
            references tags
            on update cascade,
    constraint post_tags_pkey
        primary key (post_id, tags_id)
);

alter table post_tags
    owner to postgres;


-- auto-generated definition
create table reposts
(
    id      bigserial not null
        constraint reposts_pkey
            primary key,
    post_id bigint    not null
        constraint fkersix1dfhot0h5xp4umnh2mgr
            references posts
            on update cascade,
    user_id bigint    not null
        constraint fkdtkd7swbi33b8w536k29yjbom
            references users
            on update cascade
);

alter table reposts
    owner to postgres;

-- auto-generated definition
create table single_chat
(
    chat_id              bigint not null
        constraint single_chat_pkey
            primary key
        constraint fkn8fkl5522npp79gun310v08gc
            references chats
            on update cascade,
    user_one_id          bigint
        constraint fk38uct6yu5d7ny6uxeuk3afmte
            references users
            on update cascade,
    user_two_id          bigint
        constraint fklixke8t3kyjbirn6xxfs58s35
            references users
            on update cascade,
    deleted_for_user_one boolean,
    deleted_for_user_two boolean
);

alter table single_chat
    owner to postgres;

-- auto-generated definition
create table user_has_album
(
    id           bigint    not null
        constraint user_has_album_pkey
            primary key,
    persist_date timestamp not null,
    album_id     bigint    not null
        constraint fkr45vx07d7vjayb6xr2x4ekirf
            references albums
            on update cascade,
    user_id      bigint    not null
        constraint fk1biix5wmnyeps5p7ddvad6rwc
            references users
            on update cascade
);

alter table user_has_album
    owner to postgres;

-- auto-generated definition
create table user_languages
(
    user_id     bigint not null
        constraint fkt3sjkb7b30p03i378qdcr2s9k
            references users
            on update cascade,
    language_id bigint not null
        constraint fk85eswkit45d5iy2oeuajp6ms5
            references language
            on update cascade,
    constraint user_languages_pkey
        primary key (user_id, language_id)
);

alter table user_languages
    owner to postgres;

-- auto-generated definition
create table user_tabs
(
    id           bigint    not null
        constraint user_tabs_pkey
            primary key,
    persist_date timestamp not null,
    post_id      bigint    not null
        constraint fkmaajj3ua3djfl3tj52db22ypm
            references posts
            on update cascade,
    user_id      bigint    not null
        constraint fkgo6h5c1wskrntjm2rkcrgmvih
            references users
            on update cascade
);

alter table user_tabs
    owner to postgres;

-- auto-generated definition
create table users_audios_collections
(
    user_id  bigint not null
        constraint fk94u8ir5f3j7wfwbhyddx826jj
            references users
            on update cascade,
    audio_id bigint not null
        constraint fk53yqrthjucjf6vqvm26s2il4k
            references audios
            on update cascade,
    constraint users_audios_collections_pkey
        primary key (user_id, audio_id)
);

alter table users_audios_collections
    owner to postgres;

-- auto-generated definition
create table users_group_chats
(
    user_id bigint not null
        constraint fknkf7ei1oneo72n53x8rw6ht23
            references users
            on update cascade,
    group_chat_id bigint not null
        constraint fk6q5yt8kersl8ejr63nfi53314
            references group_chats
            on update cascade,
    constraint users_group_chats_pkey
        primary key (user_id, group_chat_id)
);

alter table users_group_chats
    owner to postgres;

-- auto-generated definition
create table users_videos_collections
(
    user_id  bigint not null
        constraint fk1ijcokiwo0s5wsl2jidbv071v
            references users
            on update cascade,
    video_id bigint not null
        constraint fkjic2imsrvl3jumk8fcbcafydo
            references videos
            on update cascade,
    constraint users_videos_collections_pkey
        primary key (user_id, video_id)
);

alter table users_videos_collections
    owner to postgres;


