USE server;
CREATE TABLE `mock_info` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) DEFAULT NULL,
  `proto` varchar(10) DEFAULT NULL,
  `domain` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `bind` int(2) DEFAULT NULL,
  `json` longtext,
  `port` int(6) unsigned zerofill NOT NULL DEFAULT '000000',
  `filename` varchar(20) DEFAULT NULL,
  `status` int(4) DEFAULT '200',
  `complete_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
