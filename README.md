# arenema-coding-test

### Before migrate:

shnmkhk@shnmkhk:~/ws/arenema-coding-test$ mysql -u fxuser -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 202
Server version: 8.0.39-0ubuntu0.24.04.1 (Ubuntu)

Copyright (c) 2000, 2024, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use fxdb;
Database changed
mysql> show tables;
Empty set (0.00 sec)

mysql> \q
Bye

### Migration command

shnmkhk@shnmkhk:~/ws/arenema-coding-test$ flyway -configFiles=./flyway.conf migrate
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
Flyway Community Edition 7.0.2 by Redgate
Database: jdbc:mysql://localhost:3306/fxdb (MySQL 8.0)
Successfully validated 1 migration (execution time 00:00.059s)
Creating Schema History table `fxdb`.`flyway_schema_history` ...
Current version of schema `fxdb`: << Empty Schema >>
Migrating schema `fxdb` to version "1 - create table"
WARNING: DB: Specifying number of digits for floating point data types is deprecated and will be removed in a future release. (SQL State: HY000 - Error Code: 1681)
WARNING: DB: Specifying number of digits for floating point data types is deprecated and will be removed in a future release. (SQL State: HY000 - Error Code: 1681)
Successfully applied 1 migration to schema `fxdb` (execution time 00:00.271s)


### After migrate

shnmkhk@shnmkhk:~/ws/arenema-coding-test$ mysql -u fxuser -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 204
Server version: 8.0.39-0ubuntu0.24.04.1 (Ubuntu)

Copyright (c) 2000, 2024, Oracle and/or its affiliates.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> use fxdb; show tables;
Reading table information for completion of table and column names
You can turn off this feature to get a quicker startup with -A

Database changed
+-----------------------+
| Tables_in_fxdb        |
+-----------------------+
| flyway_schema_history |
| rate                  |
+-----------------------+
2 rows in set (0.01 sec)

mysql> desc rate;
+-----------------------+--------------+------+-----+---------+----------------+
| Field                 | Type         | Null | Key | Default | Extra          |
+-----------------------+--------------+------+-----+---------+----------------+
| id                    | int          | NO   | PRI | NULL    | auto_increment |
| base_currency_code    | char(3)      | NO   |     | NULL    |                |
| target_currency_code  | char(3)      | NO   |     | NULL    |                |
| base_currency_value   | double(16,2) | NO   |     | NULL    |                |
| target_currency_value | double(16,2) | NO   |     | NULL    |                |
| revision_date         | date         | NO   |     | NULL    |                |
+-----------------------+--------------+------+-----+---------+----------------+
6 rows in set (0.04 sec)

mysql> 

