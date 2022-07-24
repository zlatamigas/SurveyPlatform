create table themes
(
    id_theme     int auto_increment
        primary key,
    theme_name   varchar(200)                                    not null,
    theme_status enum ('WAITING', 'CONFIRMED') default 'WAITING' not null,
    constraint theme_name_UNIQUE
        unique (theme_name)
);

create table users
(
    id_user           int auto_increment
        primary key,
    email             varchar(45)               not null,
    password          varchar(20)               not null,
    registration_date date                      null,
    user_role         enum ('ADMIN', 'USER')    null,
    user_status       enum ('ACTIVE', 'BANNED') null,
    constraint email_UNIQUE
        unique (email)
);

INSERT INTO survey_schema.users (email, password, user_role, user_status) VALUES ('admin@admin.com', '369e752512edfc29d000', 'ADMIN', 'ACTIVE');

create table surveys
(
    id_survey          int auto_increment
        primary key,
    survey_name        text                                      not null,
    survey_description text                                      null,
    survey_status      enum ('NOT_STARTED', 'STARTED', 'CLOSED') null,
    theme_id           int                                       null,
    creator_id         int                                       null,
    start_date_time    datetime                                  null,
    close_date_time    datetime                                  null,
    constraint creator_id_fk
        foreign key (creator_id) references users (id_user)
            on update cascade on delete cascade,
    constraint theme_id_fk
        foreign key (theme_id) references themes (id_theme)
            on update cascade on delete set null
);

create table questions
(
    id_question     int auto_increment
        primary key,
    formulation     text                 not null,
    select_multiple tinyint(1) default 0 not null,
    survey_id       int                  null,
    constraint question_quiz_id_fk
        foreign key (survey_id) references surveys (id_survey)
            on update cascade on delete cascade
);

create table question_answers
(
    id_question_answer int auto_increment
        primary key,
    answer             text          not null,
    selected_count     int default 0 not null,
    question_id        int           null,
    constraint question_id_fk
        foreign key (question_id) references questions (id_question)
            on update cascade on delete cascade
);

create index question_id_fk_idx
    on question_answers (question_id);

create index question_quiz_id_fk_idx
    on questions (survey_id);

create table survey_user_attempts
(
    id_survey_user_attempt int auto_increment
        primary key,
    finished_date_time     datetime not null,
    survey_id              int      null,
    user_id                int      null,
    constraint attempt_quiz_id_fk
        foreign key (survey_id) references surveys (id_survey)
            on update cascade on delete cascade,
    constraint attempt_user_id_fk
        foreign key (user_id) references users (id_user)
            on update cascade on delete set null
);

create index attempt_quiz_id_fk_idx
    on survey_user_attempts (survey_id);

create index attempt_user_id_fk_idx
    on survey_user_attempts (user_id);

create index creator_id_fk_idx
    on surveys (creator_id);

create index theme_id_fk_idx
    on surveys (theme_id);

create index role_id_fk_idx
    on users (user_role);

create index status_id_fk_idx
    on users (user_status);

