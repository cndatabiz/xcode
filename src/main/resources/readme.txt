xcode用户手册

1、功能介绍
用户配置数据库连接，获取数据库表信息，生成表对应代码，存放在gencode目录(用户可以配置)。
目录和文件说明：
config 目录是配置文件; gencode生成代码目录; readme.txt是用户指南, xcode-5.1.jar是运行程序。

2、程序参数配置
application.yml 是系统和一些公共配置,一般不需要更改;
application-mysql.yml  配置mysql数据库连接;
application-oracle.yml 配置oracle数据库连接;
application-postgresql.yml 配置postgresql数据库连接;

application.properties 是用户配置,用于配置具体数据库连接, 代码包前缀以及需要生成表名称配置.

3、启动程序
java -jar -Dfile.encoding=UTF8 xcode-5.2.jar

切换数据库连接
java -jar -Dfile.encoding=UTF8 xcode-5.2.jar --spring.profiles.active=mysql

4、生成代码
用户设置好相关配置后，在shell命令行输入命令: xcode 即生成代码。exit 命令退出。

示例：
xcode -t sys_user,sys_org,sys_role

如果没有使用-t指定参数，则会获取application.properties中的配置xcode.tables值。

5、搜索数据库表
通过ls 命令模糊搜索数据库中表信息。

示例:
ls -c sys_user 或者 ls -n sys_user
