const routers = [{
    path: '/',
    meta: {
        title: ''
    },
    //component: (resolve) => require(['./views/index.vue'], resolve)
    component: (resolve) => require(['./views/login.vue'], resolve)
},{
    path: '/home',
    meta: {
        title: 'Home'
    },
    component: (resolve) => require(['./views/Main.vue'], resolve)
},{
    path: '/order',
    meta: {
        title: 'Order'
    },
    component: (resolve) => require(['./views/Order.vue'], resolve)
},{
    path: '/person',
    meta: {
        title: 'Person'
    },
    component: (resolve) => require(['./views/Person.vue'], resolve)
}
,{
    path: '/home1',
    meta: {
        title: 'Layout'
    },
    component: (resolve) => require(['./views/Layout.vue'], resolve)
}];
export default routers;