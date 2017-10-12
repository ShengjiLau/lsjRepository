create table permission
(
	permission_id int auto_increment
		primary key,
	permission_remark varchar(100) null,
	permission_name varchar(50) not null,
	permission_prefix varchar(80) null,
	permission_code varchar(100) not null,
	constraint permission_permission_name_uindex
		unique (permission_name),
	constraint permission_permission_code_uindex
		unique (permission_code)
)default charset = utf8mb4
;

create table role
(
	role_id bigint auto_increment
		primary key,
	role_name varchar(40) not null,
	role_comment varchar(60) null,
	role_company_id mediumtext not null,
	constraint role_role_id_uindex
		unique (role_id)
)default charset = utf8mb4
comment '角色表 公司内可见 有固定的几个角色'
;

create table role_permission
(
	role_permission_id bigint not null
		primary key,
	role_id bigint not null,
	permission_id int not null
)default charset = utf8mb4
comment '角色权限关系表'
;

create table role_user_relation
(
	relation_id bigint auto_increment
		primary key,
	role_id bigint not null,
	user_id bigint not null,
	company_id bigint not null
)default charset = utf8mb4
comment '企业用户角色关系表'
;

