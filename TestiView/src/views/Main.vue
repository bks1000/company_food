<style scoped>
    @import "main.css";
</style>

<template>
  <div id="main" class="wrap">
    <Row>
        <Col span="4" offset="20">欢迎：{{username}}</Col>
    </Row>
    <Row>
        <Col span="4" offset="20">
            <Affix :offset-top="20">
                <Button type="info" @click="info=true">已点菜品</Button>
            </Affix>
        </Col>
    </Row>
    <div class="masonry"> 
        <div class="item" v-for="menu in menus"> 
             <Table border :columns="colMenu" :data="menu" :width="400" :loading="loading"
             @on-select="selectOne" @on-selection-change="selectChange"></Table>
        </div>
     </div>

    <Modal
        v-model="info"
        title="确认订单"
        @on-ok="confirmOrder"
        @on-cancel="info=false">
        <Table border :columns="colOrder" :data="orders" :width="490"></Table>
    </Modal>

    <div>{{message}}</div>
    <Input v-model="value" placeholder="Enter something..." style="width: 300px"></Input>
    <Button type="primary" @click="send">Primary</Button>
  </div>
</template>

<script>
//import Vue from 'vue'
import Cookies from 'js-cookie';
import axios from 'axios';
import config from '../config/config'
//Vue.prototype.$http = axios;
//import "../libs/sockjs.min.js"
export default {
    data () {
        return {
            username:'',
            websocket:'',
            value:'',
            message:'',
            loading: true,
            info:false,
            menus:[[]], //菜单数据
            colMenu:[
                {type: 'selection',width: 60,align: 'center'},
                {title: 'ID',key: 'id',width: 60},
                {title: '类型',key: 'tname',width: 80},
                {title: '名称',key: 'name'},
                {title: '价格',key: 'price',width: 80},
            ],
            orders:[],
            colOrder:[
                {title: 'ID',key: 'id',width: 60},
                {title: '类型',key: 'tname',width: 80},
                {title: '名称',key: 'name'},
                {title: '价格',key: 'price',width: 80},
                {title: '数量',key:'count',width: 70, render: (h, params) => {
                    if(params.row.id==""){
                        return "";
                    }
                    return h('div', [
                        h('InputNumber',{
                            props: {
                                min: 1,
                                value:parseInt(params.row.count)
                            }, 
                            nativeOn: {
                                change: (c) => {
                                    //console.log(c.target.value);
                                    this.orders[params.index].count=parseInt(c.target.value);
                                    //console.log(this.orders);

                                    //重新计算合计
                                    this.orders[this.orders.length-1].price = 0;
                                    var total = 0;
                                    for(var order of this.orders){
                                        if(order.name==""){
                                            break;
                                        }
                                        total += order.price*order.count;
                                    }
                                    this.orders[this.orders.length-1].price=total;
                                }
                            }
                        })
                    ]);
                }},
                {title: '移除',width: 70, render: (h, params) => {
                    if(params.row.id==""){
                        return "";
                    }
                    return h('div', [
                        h('Button', {
                            props: {
                                type: 'error',
                                size: 'small'
                                ,align:'center'
                            },
                            on: {
                                click: () => {
                                    this.orders.splice(params.index,1);

                                    //重新计算合计
                                    this.orders[this.orders.length-1].price = 0;
                                    var total = 0;
                                    for(var order of this.orders){
                                        if(order.name==""){
                                            break;
                                        }
                                        total += order.price*order.count;
                                    }
                                    this.orders[this.orders.length-1].price=total;
                                }
                            }
                        }, 'X')
                    ]);
                }}
            ]
        };
    },
    methods: {
        query(){
            var _self = this;
            /*axios.post('/api/menu/getmenus').then((res)=>{
                console.log(res.data);
                this.menus = res.data;
            })*/
            axios.post(config.jvserver+'/menu/getmenus').then((res)=>{
                //console.log(res.data);
                this.menus = res.data;
            })
        },
        closeWebSocket(){
            //关闭连接
            this.websocket.close();
        },
        send(){
            //发送消息
            //var message = document.getElementById('text').value;
            this.websocket.send(this.value);
        },
        selectOne(selection,row){
            /*console.log(selection);//对于单个table有用，对于同时处理多个，没用。
            console.log(row);*/

            //先加入合计行
            if(this.orders.length==0){
                this.orders.push({'id':'','tname':'合计','name':'','price':0,'count':''});
            }
            row.count=1;
            for(var order of this.orders){
                if(order.id==row.id){
                    return;
                }
            }

            //在合计行之前加入
            this.orders.splice(this.orders.length-1,0,row);
            //this.orders.push(row);
            //console.log(this.orders);

            //计算合计
            this.orders[this.orders.length-1].price = this.orders[this.orders.length-1].price + row.price;
        },
        selectChange(selection){
            
        },
        confirmOrder(){
            console.log(this.orders);
            this.info=false;
        }
    },
    mounted: function(){
        this.query();

        /****************************************websocket****************************************** */
        this.username = Cookies.get("username");
        //判断当前浏览器是否支持WebSocket
        if('WebSocket' in window){
            this.websocket = new WebSocket(config.wsserver);//这里报错.代理不了websocket.通过spring boot设置跨域解决
        }
        else{
            alert('Not support websocket')
        }

        var _self = this;

        //连接发生错误的回调方法
        this.websocket.onerror = function(){
            //this.setMessageInnerHTML("error");
            _self.message = "error";
        };

        //连接成功建立的回调方法
        this.websocket.onopen = function(event){
            //this.setMessageInnerHTML("open");
            _self.message = "open";
        }

        //接收到消息的回调方法
        this.websocket.onmessage = function(event){
            //this.setMessageInnerHTML(event.data);
            console.log(this);
            console.log(event);
            _self.message = event.data;
        }

        //连接关闭的回调方法
        this.websocket.onclose = function(){
            //this.setMessageInnerHTML("close");
            _self.message="close";
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
            _self.websocket.close();
        }

        this.loading=false;
    }
};
</script>


