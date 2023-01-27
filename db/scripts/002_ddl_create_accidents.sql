create table if not exists accidents (
    id_accidents serial primary key,
    name VARCHAR,
    description TEXT,
    address VARCHAR,
    type_id int references accident_types(id_accident_types)
)