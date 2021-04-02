create sequence genres_seq start 1 increment 1;
create sequence genres_music_seq start 1 increment 1;
create table genres
(
    id bigint not null  primary key,
    title varchar(255)
);
create table music_genres
(
    id bigint not null  primary key,
    genre_id bigint not null ,
    audio_id bigint not null ,
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE,
    CONSTRAINT fk_genre_audio FOREIGN KEY (audio_id) REFERENCES audios (media_id)  ON DELETE CASCADE
);