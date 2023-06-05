CREATE TABLE resume
(
    uuid      CHAR(36) NOT NULL
        CONSTRAINT resume_pk
            PRIMARY KEY,
    full_name TEXT     NOT NULL
);

ALTER TABLE resume
    OWNER TO postgres;

CREATE TABLE public.contact
(
    id          SERIAL
        CONSTRAINT contact_pk
            PRIMARY KEY,
    type        TEXT NOT NULL,
    value       TEXT NOT NULL,
    resume_uuid CHAR(36)
        CONSTRAINT contact_resume_uuid_fk
            REFERENCES public.resume
            ON DELETE CASCADE
);

ALTER TABLE public.contact
    OWNER TO postgres;

CREATE UNIQUE INDEX contact_resume_uuid_type_uindex
    ON public.contact (resume_uuid, type);

ALTER TABLE contact
    OWNER TO postgres;
