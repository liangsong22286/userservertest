drop table if exists user_mail;

/*==============================================================*/
/* Table: user_mail                                             */
/*==============================================================*/
create table user_mail
(
   id                   int(32) not null comment '����',
   user_id              int(32) comment '�û�id',
   content              varchar(1000) comment '�ʼ�����',
   state                char(1) comment '����״̬',
   crt_time             datetime comment '����ʱ��',
   crt_user             varchar(50) comment '������',
   crt_name             varchar(50) comment '����������',
   crt_host             varchar(20) comment '������ip',
   upd_time             datetime comment '�޸�ʱ��',
   upd_user             varchar(50) comment '�޸���',
   upd_name             varchar(50) comment '�޸�������',
   upd_host             varchar(20) comment '�޸���ip',
   attr1                varchar(100) comment '��ע1',
   attr2                varchar(100) comment '��ע2',
   attr3                varchar(100) comment '��ע3',
   attr4                varchar(100) comment '��ע4',
   attr5                varchar(100) comment '��ע5',
   attr6                varchar(100) comment '��ע6',
   attr7                varchar(100) comment '��ע7',
   attr8                varchar(100) comment '��ע8',
   attr9                varchar(100) comment '��ע9',
   primary key (id)
);

alter table user_mail comment '�û��ʼ���Ϣ��';
