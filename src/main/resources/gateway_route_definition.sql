/*
 Navicat MySQL Data Transfer

 Source Server         : 阿里云服务器
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : youxiu326.xin:3306
 Source Schema         : super_man

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 01/04/2020 11:18:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gateway_route_definition
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route_definition`;
CREATE TABLE `gateway_route_definition` (
  `id` varchar(255) NOT NULL,
  `uri` varchar(255) DEFAULT NULL,
  `order_` int(255) DEFAULT NULL,
  `predicates_json` varchar(512) DEFAULT NULL,
  `filters_json` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of gateway_route_definition
-- ----------------------------
BEGIN;
INSERT INTO `gateway_route_definition` VALUES ('jd_route', 'http://www.jd.com', 0, '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/jd\"}}]', '');
INSERT INTO `gateway_route_definition` VALUES ('pinduoduo_route', 'http://www.pinduoduo.com', 0, '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/pinduoduo\"}}]', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
