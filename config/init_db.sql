CREATE TYPE section_type AS ENUM ('PERSONAL', 'POSITION', 'ACHIEVEMENTS', 'QUALIFICATIONS', 'EDUCATION', 'EXPERIENCE');

ALTER TYPE section_type OWNER TO postgres;

CREATE TYPE public.contact_type AS ENUM ('PHONE_NUMBER', 'EMAIL', 'SKYPE', 'LINKEDIN', 'GITHUB');

alter type public.contact_type owner to postgres;

create table public.resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table public.resume
    owner to postgres;

create table public.contact
(
    id          serial
        constraint contact_pk
            primary key,
    type        contact_type not null,
    value       text not null,
    resume_uuid char(36)
        constraint contact_resume_uuid_fk
            references public.resume
            on delete cascade
);

alter table public.contact
    owner to postgres;

create unique index contact_resume_uuid_type_uindex
    on public.contact (resume_uuid, type);

create table public.section
(
    id          serial
        constraint text_section_pk
            primary key,
    text        text         not null,
    resume_uuid char(36)     not null
        constraint text_section_resume_uuid_fk
            references public.resume
            on delete cascade,
    type        section_type not null
);

alter table public.section
    owner to postgres;

