CREATE TABLE `demo`.`user` (
                               `id` INT NOT NULL,
                               `username` VARCHAR(45) NULL,
                               `password` VARCHAR(45) NULL,
                               PRIMARY KEY (`id`));
ALTER TABLE `demo`.`user`
    CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;

CREATE TABLE `demo`.`otp` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `username` VARCHAR(45) NULL,
                              `otp` VARCHAR(45) NULL,
                              PRIMARY KEY (`id`));

