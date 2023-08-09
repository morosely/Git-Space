create table t_i18n_allocate
(
    pid    int auto_increment,
    type   varchar(30)   not null comment '类型',
    module varchar(30)   null comment '模块',
    label  varchar(100)  not null comment '标签',
    langs  json          not null comment '国际化json',
    to_web int default 0 not null comment '0为后端，1为前端',
    constraint t_i18n_allocate_label_uindex
        unique (label),
    constraint t_i18n_allocate_pid_uindex
        unique (pid)
)
    comment '国际化配置表';

create index t_i18n_allocate_module_label_uindex
    on t_i18n_allocate (module, label);

alter table t_i18n_allocate
    add primary key (pid);

INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (1, 'Web', 'web', 'home', '{"CN": "家", "US": "home"}', 1);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (2, 'Web', 'web', 'hello', '{"CN": "你好", "US": "hello"}', 1);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (3, 'Java', 'category', '@9999', '{"CN": "旅行用品", "US": "travel accessories"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (4, 'Java', 'category', '@8888', '{"CN": "办公用品", "US": "office supplies"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (5, 'Java', 'category', '@7777', '{"CN": "生活用品", "US": "articles of daily use"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (6, 'Java', 'products', '#1111', '{"CN": "牙刷", "US": "water glass"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (7, 'Java', 'products', '#2222', '{"CN": "水杯", "US": "articles of daily use"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (8, 'Java', 'products', '#3333', '{"CN": "拖鞋", "US": "slipper"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (9, 'Java', 'products', '#4444', '{"CN": "笔记本", "US": "jotter"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (10, 'Java', 'products', '#5555', '{"CN": "笔", "US": "pen"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (11, 'Java', 'products', '#6666', '{"CN": "办公桌", "US": "desk"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (12, 'Java', 'products', '#7777', '{"CN": "显示器", "US": "display"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (13, 'Java', 'products', '#8888', '{"CN": "被子", "US": "quilt"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (14, 'Java', 'products', '#9999', '{"CN": "一次性浴巾", "US": "Disposable bath towel"}', 0);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (15, 'Web', '', 'hi', '{"CN": "嗨", "US": "hi"}', 1);
INSERT INTO myi18n.t_i18n_allocate (pid, type, module, label, langs, to_web) VALUES (16, 'Java', 'exception', '@1', '{"CN": "服务器异常", "US": "DServer Exception"}', 0);