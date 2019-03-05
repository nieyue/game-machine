/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : game_machine_db

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2019-03-05 15:00:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_tb`
-- ----------------------------
DROP TABLE IF EXISTS `account_tb`;
CREATE TABLE `account_tb` (
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `phone` varchar(255) DEFAULT NULL COMMENT '注册手机号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图像',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别,默认为0未知，为1男性，为2女性',
  `country` varchar(255) DEFAULT NULL COMMENT '国家,默认中国',
  `realname` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(255) DEFAULT NULL COMMENT 'email',
  `invite_code` varchar(255) DEFAULT NULL COMMENT '邀请码',
  `identity_cards` varchar(255) DEFAULT NULL COMMENT '身份证',
  `auth` tinyint(4) DEFAULT NULL COMMENT '认证，0没认证，1审核中，2已认证',
  `identity_cards_front_img` varchar(255) DEFAULT NULL COMMENT '身份证正面',
  `identity_cards_back_img` varchar(255) DEFAULT NULL COMMENT '身份证反面',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `login_date` datetime DEFAULT NULL COMMENT '登陆时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态，默认0正常，1封禁，2异常',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id外键',
  `master_id` bigint(20) DEFAULT NULL COMMENT '上级id外键',
  PRIMARY KEY (`account_id`),
  KEY `INDEX_AUTH` (`auth`) USING BTREE,
  KEY `INDEX_PHONE` (`phone`) USING BTREE,
  KEY `INDEX_REALNAME` (`realname`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_LOGINDATE` (`login_date`) USING BTREE,
  KEY `INDEX_ROLEID` (`role_id`) USING BTREE,
  KEY `INDEX_INVITECODE` (`invite_code`) USING BTREE,
  KEY `INDEX_MASTERID` (`master_id`) USING BTREE,
  KEY `INDEX_STATUS` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Records of account_tb
-- ----------------------------
INSERT INTO `account_tb` VALUES ('1000', '1000', '11874bb6149dd45428da628c9766b252', '??', null, '0', null, 'sdfsd', '1000@qq.com', '1001', null, null, null, null, '2019-02-25 14:06:13', '2019-03-04 22:56:08', '0', '1000', null);
INSERT INTO `account_tb` VALUES ('1099957358295506945', '15111336588', '11874bb6149dd45428da628c9766b252', null, null, '0', null, 'dsfds', null, '6dmOBfcP', null, null, null, null, '2019-02-25 17:00:45', '2019-02-25 17:00:45', '0', '1002', null);
INSERT INTO `account_tb` VALUES ('1100211858902032386', '15111336586', '11874bb6149dd45428da628c9766b252', null, null, '0', null, null, null, null, null, '0', null, null, '2019-02-26 09:52:03', '2019-03-04 22:56:20', '0', '1002', null);
INSERT INTO `account_tb` VALUES ('1102512950389788674', '18684648787', '93d12654a890c4831540498e4cbef2f9', null, null, '0', null, null, null, null, null, '0', null, null, '2019-03-04 02:15:46', '2019-03-04 02:15:46', '0', '1002', null);

