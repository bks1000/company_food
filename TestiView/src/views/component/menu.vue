<template>
  <Row>
        <Col offset="6" span="12">
            <Menu mode="horizontal" theme="light" active-name="1" @on-select="menuclick">
                <MenuItem name="home">
                    <Icon type="pizza"></Icon>
                    订餐
                </MenuItem>
                <MenuItem name="order">
                    <Icon type="gear-b"></Icon>
                    订单管理
                </MenuItem>
                <MenuItem name="person">
                    <Avatar icon="person" size="small" />{{userName}}
                </MenuItem>
            </Menu>
        </Col>
    </Row>
</template>
<script>
//组件
export default {
    data: function () {
        return {
           //data必须是函数
        }
    },
    props:['userName'],//此值将从父组件中传入，注意命名规则
    methods:{
        menuclick(name){
            //console.log(name);
            window.location="/#/"+name;
        }
    }
}
</script>
