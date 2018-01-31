<template>
    <div id="order">
        <my-menu v-bind:user-name="username"></my-menu>
        
        <Table border :columns="colOrder" :data="tableData"></Table>

        <Modal
        v-model="info"
        title="订单明细"
        @on-ok="info=false"
        @on-cancel="info=false">
        <Table border :columns="colOrderDetail" :data="orders" :width="490"></Table>
    </Modal>
    </div>
</template>

<script>
Date.prototype.format = function(fmt) { 
     var o = { 
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt; 
} 
//import Vue from 'vue'
import Cookies from 'js-cookie';
import axios from 'axios';
import config from '../config/config'
//Vue.prototype.$http = axios;
//import "../libs/sockjs.min.js"
export default {
    name:'order',
    data() {
        return {
            username:'',
            tableData:[],
            orders:[],
            info:false,
            colOrder:[
                {title: '订单日期',key: 'createdtime'},
                {title: '总价',key: 'total'},
                {
                    title: '操作',
                    key: 'action',
                    width: 260,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                style: {
                                    marginRight: '5px'
                                },
                                on: {
                                    click: () => {
                                        this.show(params.index)
                                    }
                                }
                            }, '查看明细'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.index)
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],
            colOrderDetail:[
                {title: '类型',key: 'tname',width: 80},
                {title: '名称',key: 'mname'},
                {title: '价格',key: 'price',width: 80},
                {title: '数量',key:'nums',width: 70}
            ]
        }
    },
    methods:{
        query(){
            var _self = this;
            /*axios.post('/api/menu/getmenus').then((res)=>{
                console.log(res.data);
                this.menus = res.data;
            })*/
            axios.post(config.jvserver+'/menu/queryOrders').then((res)=>{
                this.tableData = res.data;
            });
        
        },
        show(idx){
            //console.log(idx);
            var oid = this.tableData[idx].oid;
            //console.log(oid);
            var token = Cookies.get('token');
            axios.post(config.jvserver+'/menu/queryOrderDetails',{token:token,oid:oid}).then((res)=>{
                //console.log(res.data);
                this.orders = res.data;
            });
            this.info=true;
        },
        remove(idx){
            var t1 = this.tableData[idx].createdtime.split(" ")[0];
            var t2 = new Date().format("yyyy-MM-dd");
            if(t1!=t2){
                this.$Message.info('不是今天的订单，不可删除！');
                return;
            }
            this.$Modal.confirm({
                    title: '警告',
                    content: '确认要删除订单吗?',
                    onOk: () => {
                        var oid = this.tableData[idx].oid;
                        var token = Cookies.get('token');
                        axios.post(config.jvserver+'/menu/delOrder',{token:token,oid:oid}).then((res)=>{
                            //console.log(res);
                            if(res.data=="1"){
                                this.$Message.info('删除成功');
                                this.query();
                            }
                        });
                    },
                    onCancel: () => {
                        //this.$Message.info('Clicked cancel');
                    }
                });
            
        }
    },
    mounted:function(){
        this.query();
        this.username = Cookies.get("username");
    }
}
</script>