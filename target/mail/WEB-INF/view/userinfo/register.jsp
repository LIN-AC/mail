<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/8
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册</title>
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="./css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="./css/animate.css" rel="stylesheet">
    <link href="./css/style.css" rel="stylesheet">
    <!-- Mainly scripts -->
    <script src="./js/jquery-3.1.1.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.js"></script>
    <!-- iCheck -->
    <script src="./js/plugins/iCheck/icheck.min.js"></script>
    <script src="./js/bootstrap-dialog.js"></script>
    <script>
        $(document).ready(function(){
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
        //跳转至登录页面
        function toLogin() {
            location.href="./LoginServlet"
        }
        //注册方法
        function regist() {
            var values = $("#register_form").serialize();
            var userName = $("[name='user_name']").val();
            if (userName=="" || userName==undefined){
                Bootstrap.alert("请输入用户名");
                return;
            }
            var password = $("[name='password']").val();
            if (password=="" || password==undefined){
                Bootstrap.alert("请输入密码");
                return;
            }
            var rePassword = $("[name='re_password']").val();
            if (rePassword!=password){
                Bootstrap.alert("两次密码不一致");
                return;
            }
            var obj ={
                url:"./UniqueServlet?user_name="+userName,
                data: values,
                success:function (data) {
                    if (data=="0"){
                        Bootstrap.tip("用户名已存在,注册失败");
                        return;
                    }
                    if (data=="1"){
                        obj = {
                            url:"./RegisterServlet",
                            type:"post",
                            data:values,
                            success:function (data) {
                                if (data=="1"){
                                    Bootstrap.tip("注册成功");
                                    setTimeout("toLogin()",3000);
                                    return;
                                } else {
                                    Bootstrap.tip("注册失败");
                                    return;
                                }
                            }
                        };
                        $.ajax(obj);
                    }

                }
            }
            $.ajax(obj);
        }
    </script>
</head>

<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">Yk</h1>
        </div>
        <h3>欢迎注册</h3>
        <form class="m-t" id="register_form">
            <input class="form-group form-control" name="user_name" style="width:59%;display:inline" placeholder="邮箱账户名" required="">
            <input class="form-group form-control" style="width:39%;display:inline;padding-left: 0;" value="@yukino.asia" disabled="disabled" required="">
            <input name="password" type="password" class="form-group form-control" placeholder="请输入邮箱密码">
            <input name="re_password" type="password" class="form-group form-control" placeholder="请确认邮箱密码">
            <button type="button" class="btn btn-primary block full-width m-b" onclick="regist()">注册</button>
            <p class="text-muted text-center"><small>已经有账号了?</small></p>
            <button type="button" class="btn btn-sm btn-white btn-block" onclick="toLogin()">登录</button>
        </form>
    </div>
</div>
</body>
</html>
