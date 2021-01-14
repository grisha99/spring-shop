create table products (
    id              bigserial primary key,
    title           VARCHAR(255),
    price           int,
    created_at      timestamp default current_timestamp,
    modified_at     timestamp default current_timestamp
);
insert into products (title, price) values
('Milk', 40),
('Bread', 20),
('Meat', 87),
('Sugar', 23),
('Oil', 44),
('Chocolate', 31),
('Salt', 15),
('Potato', 19),
('Cucumber', 34),
('Chicken', 55),
('Pepsi', 42),
('Water', 14),
('Cheese', 50),
('Cake', 37),
('Butter', 50);