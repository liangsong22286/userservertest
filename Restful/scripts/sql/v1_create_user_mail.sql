drop table if exists user_mail;

/*==============================================================*/
/* Table: user_mail                                             */
/*==============================================================*/
create table user_mail
(
   id                   int(32) not null comment '主键',
   user_id              int(32) comment '用户id',
   content              varchar(1000) comment '邮件内容',
   state                char(1) comment '发送状态',
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

alter table user_mail comment '用户邮件信息表';
