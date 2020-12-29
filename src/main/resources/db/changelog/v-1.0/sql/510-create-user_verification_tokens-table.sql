create table user_verification_tokens (
    id int8 not null,
    value varchar(60) not null,
    expires_at timestamp not null,
    primary key (id)
);

next

alter table user_verification_tokens
    add constraint fk8joo3rt688ti3mgv4q9sjs75g foreign key (id)
    references users on delete cascade

next

alter table user_verification_tokens
    add constraint uk_8mf8d7twp3t4h9r3a5uj6mj15 unique (value)

next