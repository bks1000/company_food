//全局函数
/*exports.install = function (Vue, options) {
    Vue.prototype.websocket = null;

    Vue.prototype.setMessageInnerHTML=function(msg){
        document.getElementById("message").innerHTML=msg;
    }

    Vue.prototype.closeWebSocket=function(){
        //关闭连接
        websocket.close();
    },
    Vue.prototype.send=function(){
        //发送消息
        var message = document.getElementById('text').value;
        websocket.send(message);
    }

    Vue.prototype.init = function (){
        if('WebSocket' in window){
            websocket = new WebSocket("ws://localhost:8080/ws");//这里报错.代理不了websocket.通过spring boot设置跨域解决
        }
        else{
            alert('Not support websocket')
        }

        //连接发生错误的回调方法
        websocket.onerror = function(){
            this.setMessageInnerHTML("error");
        };

        //连接成功建立的回调方法
        websocket.onopen = function(event){
            this.setMessageInnerHTML("open");
        }

        //接收到消息的回调方法
        websocket.onmessage = function(event){
            this.setMessageInnerHTML(event.data);
        }

        //连接关闭的回调方法
        websocket.onclose = function(){
            this.setMessageInnerHTML("close");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function(){
            websocket.close();
        }
    };
    
};*/