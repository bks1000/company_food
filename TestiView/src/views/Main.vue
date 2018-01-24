<style scoped>
    @import "main.css";
</style>

<template>
  <div id="main" class="wrap">
    <Row>
        <Col span="4" offset="20">欢迎：{{username}}</Col>
    </Row>
    <div class="masonry"> 
        <div class="item" v-for="menu in menus"> 
             <Table border :columns="columns7" :data="menu" :width="400"></Table>
        </div>
     </div>

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
            menus:[[]], //菜单
            columns7:[
                {type: 'selection',width: 40,align: 'center'},
                {title: 'ID',key: 'id',width: 60},
                {title: '类型',key: 'tname',width: 80},
                {title: '名称',key: 'name'},
                {title: '价格',key: 'price',width: 80},
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
                console.log(res.data);
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
    }
};
</script>


