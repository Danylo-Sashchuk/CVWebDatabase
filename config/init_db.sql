CREATE TYPE public.section_type AS ENUM ('PERSONAL', 'POSITION', 'ACHIEVEMENTS', 'QUALIFICATIONS', 'EDUCATION', 'EXPERIENCE');

ALTER TYPE public.section_type OWNER TO postgres;

CREATE TYPE public.contact_type AS ENUM ('PHONE_NUMBER', 'EMAIL', 'SKYPE', 'LINKEDIN', 'GITHUB');

ALTER TYPE public.contact_type OWNER TO postgres;

CREATE TABLE public.resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT     NOT NULL
);

ALTER TABLE public.resume
    OWNER TO postgres;

CREATE TABLE public.contact
(
    id          SERIAL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    type        contact_type NOT NULL,
    value       TEXT         NOT NULL,
    resume_uuid CHAR(36)
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES public.resume
            ON DELETE CASCADE
);

ALTER TABLE public.contact
    OWNER TO postgres;

CREATE UNIQUE INDEX contact_resume_uuid_type_uindex
    ON public.contact (resume_uuid, type);

CREATE TABLE public.section
(
    id           SERIAL
        CONSTRAINT text_section_pk
            PRIMARY KEY,
    text         TEXT         NOT NULL,
    resume_uuid  CHAR(36)     NOT NULL
        CONSTRAINT text_section_resume_uuid_fk
            REFERENCES public.resume
            ON DELETE CASCADE,
    section_type section_type NOT NULL
);

ALTER TABLE public.section
    OWNER TO postgres;

