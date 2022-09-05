用户微服务

1、首先建立数据库，并执行Restful/scripts/sql下的sql文件，新建对应的表

2、更改resources目录下是application.properties文件中的数据库链接地址，用户名和密码

3、使用打包命令进行程序打包:在根目录执行mvn package -DskipTests

4、执行命令:java -jar Restful/target/UserServerApi-0.0.1-SNAPSHOT.jar来运行程序

5、访问页面http://127.0.0.1:18600/swagger-ui.html查看接口api文档，并进行测试

6、运行脚本，可通过Restful/scripts目录下的sh文件运行，后续还可以集成Jenkins进行自动部署等操作
