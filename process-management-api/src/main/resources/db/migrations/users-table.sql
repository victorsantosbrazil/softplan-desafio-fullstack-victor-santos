CREATE TABLE public.users (
	id uuid NOT NULL,
	email varchar(255) NOT NULL,
	name varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
	"role" varchar(255) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);