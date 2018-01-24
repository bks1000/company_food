<style scoped>
</style>

<template>
  <div id="main">
    <Row>
        <Col span="4" offset="20">欢迎：{{username}}</Col>
    </Row>
    <div>{{message}}</div>
     <Input v-model="value" placeholder="Enter something..." style="width: 300px"></Input>
    <Button type="primary" @click="send">Primary</Button>
  </div>
</template>

<script>
//import Vue from 'vue'
import Cookies from 'js-cookie';
import axios from 'axios';
//Vue.prototype.$http = axios;
//import "../libs/sockjs.min.js"
export default {
    data () {
        return {
            username:'',
            websocket:'',
            value:'',
            message:''
        };
    },
    methods: {
        setMessageInnerHTML(innerHTML){
            //将消息显示在网页上
            this.message = innerHTML;
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
        /****************************************websocket****************************************** */
        this.username = Cookies.get("username");
        //判断当前浏览器是否支持WebSocket
        if('WebSocket' in window){
            this.websocket = new WebSocket("ws://localhost:8080/ws");//这里报错.代理不了websocket.通过spring boot设置跨域解决
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


