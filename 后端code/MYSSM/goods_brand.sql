DROP TABLE IF EXISTS `goods_brand`;
CREATE TABLE `goods_brand`  (
                                `id` int NOT NULL AUTO_INCREMENT COMMENT '商品品牌id',
                                `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品品牌名称',
                                `introduction` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品品牌介绍',
                                `logoUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品品牌logo',
                                `sortNo` int NOT NULL DEFAULT 0 COMMENT '排序号',
                                `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                                `updateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_brand
-- ----------------------------
INSERT INTO `goods_brand` VALUES (13, '小米', '让每个人都能享受科技的乐趣！', 'https://static.zwmall.chengdongqing.top/logo/bab7d4502c184ffe97f7d10b2b5847e2.png', 9, '2020-05-17 16:32:43', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (14, '苹果', NULL, 'https://static.zwmall.chengdongqing.top/logo/49b709b6e1b04c96975bbfc6c083f4ca.jpg', 1, '2020-05-17 16:33:05', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (15, '华为', NULL, 'https://static.zwmall.chengdongqing.top/logo/4279bb54fe0a4209884f1159c4000c20.jpg', 2, '2020-05-17 16:33:31', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (16, 'OPPO', NULL, 'https://static.zwmall.chengdongqing.top/logo/bd1b6c0fd3d142f397342259b9e93326.jfif', 3, '2020-05-17 16:34:05', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (17, 'vivo', NULL, 'https://static.zwmall.chengdongqing.top/logo/19e3df35ab394ee5b79015b84e611f58.jpg', 4, '2020-05-17 16:34:28', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (18, '魅族', NULL, 'https://static.zwmall.chengdongqing.top/logo/4687ba905b4342d19744d9488ade6677.jfif', 5, '2020-05-17 16:35:11', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (19, '中兴', NULL, 'https://static.zwmall.chengdongqing.top/logo/23173d95ea39409ea0edcc27ebd33415.jfif', 6, '2020-05-17 16:35:32', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (20, '三星', NULL, 'https://static.zwmall.chengdongqing.top/logo/6b4f59d841f840cd865abaa34699e0b1.jpg', 7, '2020-05-17 16:36:14', '2020-06-30 21:05:33');
INSERT INTO `goods_brand` VALUES (21, '一加', NULL, 'https://static.zwmall.chengdongqing.top/logo/ee633d66ee664ddab6ad71542b8048b5.jfif', 8, '2020-05-17 16:36:39', '2020-06-30 21:05:33');
