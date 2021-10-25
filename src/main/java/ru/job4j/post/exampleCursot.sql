create table t (id integer, s text);

insert into t values (1, 'first'), (2, 'second'), (3, 'third');

do
declare
cur cursor(id integer) for select * from t where t.id = cur.id;
begin
open cur(1);
end;