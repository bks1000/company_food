这是一个前后台分离的项目，前端采用vue.js+iview，开发IDE为vscode
项目由iview-cli生成

启动脚本：npm run dev
dev命令在package.json ，scripts中配置了相关命令


后端采用spring-boot框架
通过mvn package 构建
java -jar target/vueService....jar 运行




项目说明，这是尝试使用前后台分离开发的模式（前端采用vue.js+iview ；后端采用sprint boot开发）
开发模式，前后端沟通桥梁是webpack proxy 实现跨域访问

正式发布，还没有想好。


mvn package && java -jar target/gs-spring-boot-0.1.0.jar


官网及教程
https://projects.spring.io/spring-boot/
https://spring.io/guides/gs/spring-boot/
https://spring.io/guides/gs/rest-service/


spring boot 配置
http://blog.csdn.net/isea533/article/details/50281151