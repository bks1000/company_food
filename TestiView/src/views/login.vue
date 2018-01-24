<style scoped>
    .login{
        width: 100%;
        height: 100%;
        background-image: url('https://file.iviewui.com/iview-admin/login_bg.jpg');
        background-size: cover;
        background-position: center;
        position: relative;
    }
    .form-con{
        position: fixed;
        right: 160px;
        top: 50%;
        transform: translateY(-60%);
        width: 300px;
        padding: 10px 0 0;
    }
    .form-header{
        font-size: 16px;
        font-weight: 300;
        text-align: center;
        padding: 30px 0;
    }
    .login-tip{
        font-size: 10px;
        text-align: center;
        color: #c3c3c3;
    }
    
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <div class="form-con">
                <Form ref="loginForm" :model="form" :rules="rules">
                    <FormItem prop="userName">
                        <Input v-model="form.userName" placeholder="请输入用户名">
                            <span slot="prepend">
                                <Icon :size="16" type="person"></Icon>
                            </span>
                        </Input>
                    </FormItem>
                    <FormItem prop="pwd">
                        <Input type="password" v-model="form.pwd" placeholder="请输入密码">
                            <span slot="prepend">
                                <Icon :size="14" type="locked"></Icon>
                            </span>
                        </Input>
                    </FormItem>
                    <FormItem>
                        <Button @click="handleSubmit" type="primary" long>登录</Button>
                    </FormItem>
                </Form>
                <p class="login-tip">输入任意用户名和密码即可</p>
            </div>
        </div>
    </div>
</template>

<script>
//import Vue from 'vue'
import Cookies from 'js-cookie';
import axios from 'axios';
//Vue.prototype.$http = axios;
export default {
    data () {
        return {
            form: {
                userName: '',
                pwd: ''
            },
            rules: {
                userName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                pwd: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    console.log(this.form);
                    //return;
                    /*Cookies.set('user', this.form.userName);
                    Cookies.set('pwd', this.form.pwd);
                    this.$store.commit('setAvator', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3448484253,3685836170&fm=27&gp=0.jpg');
                    if (this.form.userName === 'iview_admin') {
                        Cookies.set('access', 0);
                    } else {
                        Cookies.set('access', 1);
                    }
                    this.$router.push({
                        name: 'home_index'
                    });*/
                    var _self = this;
                    axios.post('/api/login',{username: this.form.userName, pwd: this.form.pwd}).then((res)=>{
                        console.log(res);
                        if(res.data.length>20){
                            Cookies.set('token', res.data);
                            Cookies.set('username', this.form.userName);
                            window.location="home";
                        }
                    })
                }
            });
        }
    }
};
</script>

