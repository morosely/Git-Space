create table category
(
    cid   int auto_increment,
    cname varchar(50) null,
    constraint category_cid_uindex
        unique (cid)
);

alter table category
    add primary key (cid);

INSERT INTO myi18n.category (cid, cname) VALUES (1, '${category.@8888}');
INSERT INTO myi18n.category (cid, cname) VALUES (2, '${category.@9999}');
INSERT INTO myi18n.category (cid, cname) VALUES (3, '${category.@7777}');