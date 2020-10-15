# dynamic-label-demo


    docker pull mysql/mysql-server:5.6
    
    For example, to start a new Docker container for the MySQL Community Server, use this command:
    docker run --name=mysql1 --publish 3306:3306 --restart on-failure -d mysql/mysql-server:5.6
    
    
    docker logs mysql1
    
    1. get the one time password
    docker logs mysql1 2>&1 | grep GENERATED
    
    GENERATED ROOT PASSWORD: PESxEmUv4fT3geNC@M+eb3J@cSI
    
    
    docker exec -it mysql2 mysql -uroot -p
    
    SET PASSWORD = PASSWORD('mediamath');
    
    
    docker exec -it mysql1 bash
     
    CREATE USER 'root'@'172.17.0.1' IDENTIFIED BY 'mediamath';
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'172.17.0.1' WITH GRANT OPTION;
    FLUSH PRIVILEGES;    

    CREATE DATABASE label;
    
    Add these columns : 
    enable, producer_app, schema_validation 
    
    DROP TABLE label;
    CREATE TABLE `label` (`id` int(11) NOT NULL AUTO_INCREMENT,
        `source` varchar(200) NOT NULL,
        `field` varchar(200) NOT NULL,
        `operation` varchar(200) NOT NULL,
        `enabled` tinyint(1) DEFAULT 0,
        `experimental` tinyint(1) DEFAULT 0,
        `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (`id`));
    
    use label;
    DELETE FROM label;
    INSERT INTO `label`.`label`(`id`, `source`, `field`, `operation`, `enabled`, `experimental` ) values(1, 'OPEN_RTB', 'site.demo', 'CALL_DELPHI', true, false);
    INSERT INTO `label`.`label`(`id`, `source`, `field`, `operation`, `enabled`, `experimental`) values(2, 'OPEN_RTB', 'site.domain', 'LOG', true, false);
    INSERT INTO `label`.`label`(`id`, `source`, `field`, `operation`, `enabled`, `experimental`) values(3, 'OPEN_RTB', 'site.page', 'LOG', true, false);
    INSERT INTO `label`.`label`(`id`, `source`, `field`, `operation`, `enabled`, `experimental`) values(4, 'OPEN_RTB', 'site.id', 'LOG', true, false);
    INSERT INTO `label`.`label`(`id`, `source`, `field`, `operation`, `enabled`, `experimental`) values(5, 'OPEN_RTB', 'site.domain', 'CALL_DELPHI', true, false);
    select * FROM label\G;



    How to install and run ui
    Steps to install
    Make sure you have Node.js installed.
    Navigate to the main directory (from terminal) where package.json is located.

    Run npm install or yarn install
    Run npm run serve or yarn serve to start the local development server and start prototyping.

