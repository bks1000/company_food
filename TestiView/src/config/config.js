import Env from './env';

let config = {
    env: Env,
    jvserver:'http://192.168.1.137:8080',//配置后端服务器地址
    //jvserver:'http://happydaily.imwork.net:33950',//外网地址，如果当前静态网站通过花生壳让外网访问的话，spring boot的地址也要是外网的。
    //wsserver:'ws://happydaily.imwork.net:33950/ws'//配置后端服务器地址
    wsserver:'ws://192.168.1.137:8080/ws'//配置后端服务器地址
};
export default config;