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
)
;

create table role
(
	role_id int auto_increment
		primary key,
	role_name varchar(40) not null,
	role_comment varchar(60) null,
	role_company_id mediumtext not null,
	constraint role_role_id_uindex
		unique (role_id)
)
comment '角色表 公司内可见 有固定的几个角色'
;

create table role_permission
(
	role_permission_id bigint not null
		primary key,
	role_id int not null,
	permission_id int not null
)
comment '角色权限关系表'
;