-- ----------------------------
-- Table structure for `card_tb`
-- ----------------------------
DROP TABLE IF EXISTS `card_tb`;
CREATE TABLE `card_tb` (
  `card_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '卡片id',
  `card_number1` int(11) DEFAULT NULL COMMENT '袋身卡张数',
  `card_number2` int(11) DEFAULT NULL COMMENT '面料卡张数',
  `card_number3` int(11) DEFAULT NULL COMMENT '手挽卡张数',
  `card_number4` int(11) DEFAULT NULL COMMENT '五金卡张数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `mer_id` bigint(20) DEFAULT NULL COMMENT '商品id外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id外键',
  PRIMARY KEY (`card_id`),
  KEY `INDEX_MERID` (`mer_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_UPDATEDATE` (`update_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1102633029471358978 DEFAULT CHARSET=utf8 COMMENT='卡片表';

-- ----------------------------
-- Records of card_tb
-- ----------------------------
INSERT INTO `card_tb` VALUES ('1101374266563563522', '0', '1', '1', '1', '2019-03-01 14:51:02', '2019-03-01 20:20:34', '1100578748119875585', '1100211858902032386');
INSERT INTO `card_tb` VALUES ('1101378108462141441', '1', '0', '0', '1', '2019-03-01 15:06:18', '2019-03-01 20:19:56', '1099972517915254785', '1100211858902032386');
INSERT INTO `card_tb` VALUES ('1101698725597728769', '0', '0', '0', '1', '2019-03-01 20:20:19', '2019-03-01 20:20:19', '1100578788909481986', '1100211858902032386');
INSERT INTO `card_tb` VALUES ('1101698852114714626', '0', '1', '0', '0', '2019-03-01 20:20:50', '2019-03-01 20:20:50', '1100578654272323585', '1100211858902032386');
INSERT INTO `card_tb` VALUES ('1102513119810310145', '1', '0', '0', '0', '2019-03-04 02:16:26', '2019-03-04 02:16:26', '1100577889931722754', '1102512950389788674');
INSERT INTO `card_tb` VALUES ('1102633029471358977', '0', '0', '1', '0', '2019-03-04 10:12:55', '2019-03-04 10:12:55', '1100577889931722754', '1100211858902032386');

-- ----------------------------
-- Table structure for `config_tb`
-- ----------------------------
DROP TABLE IF EXISTS `config_tb`;
CREATE TABLE `config_tb` (
  `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置id',
  `platform_name` varchar(255) DEFAULT NULL COMMENT '平台名称',
  `service_phone` varchar(255) DEFAULT NULL COMMENT '平台联系电话',
  `service_qq` varchar(255) DEFAULT NULL COMMENT '平台联系qq',
  `free_number` tinyint(4) DEFAULT NULL COMMENT '免费次数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Records of config_tb
-- ----------------------------
INSERT INTO `config_tb` VALUES ('1000', '欢乐抓包包', '15111336587', '278076304', '3', '2019-02-25 14:06:13', '2019-02-25 14:54:10');

-- ----------------------------
-- Table structure for `finance_tb`
-- ----------------------------
DROP TABLE IF EXISTS `finance_tb`;
CREATE TABLE `finance_tb` (
  `finance_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '财务id',
  `password` varchar(255) DEFAULT NULL COMMENT '交易密码',
  `money` decimal(11,2) DEFAULT '0.00' COMMENT '余额',
  `recharge` decimal(11,2) DEFAULT '0.00' COMMENT '充值金额',
  `consume` decimal(11,2) DEFAULT '0.00' COMMENT '消费金额',
  `withdrawals` decimal(11,2) DEFAULT '0.00' COMMENT '提现金额',
  `self_profit` decimal(11,2) DEFAULT '0.00' COMMENT '自身总收益',
  `partner_profit` decimal(11,2) DEFAULT '0.00' COMMENT '合伙人总收益',
  `base_profit` decimal(11,2) DEFAULT '0.00' COMMENT '赠送金钱',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id外键',
  PRIMARY KEY (`finance_id`),
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1102512950620475395 DEFAULT CHARSET=utf8 COMMENT='财务表';

-- ----------------------------
-- Records of finance_tb
-- ----------------------------
INSERT INTO `finance_tb` VALUES ('1', '11874bb6149dd45428da628c9766b252', '2100.00', '2100.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2019-02-25 15:37:34', '1000');
INSERT INTO `finance_tb` VALUES ('1099957358345838594', null, '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2019-02-25 17:00:45', '1099957358295506945');
INSERT INTO `finance_tb` VALUES ('1100211858990112770', null, '0.00', '10.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2019-03-04 17:28:52', '1100211858902032386');
INSERT INTO `finance_tb` VALUES ('1102512950620475394', null, '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '0.00', '2019-03-04 02:15:46', '1102512950389788674');

-- ----------------------------
-- Table structure for `mer_order_detail_tb`
-- ----------------------------
DROP TABLE IF EXISTS `mer_order_detail_tb`;
CREATE TABLE `mer_order_detail_tb` (
  `mer_order_detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品订单详情id',
  `mer_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `img_address` varchar(255) DEFAULT NULL COMMENT '封面',
  `name` varchar(255) DEFAULT NULL COMMENT '收货地址姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '收货地址手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `mer_order_id` bigint(20) DEFAULT NULL COMMENT '商品订单ID',
  PRIMARY KEY (`mer_order_detail_id`),
  KEY `INDEX_MERORDERID` (`mer_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1101387087166717954 DEFAULT CHARSET=utf8 COMMENT='商品订单详情表';

-- ----------------------------
-- Records of mer_order_detail_tb
-- ----------------------------
INSERT INTO `mer_order_detail_tb` VALUES ('1101385397453631490', '和舰科技', 'http://localhost:8080/uploaderPath/img/20190227/1551233394388.png', null, null, '北京市北京市东城区', '2019-03-01 15:35:16', '2019-03-01 15:35:16', '1101385397394911234');
INSERT INTO `mer_order_detail_tb` VALUES ('1101387087166717953', '和舰科技', 'http://localhost:8080/uploaderPath/img/20190227/1551233394388.png', null, null, '北京市北京市东城区', '2019-03-01 15:41:59', '2019-03-04 19:58:40', '1101387087112192002');

-- ----------------------------
-- Table structure for `mer_order_tb`
-- ----------------------------
DROP TABLE IF EXISTS `mer_order_tb`;
CREATE TABLE `mer_order_tb` (
  `mer_order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品订单id',
  `order_number` varchar(255) DEFAULT NULL COMMENT '订单号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '订单状态，1待发货，2已发货',
  `account_id` bigint(20) DEFAULT NULL COMMENT '下单人',
  PRIMARY KEY (`mer_order_id`),
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_UPDATEDATE` (`update_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1101387087112192003 DEFAULT CHARSET=utf8 COMMENT='商品订单表';

-- ----------------------------
-- Records of mer_order_tb
-- ----------------------------
INSERT INTO `mer_order_tb` VALUES ('1101385397394911234', '551064923459813376', '2019-03-01 15:35:16', '2019-03-01 15:35:16', '1', '1100211858902032386');
INSERT INTO `mer_order_tb` VALUES ('1101387087112192002', '551066612984184832', '2019-03-01 15:41:59', '2019-03-04 22:55:22', '2', '1100211858902032386');

-- ----------------------------
-- Table structure for `mer_tb`
-- ----------------------------
DROP TABLE IF EXISTS `mer_tb`;
CREATE TABLE `mer_tb` (
  `mer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `img_address` varchar(255) DEFAULT NULL COMMENT '封面',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态,默认1上架,2下架',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`mer_id`),
  KEY `INDEX_STATUS` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1100578788909481987 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of mer_tb
-- ----------------------------
INSERT INTO `mer_tb` VALUES ('1099972517915254785', 'sadf', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-25 18:00:59', '2019-02-28 23:55:31');
INSERT INTO `mer_tb` VALUES ('1100577889931722754', 'sdfsd', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:06:31', '2019-02-28 23:55:27');
INSERT INTO `mer_tb` VALUES ('1100577967568289794', 'dfsgfds梵蒂冈', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:06:50', '2019-02-28 23:55:23');
INSERT INTO `mer_tb` VALUES ('1100578341536628738', '测试2', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:08:19', '2019-02-28 23:53:10');
INSERT INTO `mer_tb` VALUES ('1100578371295215617', 'sd是的刚刚', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:08:26', '2019-02-28 23:53:17');
INSERT INTO `mer_tb` VALUES ('1100578416765665281', '发的规范', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:08:37', '2019-02-28 23:53:21');
INSERT INTO `mer_tb` VALUES ('1100578482935005186', '大范甘迪发', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:08:53', '2019-02-28 23:53:26');
INSERT INTO `mer_tb` VALUES ('1100578520482414594', '是大法官', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:09:02', '2019-02-28 23:53:31');
INSERT INTO `mer_tb` VALUES ('1100578560579960833', '法规和', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:09:11', '2019-02-28 23:53:36');
INSERT INTO `mer_tb` VALUES ('1100578619413463042', '规划局规划', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:09:25', '2019-02-28 23:53:40');
INSERT INTO `mer_tb` VALUES ('1100578654272323585', '建行卡', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:09:34', '2019-02-28 23:53:46');
INSERT INTO `mer_tb` VALUES ('1100578748119875585', '形成v型陈', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:09:56', '2019-02-28 23:53:51');
INSERT INTO `mer_tb` VALUES ('1100578788909481986', 'v不能', 'http://198.2.201.167:8080/uploaderPath/img/20190228/1551426466351.png', '1', '2019-02-27 10:10:06', '2019-02-28 23:53:55');

-- ----------------------------
-- Table structure for `number_tb`
-- ----------------------------
DROP TABLE IF EXISTS `number_tb`;
CREATE TABLE `number_tb` (
  `number_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '次数id',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图像',
  `free_number` int(11) DEFAULT '0' COMMENT '免费次数',
  `buy_number` int(11) DEFAULT '0' COMMENT '购买次数',
  `use_number` int(11) DEFAULT '0' COMMENT '使用次数',
  `surplus_number` int(11) DEFAULT '0' COMMENT '剩余次数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id外键',
  PRIMARY KEY (`number_id`),
  KEY `INDEX_USENUMBER` (`use_number`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1102512950695972867 DEFAULT CHARSET=utf8 COMMENT='次数表';

-- ----------------------------
-- Records of number_tb
-- ----------------------------
INSERT INTO `number_tb` VALUES ('1', '聂跃', null, '3', '0', '0', '3', '2019-02-25 16:51:14', '2019-02-25 16:51:14', '1000');
INSERT INTO `number_tb` VALUES ('1099957358396170242', null, null, '3', '0', '0', '3', '2019-02-25 17:00:45', '2019-02-25 17:00:45', '1099957358295506945');
INSERT INTO `number_tb` VALUES ('1100211859032055810', null, null, '33', '0', '14', '19', '2019-02-26 09:52:03', '2019-03-04 10:12:58', '1100211858902032386');
INSERT INTO `number_tb` VALUES ('1102512950695972866', null, null, '3', '0', '1', '2', '2019-03-04 02:15:46', '2019-03-04 02:16:26', '1102512950389788674');

-- ----------------------------
-- Table structure for `payment_tb`
-- ----------------------------
DROP TABLE IF EXISTS `payment_tb`;
CREATE TABLE `payment_tb` (
  `payment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付id',
  `subject` varchar(255) DEFAULT NULL COMMENT '主题',
  `body` varchar(255) DEFAULT NULL COMMENT '内容',
  `notify_url` varchar(255) DEFAULT NULL COMMENT '异步通知',
  `type` tinyint(4) DEFAULT NULL COMMENT '支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银',
  `order_number` varchar(255) DEFAULT NULL COMMENT '平台订单号',
  `money` decimal(11,2) DEFAULT NULL COMMENT '金额',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态，1已下单-未支付，2支付成功，3支付失败,4异常',
  `business_type` tinyint(4) DEFAULT NULL COMMENT '业务类型，1充值，2提现，3退款',
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务id,外键',
  `business_notify_url` longtext COMMENT '业务回调,外键',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id,外键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`payment_id`),
  KEY `INDEX_ORDERNUMBER` (`order_number`) USING BTREE,
  KEY `INDEX_TYPE` (`type`) USING BTREE,
  KEY `INDEX_BUSINESSTYPE` (`business_type`) USING BTREE,
  KEY `INDEX_BUSINESSID` (`business_id`) USING BTREE,
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_STATUS` (`status`) USING BTREE,
  KEY `INDEX_CREATEDATE` (`create_date`) USING BTREE,
  KEY `INDEX_UPDATEDATE` (`update_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1102742605646630914 DEFAULT CHARSET=utf8 COMMENT='支付表';

-- ----------------------------
-- Records of payment_tb
-- ----------------------------
INSERT INTO `payment_tb` VALUES ('1101387981593362433', '欢乐抓娃娃', '欢乐抓娃娃', 'http://localhost:8080/payment/bYPayNotifyUrl', '6', '551067504932290560', '10.00', '1', '1', '1100701513678192642', 'http://localhost:8080/home/user.html', '1100211858902032386', '2019-02-28 23:45:32', '2019-02-28 23:45:32');
INSERT INTO `payment_tb` VALUES ('1101399898294599682', '欢乐抓娃娃', '欢乐抓娃娃', 'http://localhost:8080/payment/bYPayNotifyUrl', '6', '551079421478436864', '10.00', '1', '1', '1100701513678192642', 'http://localhost:8080/home/user.html', '1100211858902032386', '2019-03-01 00:32:53', '2019-03-01 00:32:53');
INSERT INTO `payment_tb` VALUES ('1101410742579511297', '欢乐抓娃娃', '欢乐抓娃娃', 'http://localhost:8080/payment/bYPayNotifyUrl', '6', '551090265973063680', '10.00', '1', '1', '1100701513678192642', 'http://localhost:8080/home/user.html', '1100211858902032386', '2019-03-01 01:15:58', '2019-03-01 01:15:58');
INSERT INTO `payment_tb` VALUES ('1101659596876988417', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '551339120199204864', '10.00', '1', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-01 17:44:50', '2019-03-01 17:44:50');
INSERT INTO `payment_tb` VALUES ('1101660626134024194', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '551340150278324224', '10.00', '1', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-01 17:48:55', '2019-03-01 17:48:55');
INSERT INTO `payment_tb` VALUES ('1102713579225796610', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '552393102296350720', '10.00', '1', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-04 15:32:59', '2019-03-04 15:32:59');
INSERT INTO `payment_tb` VALUES ('1102724174377439233', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '552403697557045248', '10.00', '1', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-04 16:15:05', '2019-03-04 16:15:05');
INSERT INTO `payment_tb` VALUES ('1102737878242422785', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '552417400964775936', '10.00', '1', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-04 17:09:32', '2019-03-04 17:09:32');
INSERT INTO `payment_tb` VALUES ('1102742605646630913', '欢乐抓娃娃', '欢乐抓娃娃', 'http://zbb.tea18.cn/payment/bYPayNotifyUrl', '6', '552422129006542848', '10.00', '2', '1', '1100701513678192642', 'http://zbb.tea18.cn/home/user.html', '1100211858902032386', '2019-03-04 17:28:19', '2019-03-04 17:28:19');

-- ----------------------------
-- Table structure for `permission_tb`
-- ----------------------------
DROP TABLE IF EXISTS `permission_tb`;
CREATE TABLE `permission_tb` (
  `permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型，默认0开放，1鉴权',
  `manager_name` varchar(255) DEFAULT NULL COMMENT '权限管理名',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名',
  `route` varchar(255) DEFAULT NULL COMMENT '权限路由',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `route` (`route`) USING BTREE,
  KEY `INDEX_TYPE` (`type`) USING BTREE,
  KEY `INDEX_MANAGERNAME` (`manager_name`) USING BTREE,
  KEY `INDEX_NAME` (`name`) USING BTREE,
  KEY `INDEX_ROUTE` (`route`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1101387301860261891 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of permission_tb
-- ----------------------------
INSERT INTO `permission_tb` VALUES ('1099921094326222850', '1', '账户管理', '账户增加', '/account/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094422691841', '1', '账户管理', '账户实名认证', '/account/auth', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094452051970', '1', '账户管理', '认证审核', '/account/authExamine', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094485606402', '0', '账户管理', '账户数量', '/account/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094506577922', '1', '账户管理', '账户删除', '/account/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094544326658', '1', '账户管理', '忘记密码', '/account/forgetPassword', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094577881090', '0', '账户管理', '是否登录', '/account/islogin', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094607241217', '0', '账户管理', '账户列表', '/account/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094649184257', '0', '账户管理', '账户单个加载', '/account/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094674350082', '0', '账户管理', '管理员登录', '/account/login', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094703710209', '0', '账户管理', '登出', '/account/loginout', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094737264642', '1', '账户管理', '手机号账户是否存在', '/account/phoneIsExist', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094766624770', '1', '账户管理', '账户修改', '/account/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094795984898', '1', '账户管理', '账户修改用户信息', '/account/updateInfo', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094825345026', '1', '账户管理', '更新邀请码', '/account/updateInviteCode', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094858899458', '1', '账户管理', '账户修改密码', '/account/updatePassword', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094888259585', '0', '账户管理', '手机验证码发送/邮箱验证链接', '/account/validCode', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094921814018', '0', '账户管理', 'web用户登录', '/account/weblogin', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094934396929', '0', '账户管理', 'web用户注册', '/account/webregister', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921094988922882', '1', '卡片管理', '卡片增加', '/card/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095018283009', '0', '卡片管理', '卡片数量', '/card/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095047643137', '1', '卡片管理', '卡片删除', '/card/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095089586177', '0', '卡片管理', '卡片列表', '/card/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095123140610', '0', '卡片管理', '卡片单个加载', '/card/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095148306433', '1', '卡片管理', '卡片修改', '/card/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095173472257', '1', '配置管理', '配置增加', '/config/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095194443778', '0', '配置管理', '配置数量', '/config/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095232192514', '1', '配置管理', '配置删除', '/config/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095261552642', '0', '配置管理', '配置列表', '/config/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095290912770', '0', '配置管理', '配置单个加载', '/config/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095320272898', '1', '配置管理', '配置修改', '/config/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095345438721', '1', '财务管理', '财务增加', '/finance/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095366410241', '0', '财务管理', '财务数量', '/finance/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095387381761', '1', '财务管理', '财务删除', '/finance/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095399964673', '0', '财务管理', '财务列表', '/finance/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095420936193', '0', '财务管理', '财务单个加载', '/finance/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095441907714', '1', '财务管理', '财务修改', '/finance/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095458684929', '1', '商品管理', '商品增加', '/mer/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095467073537', '0', '商品管理', '商品数量', '/mer/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095488045058', '1', '商品管理', '商品删除', '/mer/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095500627969', '0', '商品管理', '商品列表', '/mer/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095521599490', '0', '商品管理', '商品单个加载', '/mer/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095538376706', '1', '商品管理', '商品修改', '/mer/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095555153922', '1', '商品订单管理', '商品订单增加', '/merOrder/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095571931137', '0', '商品订单管理', '商品订单数量', '/merOrder/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095584514049', '1', '商品订单管理', '商品订单删除', '/merOrder/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095601291266', '0', '商品订单管理', '商品订单列表', '/merOrder/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095618068482', '0', '商品订单管理', '商品订单单个加载', '/merOrder/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095634845697', '1', '商品订单管理', '商品订单修改', '/merOrder/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095651622913', '1', '商品订单详情管理', '商品订单详情增加', '/merOrderDetail/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095664205825', '0', '商品订单详情管理', '商品订单详情数量', '/merOrderDetail/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095680983041', '1', '商品订单详情管理', '商品订单详情删除', '/merOrderDetail/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095697760257', '0', '商品订单详情管理', '商品订单详情列表', '/merOrderDetail/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095714537473', '0', '商品订单详情管理', '商品订单详情单个加载', '/merOrderDetail/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095727120386', '1', '商品订单详情管理', '商品订单详情修改', '/merOrderDetail/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095743897601', '1', '次数管理', '次数增加', '/number/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095760674818', '0', '次数管理', '次数数量', '/number/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095781646337', '1', '次数管理', '次数删除', '/number/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095794229250', '0', '次数管理', '次数列表', '/number/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095811006466', '0', '次数管理', '次数单个加载', '/number/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095823589378', '1', '次数管理', '次数修改', '/number/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095840366594', '1', '权限管理', '权限增加', '/permission/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095857143809', '0', '权限管理', '权限数量', '/permission/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095873921025', '1', '权限管理', '权限删除', '/permission/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095894892546', '1', '权限管理', '初始化权限', '/permission/init', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095915864065', '0', '权限管理', '权限列表', '/permission/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095932641282', '0', '权限管理', '权限单个加载', '/permission/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095945224194', '1', '权限管理', '权限修改', '/permission/update', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095966195713', '1', '收货信息管理', '收货信息增加', '/receiptInfo/add', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095982972930', '0', '收货信息管理', '收货信息数量', '/receiptInfo/count', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921095991361537', '1', '收货信息管理', '收货信息删除', '/receiptInfo/delete', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921096008138754', '0', '收货信息管理', '收货信息列表', '/receiptInfo/list', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921096029110273', '0', '收货信息管理', '收货信息单个加载', '/receiptInfo/load', '2019-02-25 14:36:39');
INSERT INTO `permission_tb` VALUES ('1099921096045887490', '1', '收货信息管理', '收货信息修改', '/receiptInfo/update', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096066859010', '1', '充值记录管理', '充值记录增加', '/rechargeRecord/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096087830530', '0', '充值记录管理', '充值记录数量', '/rechargeRecord/count', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096104607745', '1', '充值记录管理', '充值记录删除', '/rechargeRecord/delete', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096121384962', '0', '充值记录管理', '充值记录列表', '/rechargeRecord/list', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096133967874', '0', '充值记录管理', '充值记录单个加载', '/rechargeRecord/load', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096150745090', '1', '充值记录管理', '充值记录修改', '/rechargeRecord/update', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096167522305', '1', '充值项管理', '充值项增加', '/rechargeTerm/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096184299521', '0', '充值项管理', '充值项数量', '/rechargeTerm/count', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096196882433', '1', '充值项管理', '充值项删除', '/rechargeTerm/delete', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096213659650', '0', '充值项管理', '充值项列表', '/rechargeTerm/list', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096230436866', '0', '充值项管理', '充值项单个加载', '/rechargeTerm/load', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096243019778', '1', '充值项管理', '充值项修改', '/rechargeTerm/update', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096268185601', '1', '角色管理', '角色增加', '/role/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096276574210', '0', '角色管理', '角色数量', '/role/count', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096297545729', '1', '角色管理', '角色删除', '/role/delete', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096314322945', '0', '角色管理', '角色列表', '/role/list', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096326905858', '0', '角色管理', '角色单个加载', '/role/load', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096343683074', '1', '角色管理', '角色修改', '/role/update', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096360460290', '1', '角色权限管理', '角色权限增加', '/rolePermission/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096377237505', '0', '角色权限管理', '角色权限数量', '/rolePermission/count', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096394014721', '1', '角色权限管理', '角色权限删除', '/rolePermission/delete', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096410791938', '0', '角色权限管理', '角色权限列表', '/rolePermission/list', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096427569154', '0', '角色权限管理', '角色权限单个加载', '/rolePermission/load', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096444346369', '1', '角色权限管理', '角色权限修改', '/rolePermission/update', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096461123586', '0', '工具接口管理', '根据url获取内容', '/tool/contentByUrl', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096473706498', '0', '工具接口管理', '上传文件', '/tool/file/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096486289410', '0', '工具接口管理', '获取sessionId', '/tool/getSession', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096503066625', '0', '工具接口管理', '验证码', '/tool/getVerificationCode', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096519843842', '0', '工具接口管理', '上传图片', '/tool/img/add', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099921096536621058', '0', '工具接口管理', 'test', '/tool/test', '2019-02-25 14:36:40');
INSERT INTO `permission_tb` VALUES ('1099932583745449986', '1', '财务管理', '管理员充值', '/finance/recharge', '2019-02-25 15:22:18');
INSERT INTO `permission_tb` VALUES ('1099932583850307586', '1', '财务管理', '管理员修改或增加交易密码', '/finance/updatePasswordByFinanceId', '2019-02-25 15:22:18');
INSERT INTO `permission_tb` VALUES ('1101038747400953858', '1', '支付管理', '支付增加', '/payment/add', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747535171585', '1', '支付管理', '宝盈支付下单', '/payment/bYPay', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747593891842', '0', '支付管理', '宝盈支付回调', '/payment/bYPayNotifyUrl', '2019-02-28 16:43:15');
INSERT INTO `permission_tb` VALUES ('1101038747644223489', '0', '支付管理', '支付浏览数量', '/payment/count', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747677777922', '1', '支付管理', '支付删除', '/payment/delete', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747711332353', '0', '支付管理', '支付列表', '/payment/list', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747736498177', '0', '支付管理', '支付单个加载', '/payment/load', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101038747761664002', '1', '支付管理', '支付修改', '/payment/update', '2019-02-28 16:37:48');
INSERT INTO `permission_tb` VALUES ('1101387301113675777', '1', '卡片管理', '卡片合成', '/card/compose', '2019-03-01 01:19:10');
INSERT INTO `permission_tb` VALUES ('1101387301860261890', '1', '商品管理', '商品抓取', '/mer/catch', '2019-03-01 17:39:37');

-- ----------------------------
-- Table structure for `receipt_info_tb`
-- ----------------------------
DROP TABLE IF EXISTS `receipt_info_tb`;
CREATE TABLE `receipt_info_tb` (
  `receipt_info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收货信息id',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `is_default` tinyint(4) DEFAULT '2' COMMENT '默认为2,1是,2不是',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id,外键',
  PRIMARY KEY (`receipt_info_id`),
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_ISDEFAULT` (`is_default`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1100601289592946690 DEFAULT CHARSET=utf8 COMMENT='收货地址表 ';

-- ----------------------------
-- Records of receipt_info_tb
-- ----------------------------
INSERT INTO `receipt_info_tb` VALUES ('1100601289592946689', '是的发生的', '15111336587', '北京市北京市东城区', '2', '2019-02-27 11:39:30', '2019-02-27 11:50:39', '1100211858902032386');

-- ----------------------------
-- Table structure for `recharge_record_tb`
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record_tb`;
CREATE TABLE `recharge_record_tb` (
  `recharge_record_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值记录id',
  `type` tinyint(4) DEFAULT NULL COMMENT '支付类型，1支付宝，2微信,3百度钱包,4Paypal,5网银,6保盈',
  `give_money` decimal(11,2) DEFAULT '0.00' COMMENT '充值真钱',
  `give_number` int(11) DEFAULT '0' COMMENT '赠送次数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '默认为1成功，2失败',
  `account_id` bigint(20) DEFAULT NULL COMMENT '账户id,外键',
  PRIMARY KEY (`recharge_record_id`),
  KEY `INDEX_ACCOUNTID` (`account_id`) USING BTREE,
  KEY `INDEX_STATUS` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1102742742926200835 DEFAULT CHARSET=utf8 COMMENT='充值记录表 ';

-- ----------------------------
-- Records of recharge_record_tb
-- ----------------------------
INSERT INTO `recharge_record_tb` VALUES ('1102742742926200834', '6', '10.00', '2', '2019-03-04 17:28:53', '2019-03-04 17:28:53', '1', '1100211858902032386');

-- ----------------------------
-- Table structure for `recharge_term_tb`
-- ----------------------------
DROP TABLE IF EXISTS `recharge_term_tb`;
CREATE TABLE `recharge_term_tb` (
  `recharge_term_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值项id',
  `title` varchar(255) DEFAULT NULL COMMENT '附加标题',
  `recharge_money` decimal(11,2) DEFAULT NULL COMMENT '充值真钱',
  `give_number` int(11) DEFAULT NULL COMMENT '赠送次数',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态,默认1上架,2下架',
  PRIMARY KEY (`recharge_term_id`),
  KEY `INDEX_STATUS` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1100701513678192643 DEFAULT CHARSET=utf8 COMMENT='充值项表 ';

-- ----------------------------
-- Records of recharge_term_tb
-- ----------------------------
INSERT INTO `recharge_term_tb` VALUES ('1099980611181338626', '10次机会', '20.00', '10', '2019-02-25 18:33:09', '2019-02-25 18:33:09', '1');
INSERT INTO `recharge_term_tb` VALUES ('1099980679573659649', '20次机会', '38.00', '20', '2019-02-25 18:33:25', '2019-02-25 18:33:25', '1');
INSERT INTO `recharge_term_tb` VALUES ('1099980752516800514', '50次机会', '94.00', '50', '2019-02-25 18:33:43', '2019-02-25 18:33:43', '1');
INSERT INTO `recharge_term_tb` VALUES ('1099980825296363521', '100次机会', '186.00', '100', '2019-02-25 18:34:00', '2019-02-28 23:56:24', '2');
INSERT INTO `recharge_term_tb` VALUES ('1100701513678192642', '2次机会', '10.00', '2', '2019-02-27 18:17:45', '2019-02-28 23:56:16', '1');

-- ----------------------------
-- Table structure for `role_permission_tb`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_tb`;
CREATE TABLE `role_permission_tb` (
  `role_permission_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色权限id',
  `region` tinyint(4) DEFAULT NULL COMMENT '范围，1公共，2自身',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id,外键',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `permission_id` bigint(20) DEFAULT NULL COMMENT '权限id,外键',
  PRIMARY KEY (`role_permission_id`),
  UNIQUE KEY `UNIQUE_ROLEID_PERMISSIONID` (`role_id`,`permission_id`) USING BTREE,
  KEY `INDEX_REGION` (`region`) USING BTREE,
  KEY `INDEX_ROLEID` (`role_id`) USING BTREE,
  KEY `INDEX_PERMISSIONID` (`permission_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1101411703125458947 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission_tb
-- ----------------------------
INSERT INTO `role_permission_tb` VALUES ('1100600733931552769', '1', '1001', '2019-02-27 11:37:18', '1099932583850307586');
INSERT INTO `role_permission_tb` VALUES ('1100600733931552770', '1', '1001', '2019-02-27 11:37:18', '1099932583745449986');
INSERT INTO `role_permission_tb` VALUES ('1100600734166433793', '1', '1001', '2019-02-27 11:37:18', '1099921096444346369');
INSERT INTO `role_permission_tb` VALUES ('1100600734577475586', '1', '1001', '2019-02-27 11:37:18', '1099921096394014721');
INSERT INTO `role_permission_tb` VALUES ('1100600734996905986', '1', '1001', '2019-02-27 11:37:18', '1099921096360460290');
INSERT INTO `role_permission_tb` VALUES ('1100600735424724993', '1', '1001', '2019-02-27 11:37:18', '1099921096343683074');
INSERT INTO `role_permission_tb` VALUES ('1100600735835766785', '1', '1001', '2019-02-27 11:37:18', '1099921096297545729');
INSERT INTO `role_permission_tb` VALUES ('1100600736255197185', '1', '1001', '2019-02-27 11:37:18', '1099921096268185601');
INSERT INTO `role_permission_tb` VALUES ('1100600736670433281', '1', '1001', '2019-02-27 11:37:18', '1099921096243019778');
INSERT INTO `role_permission_tb` VALUES ('1100600737094057986', '1', '1001', '2019-02-27 11:37:19', '1099921096196882433');
INSERT INTO `role_permission_tb` VALUES ('1100600737513488385', '1', '1001', '2019-02-27 11:37:19', '1099921096167522305');
INSERT INTO `role_permission_tb` VALUES ('1100600737932918786', '1', '1001', '2019-02-27 11:37:19', '1099921096150745090');
INSERT INTO `role_permission_tb` VALUES ('1100600738360737794', '1', '1001', '2019-02-27 11:37:19', '1099921096104607745');
INSERT INTO `role_permission_tb` VALUES ('1100600738771779586', '1', '1001', '2019-02-27 11:37:19', '1099921096066859010');
INSERT INTO `role_permission_tb` VALUES ('1100600739203792898', '1', '1001', '2019-02-27 11:37:19', '1099921096045887490');
INSERT INTO `role_permission_tb` VALUES ('1100600739631611905', '1', '1001', '2019-02-27 11:37:19', '1099921095991361537');
INSERT INTO `role_permission_tb` VALUES ('1100600740055236609', '1', '1001', '2019-02-27 11:37:19', '1099921095966195713');
INSERT INTO `role_permission_tb` VALUES ('1100600740470472705', '1', '1001', '2019-02-27 11:37:19', '1099921095945224194');
INSERT INTO `role_permission_tb` VALUES ('1100600740864737281', '1', '1001', '2019-02-27 11:37:19', '1099921095894892546');
INSERT INTO `role_permission_tb` VALUES ('1100600741288361985', '1', '1001', '2019-02-27 11:37:20', '1099921095873921025');
INSERT INTO `role_permission_tb` VALUES ('1100600741707792385', '1', '1001', '2019-02-27 11:37:20', '1099921095840366594');
INSERT INTO `role_permission_tb` VALUES ('1100600742123028481', '1', '1001', '2019-02-27 11:37:20', '1099921095823589378');
INSERT INTO `role_permission_tb` VALUES ('1100600742550847489', '1', '1001', '2019-02-27 11:37:20', '1099921095781646337');
INSERT INTO `role_permission_tb` VALUES ('1100600742978666497', '1', '1001', '2019-02-27 11:37:20', '1099921095743897601');
INSERT INTO `role_permission_tb` VALUES ('1100600743398096897', '1', '1001', '2019-02-27 11:37:20', '1099921095727120386');
INSERT INTO `role_permission_tb` VALUES ('1100600743813332993', '1', '1001', '2019-02-27 11:37:20', '1099921095680983041');
INSERT INTO `role_permission_tb` VALUES ('1100600744241152002', '1', '1001', '2019-02-27 11:37:20', '1099921095651622913');
INSERT INTO `role_permission_tb` VALUES ('1100600744647999490', '1', '1001', '2019-02-27 11:37:20', '1099921095634845697');
INSERT INTO `role_permission_tb` VALUES ('1100600745063235586', '1', '1001', '2019-02-27 11:37:20', '1099921095584514049');
INSERT INTO `role_permission_tb` VALUES ('1100600745482665985', '1', '1001', '2019-02-27 11:37:21', '1099921095555153922');
INSERT INTO `role_permission_tb` VALUES ('1100600745944039425', '1', '1001', '2019-02-27 11:37:21', '1099921095538376706');
INSERT INTO `role_permission_tb` VALUES ('1100600746325721089', '1', '1001', '2019-02-27 11:37:21', '1099921095488045058');
INSERT INTO `role_permission_tb` VALUES ('1100600746740957186', '1', '1001', '2019-02-27 11:37:21', '1099921095458684929');
INSERT INTO `role_permission_tb` VALUES ('1100600747164581890', '1', '1001', '2019-02-27 11:37:21', '1099921095441907714');
INSERT INTO `role_permission_tb` VALUES ('1100600747584012289', '1', '1001', '2019-02-27 11:37:21', '1099921095387381761');
INSERT INTO `role_permission_tb` VALUES ('1100600748003442689', '1', '1001', '2019-02-27 11:37:21', '1099921095345438721');
INSERT INTO `role_permission_tb` VALUES ('1100600748422873090', '1', '1001', '2019-02-27 11:37:21', '1099921095320272898');
INSERT INTO `role_permission_tb` VALUES ('1100600748838109186', '1', '1001', '2019-02-27 11:37:21', '1099921095232192514');
INSERT INTO `role_permission_tb` VALUES ('1100600749282705410', '1', '1001', '2019-02-27 11:37:21', '1099921095173472257');
INSERT INTO `role_permission_tb` VALUES ('1100600749681164289', '1', '1001', '2019-02-27 11:37:22', '1099921095148306433');
INSERT INTO `role_permission_tb` VALUES ('1100600750104788994', '1', '1001', '2019-02-27 11:37:22', '1099921095047643137');
INSERT INTO `role_permission_tb` VALUES ('1100600750524219394', '1', '1001', '2019-02-27 11:37:22', '1099921094988922882');
INSERT INTO `role_permission_tb` VALUES ('1100600750935261185', '1', '1001', '2019-02-27 11:37:22', '1099921094858899458');
INSERT INTO `role_permission_tb` VALUES ('1100600751350497282', '1', '1001', '2019-02-27 11:37:22', '1099921094825345026');
INSERT INTO `role_permission_tb` VALUES ('1100600751774121986', '1', '1001', '2019-02-27 11:37:22', '1099921094795984898');
INSERT INTO `role_permission_tb` VALUES ('1100600752201940994', '1', '1001', '2019-02-27 11:37:22', '1099921094766624770');
INSERT INTO `role_permission_tb` VALUES ('1100600752617177090', '1', '1001', '2019-02-27 11:37:22', '1099921094737264642');
INSERT INTO `role_permission_tb` VALUES ('1100600753036607489', '1', '1001', '2019-02-27 11:37:22', '1099921094544326658');
INSERT INTO `role_permission_tb` VALUES ('1100600753451843585', '1', '1001', '2019-02-27 11:37:22', '1099921094506577922');
INSERT INTO `role_permission_tb` VALUES ('1100600753904828417', '1', '1001', '2019-02-27 11:37:23', '1099921094452051970');
INSERT INTO `role_permission_tb` VALUES ('1100600754311675906', '1', '1001', '2019-02-27 11:37:23', '1099921094422691841');
INSERT INTO `role_permission_tb` VALUES ('1100600754722717697', '1', '1001', '2019-02-27 11:37:23', '1099921094326222850');
INSERT INTO `role_permission_tb` VALUES ('1100601044440072194', '2', '1002', '2019-02-27 11:38:32', '1099921095966195713');
INSERT INTO `role_permission_tb` VALUES ('1100601061859016705', '2', '1002', '2019-02-27 11:38:36', '1099921096045887490');
INSERT INTO `role_permission_tb` VALUES ('1101039982615072769', '1', '1001', '2019-02-28 16:42:43', '1101038747761664002');
INSERT INTO `role_permission_tb` VALUES ('1101039982619267074', '1', '1001', '2019-02-28 16:42:43', '1101038747677777922');
INSERT INTO `role_permission_tb` VALUES ('1101039983286161409', '1', '1001', '2019-02-28 16:42:43', '1101038747535171585');
INSERT INTO `role_permission_tb` VALUES ('1101039983697203202', '1', '1001', '2019-02-28 16:42:43', '1101038747400953858');
INSERT INTO `role_permission_tb` VALUES ('1101040062420094977', '2', '1002', '2019-02-28 16:43:02', '1101038747535171585');
INSERT INTO `role_permission_tb` VALUES ('1101411592412610561', '1', '1001', '2019-03-01 01:19:22', '1101387301860261890');
INSERT INTO `role_permission_tb` VALUES ('1101411613337993217', '1', '1001', '2019-03-01 01:19:27', '1101387301113675777');
INSERT INTO `role_permission_tb` VALUES ('1101411685656182785', '2', '1002', '2019-03-01 01:19:44', '1101387301860261890');
INSERT INTO `role_permission_tb` VALUES ('1101411703125458946', '2', '1002', '2019-03-01 01:19:48', '1101387301113675777');

-- ----------------------------
-- Table structure for `role_tb`
-- ----------------------------
DROP TABLE IF EXISTS `role_tb`;
CREATE TABLE `role_tb` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `duty` varchar(255) DEFAULT NULL COMMENT '角色职责',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role_tb
-- ----------------------------
INSERT INTO `role_tb` VALUES ('1000', '超级管理员', '超级管理员', '2019-02-25 14:06:13');
INSERT INTO `role_tb` VALUES ('1001', '普通管理员', '普通管理员', '2019-02-25 14:06:13');
INSERT INTO `role_tb` VALUES ('1002', '用户', '用户', '2019-02-25 14:06:13');
