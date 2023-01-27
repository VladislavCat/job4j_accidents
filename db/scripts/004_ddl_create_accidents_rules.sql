create table if not exists rules_accidents (
    id serial primary key,
    accidents_id int references accidents(id_accidents),
    rules_id int references rules(id_rules)
);