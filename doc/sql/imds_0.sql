/*
 Navicat Premium Data Transfer

 Source Server         : aa
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : imds_0

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 09/09/2018 22:22:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_order_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_0`;
CREATE TABLE `t_order_0`  (
  `order_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编码',
  `user_id` bigint(20) DEFAULT NULL,
  `total_price` decimal(20, 2) DEFAULT 0.00 COMMENT '订单总额',
  `pay_price` decimal(20, 2) DEFAULT 0.00 COMMENT '应付金额',
  `amount` bigint(20) DEFAULT 0 COMMENT '商品数量',
  `buyer_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收货人',
  `buyer_phone` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机',
  `status` int(2) DEFAULT 0 COMMENT '订单状态 0:未付款，1：已付款，2：已发货，3：已完成，4：取消订单，5：支付失败',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_1`;
CREATE TABLE `t_order_1`  (
  `order_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单编码',
  `user_id` bigint(20) DEFAULT NULL,
  `total_price` decimal(20, 2) DEFAULT 0.00 COMMENT '订单总额',
  `pay_price` decimal(20, 2) DEFAULT 0.00 COMMENT '应付金额',
  `amount` bigint(20) DEFAULT 0 COMMENT '商品数量',
  `buyer_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '收货人',
  `buyer_phone` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机',
  `status` int(2) DEFAULT 0 COMMENT '订单状态 0:未付款，1：已付款，2：已发货，3：已完成，4：取消订单，5：支付失败',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_0`;
CREATE TABLE `t_order_detail_0`  (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单明细编码',
  `order_id` bigint(20) NOT NULL COMMENT '订单编码',
  `goods_id` bigint(20) NOT NULL COMMENT '商品编码',
  `price` decimal(20, 2) DEFAULT 0.00 COMMENT '商品价格',
  `count` bigint(20) DEFAULT 0 COMMENT '商品数量',
  `goods_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名称',
  `status` int(2) DEFAULT 0 COMMENT '预留字段',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单明细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_1`;
CREATE TABLE `t_order_detail_1`  (
  `detail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单明细编码',
  `order_id` bigint(20) NOT NULL COMMENT '订单编码',
  `goods_id` bigint(20) NOT NULL COMMENT '商品编码',
  `price` decimal(20, 2) DEFAULT 0.00 COMMENT '商品价格',
  `count` bigint(20) DEFAULT 0 COMMENT '商品数量',
  `goods_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品名称',
  `status` int(2) DEFAULT 0 COMMENT '预留字段',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单明细信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
