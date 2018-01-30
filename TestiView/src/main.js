import Vue from 'vue';
import iView from 'iview';
import VueRouter from 'vue-router';
import Routers from './router';
import Vuex from 'vuex';
import Util from './libs/util';
import App from './app.vue';
import 'iview/dist/styles/iview.css';

import base from './base'
Vue.use(base);

//在main.js中如下声明使用
//在其他vue组件中就可以this.$http调用使用
//axios中文文档  ：https://www.kancloud.cn/yunye/axios/234845
import axios from 'axios';
Vue.prototype.$http=axios;


Vue.use(VueRouter);
Vue.use(Vuex);

Vue.use(iView);



// 路由配置
const RouterConfig = {
    //mode: 'history',
    routes: Routers
};
const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    Util.title(to.meta.title);
    next();
});

router.afterEach(() => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});


const store = new Vuex.Store({
    state: {

    },
    getters: {

    },
    mutations: {

    },
    actions: {

    }
});

import menu from "./views/component/menu.vue" 
//全局注册vue组件
Vue.component('my-menu', menu);

new Vue({
    el: '#app',
    router: router,
    store: store,
    render: h => h(App)
});