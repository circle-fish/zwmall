
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
                         `id` int NOT NULL AUTO_INCREMENT COMMENT '购物车id',
                         `userId` int NOT NULL COMMENT '用户id',
                         `goodsId` int NOT NULL COMMENT '商品id',
                         `skuId` int NOT NULL COMMENT '规格id',
                         `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
                         `thumbUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品图片',
                         `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
                         `count` int NOT NULL DEFAULT 1 COMMENT '商品数量',
                         `checked` int NOT NULL DEFAULT 0 COMMENT '选中状态 0.选中 1.未选中',
                         `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                         `updateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 190 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
