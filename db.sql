-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for webbanhangdienthoai
CREATE DATABASE IF NOT EXISTS `webbanhangdienthoai` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `webbanhangdienthoai`;

-- Dumping structure for table webbanhangdienthoai.brands
CREATE TABLE IF NOT EXISTS `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.brands: ~0 rows (approximately)
INSERT INTO `brands` (`id`, `name`) VALUES
	(1, 'SamSung'),
	(2, 'Iphone'),
	(3, 'Oppo'),
	(4, 'XiaoMi'),
	(5, 'Vivo'),
	(6, 'Realme'),
	(7, 'Nokia'),
	(8, 'Apple'),
	(9, 'Ava'),
	(10, 'Xmobie'),
	(11, 'Asus'),
	(12, 'Sony');

-- Dumping structure for table webbanhangdienthoai.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.categories: ~0 rows (approximately)
INSERT INTO `categories` (`id`, `name`) VALUES
	(1, 'Android'),
	(2, 'IOS'),
	(3, 'Điện thoại phổ thông'),
	(4, 'Sạc dự phòng'),
	(5, 'Sạc, cáp'),
	(6, 'Ốp lưng điện thoại'),
	(7, 'Miếng dán điện thoại'),
	(8, 'Tai nghe');

-- Dumping structure for table webbanhangdienthoai.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `total_price` double NOT NULL,
  `status_order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcsee00gx95vdov3at0q5ai7ab` (`status_order_id`),
  CONSTRAINT `FKcsee00gx95vdov3at0q5ai7ab` FOREIGN KEY (`status_order_id`) REFERENCES `status_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.orders: ~0 rows (approximately)
INSERT INTO `orders` (`id`, `address`, `description`, `first_name`, `last_name`, `order_date`, `phone`, `total_price`, `status_order_id`) VALUES
	(1, '21/2 Vườn Lài, Quận 12, Thành phố HCM', 'Giao hàng nhanh cho tôi', 'Nguyễn Văn', 'A', '2024-07-02 23:20:07.568000', '0967061646', 50000000, 3);

-- Dumping structure for table webbanhangdienthoai.order_details
CREATE TABLE IF NOT EXISTS `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjyu2qbqt8gnvno9oe9j2s2ldk` (`order_id`),
  KEY `FK4q98utpd73imf4yhttm3w0eax` (`product_id`),
  KEY `FKk2w14ysy80rutm079xu6d4ygm` (`user_id`),
  CONSTRAINT `FK4q98utpd73imf4yhttm3w0eax` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKjyu2qbqt8gnvno9oe9j2s2ldk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKk2w14ysy80rutm079xu6d4ygm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.order_details: ~0 rows (approximately)
INSERT INTO `order_details` (`id`, `quantity`, `order_id`, `product_id`, `user_id`) VALUES
	(1, 2, 1, 1, 2);

