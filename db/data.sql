CREATE TABLE public.user_data (
    id bigint NOT NULL,
    enabled boolean,
    password character varying(256) NOT NULL,
    role character varying(50),
    username character varying(50) NOT NULL
);
ALTER TABLE public.user_data OWNER TO postgres;
CREATE SEQUENCE public.user_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.user_id_sequence OWNER TO postgres;
insert into user_data(id, username, password , enabled, role)
    values(nextval('user_id_sequence'), 'admin@admin.com','$2a$10$HTeZve8CUiPtDGlFbeGyfeEQB/61NNLnCdiF4gQtDim0hHYiIXasi', '1', 'ROLE_ADMIN');