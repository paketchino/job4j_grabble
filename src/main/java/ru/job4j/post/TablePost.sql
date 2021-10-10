drop table if exists post;
create table if not exists post (
	id serial not null,
	name text,
	text_post text
	link text unique,
	created timestamp,
	Constraint post_pkey primary key(id)
)