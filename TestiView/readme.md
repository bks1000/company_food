这是一个前后台分离的项目，前端采用vue.js+iview，开发IDE为vscode
项目由iview-cli生成

启动脚本：npm run dev
dev命令在package.json ，scripts中配置了相关命令


#后端采用spring-boot框架
通过mvn package 构建
java -jar target/vueService....jar 运行

#spring boot 项目创建：
http://start.spring.io/


项目说明，这是尝试使用前后台分离开发的模式（前端采用vue.js+iview ；后端采用sprint boot开发）
开发模式，前后端沟通桥梁是webpack proxy 实现跨域访问

#正式发布，还没有想好。向下看


mvn package && java -jar target/gs-spring-boot-0.1.0.jar


官网及教程
https://projects.spring.io/spring-boot/
https://spring.io/guides/gs/spring-boot/
https://spring.io/guides/gs/rest-service/


spring boot 配置
http://blog.csdn.net/isea533/article/details/50281151


#前端发布（部署在iis或nginx中均可）：
运行 npm run build
会在项目根目录下生成dist文件夹和index_prod.html
1.在IIS中创建网站，把项目路径设置好。注意：网站的默认文档一定要设置为index_prod.html并且尽量不要有index.html。
因为index_prod.html中设置的资源文件路径是发布后的资源。

2.使用nginx配置静态资源
nginx.conf
server {
        listen       8011;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        #location / {
        #    proxy_pass    http://localhost:8033;
        #}

	location / {
            root   E:\Test\TestiView;
            index  index_prod.html;
        }

注意：在main.js中
// 路由配置
const RouterConfig = {
    //mode: 'history', //这里如果打开，在部署后，路由无法跳转，暂时没有解决
    routes: Routers
};



#vue.js组件
https://vuefe.cn/v2/guide/components.html#Props
https://www.cnblogs.com/landeanfen/p/6518679.html#_label1
