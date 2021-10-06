drop table if exists post;
create table if not exists post (
	id integer not null,
	name varchar(50),
	text_post text,
	link text unique,
	created timestamp,
	Constraint post_pkey primary key(id)
)