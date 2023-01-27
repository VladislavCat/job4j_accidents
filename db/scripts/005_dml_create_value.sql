insert into accident_types(type_name) values('Две машины'),('Машина и человек'),('Машина и окружение');
insert into rules(name) values('211 статья'),('111 статья'),('342 статья');
insert into accidents(name, description, address, type_id) values
('Авария','Две машины столкнулись на перекрестке','ул. Салтыковская д.11', 1),
('Сбитие пешехода','Человека сбили на пешеходном переходе','Сернинский проспект д.45',2),
('Пьяное вождение','Нетрезвый водитель вьехал в столб','Ильтинский бульв д.2',3);
insert into rules_accidents(accidents_id, rules_id) values(1, 2),(1, 3),(2, 1),(3, 3),(3,1);