drop table if exists base_user;

/*==============================================================*/
/* Table: base_user                                             */
/*==============================================================*/
create table base_user
(
   id                   int(32) not null comment '主键',
   username             varchar(50) comment '用户名',
   salt                 varchar(100) comment '盐值',
   password             varchar(100) comment '密码',
   name                 varchar(50) comment '姓名',
   birthday             varchar(50) comment '出生日期',
   address              varchar(200) comment '家庭地址',
   mobile_phone         varchar(20) comment '手机号',
   tel_phone            varchar(20) comment '联系电话',
   email                char(10) comment '联系邮箱',
   sex                  char(1) comment '性别',
   type                 char(1) comment '用户类型',
   status               char(1) comment '用户状态',
   description          varchar(200) comment '用户描述',
   crt_time             datetime comment '创建时间',
   crt_user             varchar(50) comment '创建人',
   crt_name             varchar(50) comment '创建人姓名',
   crt_host             varchar(20) comment '创建人ip',
   upd_time             datetime comment '修改时间',
   upd_user             varchar(50) comment '修改人',
   upd_name             varchar(50) comment '修改人姓名',
   upd_host             varchar(20) comment '修改人ip',
   attr1                varchar(100) comment '备注1',
   attr2                varchar(100) comment '备注2',
   attr3                varchar(100) comment '备注3',
   attr4                varchar(100) comment '备注4',
   attr5                varchar(100) comment '备注5',
   attr6                varchar(100) comment '备注6',
   attr7                varchar(100) comment '备注7',
   attr8                varchar(100) comment '备注8',
   attr9                varchar(100) comment '备注9',
   primary key (id)
);

alter table base_user comment '用户信息表';
