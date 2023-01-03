
-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
                           `id` int NOT NULL AUTO_INCREMENT COMMENT '横幅id',
                           `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'slide' COMMENT '类型 slide.轮播图 advert.商品横幅 promo.商品推销 category.商品类别广告',
                           `pictureUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片地址',
                           `linkMode` int NOT NULL DEFAULT 0 COMMENT '链接方式 0.内链 1.外链',
                           `linkUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外部链接地址',
                           `goodsId` int NULL DEFAULT NULL COMMENT '链接的商品id',
                           `goodsName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
                           `categoryId` int NULL DEFAULT NULL COMMENT '商品类别id,仅type等于category时需要',
                           `categoryName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品类别名称',
                           `status` int NOT NULL DEFAULT 0 COMMENT '状态 0.正常 1.下架',
                           `sortNo` int NOT NULL DEFAULT 0 COMMENT '排序号',
                           `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                           `updateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES (37, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/59f26a8abe18489db9ea8afe0ac963b7.jpg', 0, NULL, 91, '小米10 Pro', NULL, NULL, 0, 1, '2020-05-18 19:58:30', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (38, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/8ae283e981954979ac9b313ddcd35fba.webp', 0, NULL, 95, 'Redmi K30 5G', NULL, NULL, 0, 0, '2020-05-18 19:58:50', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (40, 'promo', 'https://static.zwmall.chengdongqing.top/banner/promo/a51a490e11bb4ba7bb88f3d9b2bbf1b7.jpg', 1, 'http://www.mi.com/redminote8', NULL, NULL, NULL, NULL, 0, 0, '2020-05-18 20:01:32', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (41, 'promo', 'https://static.zwmall.chengdongqing.top/banner/promo/49586413690240e0a118db9cd4dedc85.jpg', 1, 'https://item.mi.com/product/10000203.html?selected=10000203', NULL, NULL, NULL, NULL, 0, 1, '2020-05-18 20:01:47', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (42, 'promo', 'https://static.zwmall.chengdongqing.top/banner/promo/3b487ef2a2ee4ed297db0d9cf2bbd310.jpg', 1, 'https://www.mi.com/shouhuan4/', NULL, NULL, NULL, NULL, 0, 2, '2020-05-18 20:01:57', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (43, 'category', 'https://static.zwmall.chengdongqing.top/banner/category/5bad604734da45438648b8fea979a440.webp', 0, NULL, 93, '小米MIX Alpha', 212, '手机', 0, 0, '2020-05-18 20:03:19', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (44, 'advert', 'https://static.zwmall.chengdongqing.top/banner/advert/44d2328a40644fc9a91768cb507616a9.webp', 1, 'https://www.mi.com/a/h/15351.html', NULL, NULL, NULL, NULL, 0, 0, '2020-05-18 20:08:48', '2020-06-30 20:56:55');
INSERT INTO `banner` VALUES (45, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/19f0f4ad51a24c8ba26229efc4ac6d35.jpg', 1, 'https://www.mi.com/redmi10x-5G', NULL, NULL, NULL, NULL, 0, 6, '2020-06-05 13:05:35', '2020-06-30 20:57:21');
INSERT INTO `banner` VALUES (46, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/10230aaf092646258ddf03dc8f20a4ae.webp', 0, 'https://www.mi.com/a/h/15707.html', 98, 'Redmi 智能电视 MAX 98\'\'', NULL, NULL, 0, 4, '2020-06-05 13:06:24', '2020-06-30 20:57:21');
INSERT INTO `banner` VALUES (47, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/5ea572dd21ca4e6f91b5fd4b97c91751.webp', 1, 'https://www.mi.com/a/h/15706.html', NULL, NULL, NULL, NULL, 0, 3, '2020-06-05 13:06:39', '2020-06-30 20:57:21');
INSERT INTO `banner` VALUES (48, 'slide', 'https://static.zwmall.chengdongqing.top/banner/slide/f24a7e326fcc4f2fb789a06796ed618f.webp', 0, 'https://www.mi.com/buy?product_id=10000225', 92, '小米10', NULL, NULL, 0, 5, '2020-06-05 13:12:22', '2020-06-30 20:57:21');
INSERT INTO `banner` VALUES (49, 'advert', 'https://static.zwmall.chengdongqing.top/banner/advert/6cefd045cb904c36a60706a7ac2e7620.webp', 1, 'https://www.mi.com/a/h/15629.html', NULL, NULL, NULL, NULL, 0, 1, '2020-06-05 13:15:29', '2020-06-30 20:57:21');
INSERT INTO `banner` VALUES (51, 'top', 'https://static.zwmall.chengdongqing.top/banner/top/7a632653348f4a428e0d91bce8c65f2e.jpg', 1, 'https://www.mi.com', NULL, '小米10 Pro', NULL, NULL, 0, 0, '2020-06-30 20:26:56', '2020-06-30 21:01:52');
