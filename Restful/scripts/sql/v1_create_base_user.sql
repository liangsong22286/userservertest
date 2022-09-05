drop table if exists base_user;

/*==============================================================*/
/* Table: base_user                                             */
/*==============================================================*/
create table base_user
(
   id                   int(32) not null comment '����',
   username             varchar(50) comment '�û���',
   salt                 varchar(100) comment '��ֵ',
   password             varchar(100) comment '����',
   name                 varchar(50) comment '����',
   birthday             varchar(50) comment '��������',
   address              varchar(200) comment '��ͥ��ַ',
   mobile_phone         varchar(20) comment '�ֻ���',
   tel_phone            varchar(20) comment '��ϵ�绰',
   email                char(10) comment '��ϵ����',
   sex                  char(1) comment '�Ա�',
   type                 char(1) comment '�û�����',
   status               char(1) comment '�û�״̬',
   description          varchar(200) comment '�û�����',
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

alter table base_user comment '�û���Ϣ��';
