<template>
    <div>
        <p>AAA</p>
        <Button @click="getData" type="primary">获取数据（GET）</Button>
        <Button @click="postData" type="success">获取数据(GET2)</Button>
        <Button @click="getData2" type="primary">获取数据（GET3）</Button>
        <Table stripe :columns="columns" :data="lst"></Table>
         <Menu :theme="dark" active-name="1">
             <Submenu name="1">
                    <template slot="title">
                        <Icon type="ios-paper"></Icon>
                        内容管理
                    </template>
                     <MenuItem v-for="item in lst" v-bind:key="item.id" name="item.id">
                     <Icon type="ios-paw"></Icon>
                        {{item.title}}
                    </MenuItem>
             </Submenu>
         </Menu>
    </div>
</template>

<script>
import axios from 'axios';
//axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

//请求前拦截
axios.interceptors.request.use((config) => {
    console.log("请求前拦截！");
    //console.log(config);
    return config;
}, (err) => {
    return Promise.reject(err);
});

export default {
   data(){
       return {
        lst:[],
        columns:[
                {
                    key: 'id',
                    title: 'ID'
                },
                {
                    key: 'title',
                    title: '标题'
                },
                {
                    key: 'stars',
                    title: '星级',
                }
            ]
       } 
   },
   methods:{
       getData(){
           //这种方式需要把this另外保存一下
            var _self = this;
            axios.get('src/views/data.json')
            .then(function (response) {
                //console.log(response);
                //console.log(this);//undefined
                _self.lst = response.data;
            })
            .catch(function (response) {
                //console.log(response);
            });
       },
       getData2(){
           //这种方式需要把this另外保存一下
            var _self = this;
            axios.get('/api/getData')
            .then(function (response) {
                //console.log(response);
                //console.log(this);//undefined
                console.log(response);
            })
            .catch(function (response) {
                console.log(response);
            });
       },
       postData(){
           
           //箭头函数是和父级上下文绑定在一起的
           //这种方式可以直接使用this调用当前data中的变量
           axios.get('src/views/data.json')
            .then((res)=>{
            if(res.data){
                this.lst = res.data;
                //window.location.href = '/';
            }else {
                //this.error = '';
            }
        })
       }
   }
}
</script>