-- Dumping structure for table webbanhangdienthoai.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `payment_date` varchar(255) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8vo36cen604as7etdfwmyjsxt` (`order_id`),
  CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.payments: ~0 rows (approximately)

-- Dumping structure for table webbanhangdienthoai.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `detail_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `status_product_id` bigint DEFAULT NULL,
  `type_of_product` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  KEY `FKn4202fu2wg7klpnk0gjxfhcqx` (`status_product_id`),
  KEY `FKdvwwui8u84w31ntrvxih5vp2i` (`type_of_product`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKdvwwui8u84w31ntrvxih5vp2i` FOREIGN KEY (`type_of_product`) REFERENCES `type_of_product` (`id`),
  CONSTRAINT `FKn4202fu2wg7klpnk0gjxfhcqx` FOREIGN KEY (`status_product_id`) REFERENCES `status_product` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.products: ~0 rows (approximately)
INSERT INTO `products` (`id`, `description`, `detail_description`, `image`, `name`, `price`, `stock`, `brand_id`, `category_id`, `status_product_id`, `type_of_product`) VALUES
	(1, 'iPhone 15 Pro Max là một chiếc điện thoại thông minh cao cấp được mong đợi nhất năm 2023.', NULL, 'ip15.jpg', 'Điện thoại iPhone 15 Pro Max 256GB ', 25000000, 38, 2, 2, 1, 1),
	(2, 'Xiaomi với thiết kế mặt lưng kính cao cấp cùng bộ thông số ấn tượng như camera 108 MP, viên pin 5030 mAh, tần số quét 90 Hz, hiệu năng mạnh mẽ,... ', NULL, 'redmi13.jpg', 'Xiaomi Redmi 13 6GB/128GB', 4000000, 65, 4, 1, 1, 1),
	(3, 'Tiếp nối thành công của Galaxy M53 5G, Samsung tiếp tục tung ra thị trường mẫu điện thoại Samsung Galaxy M54 5G.', NULL, 'sansungm54.jpg', 'Điện thoại Samsung Galaxy M54 5G 8GB/256GB ', 9000000, 20, 1, 1, 1, 1),
	(4, 'Với hình dạng vuông vức, realme Note 50 trở nên nổi bật giữa các sản phẩm giá rẻ khác nhờ vẻ hiện đại. Mặt lưng và khung viền được làm từ chất liệu nhựa, không chỉ giúp giảm giá thành mà còn tối ưu hóa khối lượng, tạo ra trải nghiệm cầm nắm nhẹ nhàng.', NULL, 'realmenote50.jpg', 'Điện thoại realme Note 50 3GB/64GB', 2450000, 100, 6, 1, 1, 1),
	(5, 'Samsung Galaxy S24 Ultra mẫu điện thoại cao cấp được ra mắt vào đầu năm 2024, sản phẩm tiếp tục kế thừa và cải tiến từ thế hệ trước. Điểm đặc biệt là sử dụng chip Snapdragon 8 Gen 3 for Galaxy, camera 200 MP và tích hợp nhiều tính năng AI.', NULL, 'samsung-galaxy-s24-ultra-grey-thumb-200x200.jpg', 'Điện thoại Samsung Galaxy S24 Ultra 5G 12GB/256GB', 29900000, 25, 1, 1, 1, 1),
	(6, 'vivo Y100 mẫu điện thoại tầm trung với nhiều nâng cấp đáng giá, được vivo giới thiệu tại thị trường Việt Nam. ', NULL, 'vivo-y100-xanh-thumb-1-600x600.jpg', 'Điện thoại vivo Y100 8GB/256GB', 7690000, 150, 5, 1, 1, 1),
	(7, 'realme C55 là mẫu máy giá rẻ được mở bán chính thức tại thị trường Việt Nam vào tháng 03/2023. ', NULL, 'realme-c35-vang-thumb-200x200.jpg', 'Điện thoại realme C55 6GB/128GB ', 5540000, 95, 6, 1, 1, 1),
	(8, 'được cộng đồng người yêu công nghệ quan tâm nhiều nhờ sở hữu cấu hình mạnh mẽ, thiết kế đẹp và còn trang bị hệ thống ống kính xịn với độ phân giải cao nhất lên đến 200 MP.', NULL, 'realme-11-pro-plus-5g-thumb-600x600.jpg', 'Điện thoại realme 11 Pro+ 5G 12GB/512GB', 11000000, 0, 6, 1, 2, 1),
	(9, 'Nokia 3210 4G, một biểu tượng trường tồn của hãng Nokia, nay trở lại với vẻ ngoài cải tiến và công nghệ hiện đại. Đây là sự kết hợp giữa thiết kế hoài cổ và những tính năng tiên tiến, đáp ứng mọi nhu cầu cơ bản của người dùng.', NULL, 'nokia-3210-4g-yellow-thumb-200x200.jpg', 'Điện thoại Nokia 3210 4G', 990000, 100, 7, 3, 1, 1),
	(10, 'Nokia 110 4G Pro là một chiếc điện thoại phổ thông nổi bật, được thiết kế tinh tế và sang trọng. Điện thoại cho khả năng bền bỉ trong thời gian dài khiến bạn cảm thấy yên tâm khi sử dụng', NULL, 'nokia-110-4g-pro-240723-024641-200x200.jpg', 'Điện thoại Nokia 110 4G Pro ', 790000, 100, 7, 3, 1, 1),
	(11, 'Tai nghe Bluetooth True Wireless Sony WF-C700N sở hữu thiết kế hiện đại, nhỏ gọn dễ dàng mang theo bất cứ đâu, tai nghe còn trang bị nhiều công nghệ âm thanh hiện đại mang đến cho người dùng những trải nghiệm tuyệt vời.', NULL, 'tai-nghe-bluetooth-true-wireless-sony-wf-c700n-den-1.jpg', 'Tai nghe Bluetooth True Wireless Sony WF-C700N', 1990000, 100, 12, 8, 1, 2),
	(12, 'Tai nghe Có Dây Sony MDR-EX15AP với kiểu dáng hiện đại, cùng độ dài dây 1.2 m tiện lợi di chuyển khi sử dụng', NULL, 'tai-nghe-ep-sony-mdr-ex15aplize-xanh-1-org.jpg', 'Tai nghe Có Dây Sony MDR-EX15AP', 190, 100, 12, 8, 1, 2),
	(13, 'Pin sạc dự phòng Polymer 20000mAh 10.5W AVA+ Q7 mang đến cho người dùng một sản phẩm vừa mạnh mẽ, vừa tiện dụng.', NULL, 'pin-sac-du-phong-polymer-10000mah-ava-jp299-thumb-1-600x600.jpg', 'Pin sạc dự phòng Polymer 20000mAh 10.5W AVA+ Q7 ', 430000, 100, 9, 4, 1, 2),
	(14, 'Ốp lưng thiết kế dành cho Galaxy S22, cắt khoét tinh tế.\r\nChế tác vị trí nút điều khiển hoàn thiện, tiện trải nghiệm.\r\nGam màu đen dễ dùng cho nhiều độ tuổi, ôm sát bảo vệ thân máy khi va chạm.', NULL, 'op-lung-galaxy-s22-silicone-samsung-den-2-1.jpg', 'Ốp lưng Galaxy S22 Silicone Samsung Chính hãng', 158, 100, 1, 6, 1, 2),
	(15, 'Thiết kế đơn giản, dài 1 m, dễ mang theo.\r\nRút ngắn thời gian sạc qua công nghệ sạc nhanh SuperVOOC, công suất đến 80 W.\r\nTrao đổi dữ liệu giữa điện thoại và máy tính ổn định.', NULL, 'cap-type-c-supervooc-oppo-dl129-1-3.jpg', 'Cáp Type C SuperVOOC 1m OPPO DL129', 150, 1000, 3, 5, 1, 2),
	(16, 'Thiết kế giản dị, màu đen thanh lịch, phù hợp cho iPhone 13 Pro.\r\nSạc không dây dễ dàng nhờ ốp hỗ trợ tiêu chuẩn sạc không dây MagSafe.\r\nLàm từ vật liệu nhựa dẻo bền tốt, hạn chế nứt gãy, tháo lắp thuận tiện. ', NULL, 'op-lung-magsafe-iphone-13-pro-nhua-deo-apple-mm2k3-den-1.jpg', 'Ốp lưng MagSafe iPhone 13 Pro Silicone Apple MM2K3 Chính hãng', 300, 150, 8, 6, 1, 2),
	(17, 'Tai nghe Bluetooth True Wireless AVA+ DS206 là dòng tai nghe In-ear của nhà AVA+ với vẻ ngoài thời thượng và nhiều tiện ích hấp dẫn so với những chiếc tai nghe trong cùng tầm giá, hứa hẹn sẽ mang lại trải nghiệm ấn tượng cho người dùng.', NULL, 'tai-nghe-bluetooth-true-wireless-ava-ds206-1.jpg', 'Tai nghe Bluetooth True Wireless AVA+ DS206 ', 235, 150, 9, 8, 1, 2);

-- Dumping structure for table webbanhangdienthoai.reviews
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `rating` int NOT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpl51cejpw4gy5swfar8br9ngi` (`product_id`),
  KEY `FKcgy7qjc1r99dp117y9en6lxye` (`user_id`),
  CONSTRAINT `FKcgy7qjc1r99dp117y9en6lxye` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKpl51cejpw4gy5swfar8br9ngi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.reviews: ~0 rows (approximately)

-- Dumping structure for table webbanhangdienthoai.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.role: ~0 rows (approximately)
INSERT INTO `role` (`id`, `name`) VALUES
	(1, 'ADMIN'),
	(2, 'USER');

-- Dumping structure for table webbanhangdienthoai.status_order
CREATE TABLE IF NOT EXISTS `status_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.status_order: ~0 rows (approximately)
INSERT INTO `status_order` (`id`, `name`) VALUES
	(1, 'Chờ xác nhận'),
	(2, 'Đang vận chuyển'),
	(3, 'Đã giao hàng'),
	(4, 'Hủy đơn');

-- Dumping structure for table webbanhangdienthoai.status_product
CREATE TABLE IF NOT EXISTS `status_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.status_product: ~0 rows (approximately)
INSERT INTO `status_product` (`id`, `name`) VALUES
	(1, 'Còn hàng'),
	(2, 'Hết hàng');

-- Dumping structure for table webbanhangdienthoai.type_of_product
CREATE TABLE IF NOT EXISTS `type_of_product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.type_of_product: ~0 rows (approximately)
INSERT INTO `type_of_product` (`id`, `name`) VALUES
	(1, 'Điện thoại'),
	(2, 'Phụ kiện');

-- Dumping structure for table webbanhangdienthoai.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.users: ~0 rows (approximately)
INSERT INTO `users` (`id`, `address`, `birthdate`, `created_at`, `email`, `enabled`, `first_name`, `gender`, `image`, `last_name`, `password`, `phone`, `username`) VALUES
	(1, NULL, NULL, '2024-07-02 22:33:21.091000', 'nguyenhung20032308@gmail.com', NULL, 'Nguyễn', NULL, NULL, 'Hùng', '$2a$10$PBz.OIc1tELKv.I0B2gjQOn7WsWWyn5zHUKsJS73gLmnqd4g1eInu', '0967061645', 'admin'),
	(2, NULL, NULL, '2024-07-02 23:16:14.490000', 'user01@gmail.com', NULL, 'Nguyễn Văn', NULL, NULL, 'A', '$2a$10$xSKjPzVnEshtNCXWQKp6H.S6qafQ.YtbBEguXzaP72VF2x5MYAOoK', '0967061646', 'user01');

-- Dumping structure for table webbanhangdienthoai.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKj345gk1bovqvfame88rcx7yyx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table webbanhangdienthoai.user_role: ~0 rows (approximately)
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 2);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
