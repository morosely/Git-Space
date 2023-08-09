create table products
(
    pid         int auto_increment,
    pname       varchar(50) null,
    price       double      null,
    flag        varchar(2)  null,
    category_id int         null,
    constraint products_pid_uindex
        unique (pid)
);

create index products_fk
    on products (category_id);

alter table products
    add primary key (pid);

INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (1, '${products.#1111}', 5000, '1', 1);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (2, '${products.#2222}', 3000, '1', 1);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (3, '${products.#3333}', 7000, '1', 1);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (4, '${products.#4444}', 800, '1', 2);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (5, '${products.#5555}', 200, '1', 2);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (6, '${products.#6666}', 450, '1', 2);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (7, '${products.#7777}', 700, '1', 2);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (8, '${products.#8888}', 2680, '1', 3);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (9, '${products.#9999}', 590, '1', 3);
INSERT INTO myi18n.products (pid, pname, price, flag, category_id) VALUES (10, '锅碗瓢盆', 590, '1', 3);