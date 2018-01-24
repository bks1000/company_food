import Env from './env';

let config = {
    env: Env,
    jvserver:'http://localhost:8080',//配置后端服务器地址
    wsserver:'ws://localhost:8080/ws'//配置后端服务器地址
};
export default config;