<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/7
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<link href="./css/bootstrap.min.css" rel="stylesheet">
<div style="text-align: center">
    <form id="image_update" >
        <input type="hidden" name="user_name" value="${userInfo.userName}">
        <input type="hidden" name="old_imageName" value="${userInfo.imageName}">
        <c:if test="${empty userInfo.imageName}">
            <img id="image" alt="图片" class="rounded-circle" src="./img/default.jpg" width="60px">
        </c:if>
        <c:if test="${not empty userInfo.imageName}">
            <img id="image" alt="图片" class="rounded-circle" src="${file_server}${userInfo.imageName}">
        </c:if>
        <div style="margin-top: 10px" >
            <a href="###" class="file">选择头像
                <input type="file" accept="image/*" id="file" name="file" onchange="change(this)" style="margin-top: 10px;">
            </a>
        </div>
    </form>
    <style>
        .file {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        function change(object){
            var files = object.files;
            if(files.length>0) {
                var file = files[0];
                if(/^image\/png$|jpeg$/.test(file.type)) {
                    $('#image')[0].src = URL.createObjectURL(file);
                } else {
                    alert("请选择png或jpeg类型的图片文件！");
                }
            } else {
                alert("请选择图片文件！");
            }
        }
    </script>
</div>