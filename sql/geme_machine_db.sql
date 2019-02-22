# 数据库 
#创建数据库
DROP DATABASE IF EXISTS game_machine_db;
CREATE DATABASE game_machine_db;

#使用数据库 
use game_machine_db;


#创建角色表
CREATE TABLE role_tb(
role_id bigint(20) NOT NULL  COMMENT '角色id',
name varchar(255) COMMENT '角色名',
duty varchar(255) COMMENT '角色职责',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (role_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色表';

#创建权限表
CREATE TABLE permission_tb(
permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
type tinyint(4) COMMENT '权限类型，默认0开放，1鉴权',
manager_name varchar(255) COMMENT '权限管理名',
name varchar(255) COMMENT '权限名',
route varchar(255) unique COMMENT '权限路由',
update_date datetime COMMENT '更新时间',
PRIMARY KEY (permission_id),
INDEX INDEX_TYPE (type) USING BTREE,
INDEX INDEX_MANAGERNAME (manager_name) USING BTREE,
INDEX INDEX_NAME (name) USING BTREE,
INDEX INDEX_ROUTE (route) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表';

#创建角色权限表
CREATE TABLE role_permission_tb(
role_permission_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
region tinyint(4) COMMENT '范围，1公共，2自身',
role_id int(11) COMMENT '角色id,外键',
update_date datetime COMMENT '更新时间',
permission_id bigint(20) COMMENT '权限id,外键',
PRIMARY KEY (role_permission_id),
UNIQUE KEY UNIQUE_ROLEID_PERMISSIONID (role_id,permission_id),
INDEX INDEX_REGION (region) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_PERMISSIONID (permission_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#创建账户表 
CREATE TABLE account_tb(
account_id bigint(20) NOT NULL  COMMENT '账户id',
phone varchar(255) COMMENT '注册手机号',
password varchar(255) COMMENT '密码',
nickname varchar(255) COMMENT '昵称',
number int(11) DEFAULT 0 COMMENT '剩余次数',
icon varchar(255) COMMENT '图像',
sex tinyint(4) DEFAULT 0 COMMENT '性别,默认为0未知，为1男性，为2女性',
country varchar(255) COMMENT '国家,默认中国',
realname varchar(255) COMMENT '真实姓名',
email varchar(255) COMMENT 'email',
invite_code varchar(255) COMMENT '邀请码',
address varchar(255) COMMENT '收货地址',
auth tinyint(4) COMMENT '认证，0没认证，1审核中，2已认证',
identity_cards_front_img varchar(255) COMMENT '身份证正面',
identity_cards_back_img varchar(255) COMMENT '身份证反面',
create_date datetime COMMENT '创建时间',
login_date datetime COMMENT '登陆时间',
status tinyint DEFAULT 0 COMMENT '状态，默认0正常，1封禁，2异常',
role_id bigint(20) COMMENT '角色id外键',
master_id bigint(20) COMMENT '上级id外键',
PRIMARY KEY (account_id),
INDEX INDEX_AUTH (auth) USING BTREE,
INDEX INDEX_PHONE (phone) USING BTREE,
INDEX INDEX_REALNAME (realname) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_LOGINDATE (login_date) USING BTREE,
INDEX INDEX_ROLEID (role_id) USING BTREE,
INDEX INDEX_INVITECODE (invite_code) USING BTREE,
INDEX INDEX_MASTERID (master_id) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='账户表';

#创建财务表
CREATE TABLE finance_tb(
finance_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务id',
money decimal(11,2) DEFAULT 0 COMMENT '余额',
recharge decimal(11,2) DEFAULT 0 COMMENT '充值金额',
consume decimal(11,2) DEFAULT 0 COMMENT '消费金额',
withdrawals decimal(11,2) DEFAULT 0 COMMENT '提现金额',
self_profit decimal(11,2) DEFAULT 0 COMMENT '自身总收益',
partner_profit decimal(11,2) DEFAULT 0 COMMENT '合伙人总收益',
base_profit decimal(11,2) DEFAULT 0 COMMENT '赠送金钱',
bank_user_name varchar(255) COMMENT '开户人',
bank_name varchar(255) COMMENT '开户银行',
bank_account varchar(255) COMMENT '银行账号',
bank_address varchar(255) COMMENT '开户银行地址',
update_date datetime COMMENT '更新时间',
acount_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (finance_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='财务表';

#创建卡片表
CREATE TABLE card_tb(
card_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '卡片id',
card_number1 int(11) COMMENT '面料卡张数',
card_number2 int(11) COMMENT '五金卡张数',
card_number3 int(11) COMMENT '手挽卡张数',
card_number4 int(11) COMMENT '袋身卡张数',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
mer_id bigint(20) COMMENT '商品id外键',
acount_id bigint(20) COMMENT '账户id外键',
PRIMARY KEY (card_id),
INDEX INDEX_MERID (mer_id) USING BTREE,
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='卡片表';

#创建商品表
CREATE TABLE mer_tb(
mer_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
name varchar(255) COMMENT '名称',
img_address varchar(255) COMMENT '封面',
status tinyint(4) DEFAULT 1 COMMENT '状态,默认1上架,2下架',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
PRIMARY KEY (mer_id),
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='商品表';

#创建收货信息表
CREATE TABLE receipt_info_tb(
receipt_info_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收货信息id',
name varchar(255) COMMENT '姓名',
phone varchar(255) COMMENT '手机号',
address varchar(255) COMMENT '收货地址',
is_default tinyint(4) DEFAULT 0 COMMENT '默认为0不是，1是',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
acount_id bigint(20) COMMENT '账户id,外键',
PRIMARY KEY (receipt_info_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_ISDEFAULT (is_default) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='收货地址表 ';

#创建充值项表
CREATE TABLE recharge_term_tb(
recharge_term_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值项id',
title varchar(255) COMMENT '附加标题',
recharge_money decimal(11,2) COMMENT '充值真钱',
give_number int(11) COMMENT '赠送次数',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
status tinyint(4) DEFAULT 0 COMMENT '0下架，默认为1上架',
PRIMARY KEY (recharge_term_id),
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='充值项表 ';

#创建充值记录表
CREATE TABLE recharge_record_tb(
recharge_record_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值记录id',
type tinyint(4) COMMENT '充值类型，1支付宝支付，2微信支付，3银联支付',
give_number int(11) DEFAULT 0 COMMENT '充值真钱',
give_money decimal(11,2) DEFAULT 0 COMMENT '赠送次数',
create_date datetime   COMMENT '创建时间',
update_date datetime   COMMENT '更新时间',
status tinyint(4) DEFAULT 0 COMMENT '默认为1成功，2失败',
acount_id bigint(20) COMMENT '账户id,外键',
PRIMARY KEY (recharge_record_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='充值记录表 ';

#创建商品订单表
CREATE TABLE mer_order_tb(
mer_order_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品订单id',
order_number varchar(255) COMMENT '订单号',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
acount_id bigint(20) COMMENT '下单人',
PRIMARY KEY (mer_order_id),
INDEX INDEX_ACOUNTID (acount_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='商品订单表';

#创建商品订单详情表
CREATE TABLE mer_order_detail_tb(
mer_order_detail_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品订单详情id',
mer_name varchar(255) COMMENT '商品名称',
img_address varchar(255) COMMENT '封面',
name varchar(255) COMMENT '收货地址姓名',
phone varchar(255) COMMENT '收货地址手机号',
address varchar(255) COMMENT '收货地址',
create_date datetime  COMMENT '创建时间',
update_date datetime  COMMENT '更新时间',
status tinyint(4) COMMENT '订单状态，1待发货，1已发货',
mer_order_id bigint(20) COMMENT '商品订单ID',
PRIMARY KEY (mer_order_detail_id),
INDEX INDEX_MERORDERID (mer_order_id) USING BTREE,
INDEX INDEX_CREATEDATE (create_date) USING BTREE,
INDEX INDEX_UPDATEDATE (update_date) USING BTREE,
INDEX INDEX_STATUS (status) USING BTREE
)ENGINE = InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单详情表';


#创建配置表
CREATE TABLE config_tb(
  config_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置id',
  platform_name varchar(255)  COMMENT '平台名称',
  service_phone varchar(255)  COMMENT '平台联系电话',
  service_qq varchar(255)  COMMENT '平台联系qq',
  free_number tinyint(4)  COMMENT '免费发布次数',
  perday_start_min_num int(11)  COMMENT '每天首次最少发布数量',
  perday_start_max_num int(11)  COMMENT '每天首次最大发布数量',
  per_seconds int(11)  COMMENT '分隔多少秒发布',
  per_seconds_min_num int(11)  COMMENT '分隔秒最少发布数量',
  per_seconds_max_num int(11)  COMMENT '分隔秒最大发布数量',
  create_date datetime COMMENT '创建时间',
  update_date datetime COMMENT '更新时间',
  PRIMARY KEY (config_id)
)ENGINE = InnoDB  DEFAULT CHARSET=utf8 COMMENT='配置表';


#设置初始角色
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1000,'超级管理员','超级管理员',now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date) 
VALUES (1001,'普通管理员','普通管理员',now());
INSERT IGNORE INTO role_tb (role_id,name,duty,update_date)
VALUES (1002,'用户','用户',now());

#初始化配置
INSERT IGNORE INTO config_tb (config_id,platform_name,free_number,perday_start_min_num,perday_start_max_num,per_seconds,per_seconds_min_num,per_seconds_max_num,create_date,update_date)
VALUES (1000,'欢乐抓包包',3,5,50,300,3,10,now(),now());

#设置初始管理员密码MD5加密123456
INSERT IGNORE INTO account_tb (account_id,nickname,phone,email,password,create_date,login_date,role_id) 
VALUES (1000,'聂跃','1000','1000@qq.com','11874bb6149dd45428da628c9766b252',now(),now(),1000);

INSERT IGNORE INTO integral_tb (integral_id,integral,recharge,consume,base_profit,create_date,update_date,account_id)
VALUES (1000,0,0,0,0,now(),now(),1000);