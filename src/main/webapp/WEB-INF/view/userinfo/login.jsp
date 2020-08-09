<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/8
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录</title>
    <link href="./css/bootstrap.min.css" rel="stylesheet">
    <link href="./font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="./css/animate.css" rel="stylesheet">
    <link href="./css/style.css" rel="stylesheet">
    <!-- Mainly scripts -->
    <script src="./js/jquery-3.1.1.min.js"></script>
    <script src="./js/popper.min.js"></script>
    <script src="./js/bootstrap.js"></script>
    <script src="./js/bootstrap-dialog.js"></script>
    <script>
        function login() {
            var userName = $("[name='user_name']").val();
            if (userName =="" || userName==undefined){
                Bootstrap.alert("请输入用户名");
                return;
            }
            var password = $("[name='password']").val();
            if (password == "" || password==undefined){
                Bootstrap.alert("请输入密码");
                return;
            }
            var values = $("#login_form").serialize();
            // console.log(values);
            var obj = {
                url:"./LoginServlet",
                type:"post",
                data:values,
                success:function (data) {
                    if (data=="1"){
                        location.href="./MainServlet";
                        return;
                    }
                    else {
                        Bootstrap.tip("用户名或密码错误");
                    }
                }
            };
            $.ajax(obj);
        }
        function toRegist() {
            location.href="./RegisterServlet";
        }
    </script>
</head>
<body class="gray-bg">
<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>
            <h1 class="logo-name">Yk</h1>
        </div>
        <h3>欢迎登陆</h3>
        <form class="m-t" id="login_form">
            <input class="form-group form-control" name="user_name" style="width:59%;display:inline" placeholder="邮箱账户名" required="">
            <input class="form-group form-control" style="width:39%;display:inline;padding-left: 0;" value="@yukino.asia" disabled="disabled" required="">
            <input type="password" name="password" class="form-group form-control" placeholder="邮箱密码">
            <button type="button" class="btn btn-primary block full-width m-b" onclick="login()">登录</button>
            <a href="#"><small>忘记密码?</small></a>
            <p class="text-muted text-center"><small>还没有账号?</small></p>
            <button type="button" class="btn btn-sm btn-white btn-block" onclick="toRegist()">注册账号</button>
        </form>
    </div>
</div>
</body>
</html>
