<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/13
  Time: 10:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="password_form">
    <div class="form-group">
        <label for="old">旧密码</label>
        <input type="password" name="old" class="form-control" id="old" placeholder="请输入旧密码">
    </div>
    <div class="form-group">
        <label for="new">新密码</label>
        <input type="password" name="new" class="form-control" id="new" placeholder="请输入新密码">
    </div>
    <div class="form-group">
        <label for="re_new">确认密码</label>
        <input type="password" name="re_new" class="form-control" id="re_new" placeholder="请输入确认密码">
    </div>
</form>
