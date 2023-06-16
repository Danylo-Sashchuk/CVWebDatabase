create type section_type as enum ('PERSONAL', 'POSITION', 'ACHIEVEMENTS', 'QUALIFICATIONS', 'EXPERIENCE', 'EDUCATION');

alter type section_type owner to postgres;

create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        text not null,
    value       text not null,
    resume_uuid char(36)
        constraint contact_resume_uuid_fk
            references resume
            on delete cascade
);

alter table contact
    owner to postgres;

create unique index contact_resume_uuid_type_uindex
    on contact (resume_uuid, type);

create table text_section
(
    id          serial
        constraint text_section_pk
            primary key,
    text        text         not null,
    resume_uuid char(36)     not null
        constraint text_section_resume_uuid_fk
            references resume
            on delete cascade,
    type        section_type not null
);

alter table text_section
    owner to postgres;

create table list_section
(
    id          serial
        constraint list_section_pk
            primary key,
    resume_uuid char(36)     not null
        constraint list_section_resume_uuid_fk
            references resume
            on delete cascade,
    type        section_type not null
);

alter table list_section
    owner to postgres;

create index list_section_resume_uuid_index
    on list_section (resume_uuid, type);

create table list_content
(
    id              serial
        constraint list_content_pk
            primary key,
    text            text    not null,
    list_section_id integer not null
        constraint list_content_list_section_id_fk
            references list_section
            on delete cascade
);

alter table list_content
    owner to postgres;

