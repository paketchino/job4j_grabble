drop table if exists person;
drop table if exists company;

create table if not exists company 
(
	id integer not null, 
	name_company character varying,
	CONSTRAINT company_key primary key (id)
);

create table if not exists person 
(
	id integer not null,
	name_person character varying,
	company_id integer references company(id),
	CONSTRAINT person_key primary key (id)
);

insert into company(id, name_company) values (1, 'Apple'); 
insert into company(id, name_company) values (2, 'Samsung'); 
insert into company(id, name_company) values (3, 'EA'); 
insert into company(id, name_company) values (4, 'Ubisoft');
insert into company(id, name_company) values (5, 'Miscrosoft');

insert into person(id, name_person, company_id) values (1, 'Maksim', 1); 
insert into person(id, name_person, company_id) values (2, 'Roman', 1); 
insert into person(id, name_person, company_id) values (3, 'Alex', 1); 
insert into person(id, name_person, company_id) values (4, 'Sacha', 3); 
insert into person(id, name_person, company_id) values (5, 'Sveta', 4); 
insert into person(id, name_person, company_id) values (6, 'Marina', 5); 
insert into person(id, name_person, company_id) values (7, 'Varya', 5); 
insert into person(id, name_person, company_id) values (8, 'Natasha', 5); 
insert into person(id, name_person, company_id) values (9, 'Sandra', 5); 
insert into person(id, name_person, company_id) values (10, 'Stas', 2); 

select name_person as Имена_Людей, name_company as Названия_Компаний 
from person as p inner join company as c on p.company_id = c.id
where company_id != 5;

select c.name_company, count(p.name_person) as name_count from company as c 
join person as p on p.company_id = c.id
group by c.name_company
order by name_count desc
limit 1