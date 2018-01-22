const routers = [{
    path: '/',
    meta: {
        title: ''
    },
    component: (resolve) => require(['./views/index.vue'], resolve)
},{
    path: '/home',
    meta: {
        title: 'Home'
    },
    component: (resolve) => require(['./views/Main.vue'], resolve)
},{
    path: '/home1',
    meta: {
        title: 'Layout'
    },
    component: (resolve) => require(['./views/Layout.vue'], resolve)
}];
export default routers;