<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/15
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="draft_detail">
    <tbody>
        <tr>
            <td class="check-mail">
                <input type="checkbox" class="i-checks" value="${draft.id}">
            </td>
            <td class="mail-ontact"><a href="###">收件人<input name="new_draft_to" value="${draft.to}"></a></td>
            <td class="mail-subject"><a href="###">主题<input name="new_draft_subject" value="${draft.subject}"></a></td>
            <td class=""><i class="fa fa-paperclip"></i></td>
            <td class="text-right mail-date">${draft.date}</td>
        </tr>
    </tbody>
</table>
<input name="new_draft_content" class="bg-info" height="100px" width="989" value="${draft.htmlContent}">
<button type="button" class="btn btn-info" onclick="draftboxChange()">保存修改</button>
<button type="button" class="btn btn-info" onclick="draftboxChange()">发送</button>

<script>
    function draftboxChange() {
        alert("修改草稿")
        var id = ${draft.id};
        var to = $("[name='new_draft_to']").val();
        var subject = $("[name='new_draft_subject']").val();
        var content =$("[name='new_draft_content']").val();
        content = encodeURIComponent(content);
        var obj = {
            url:"./DraftInboxChangeServlet",
            type:"post",
            data:"id="+id+"&to="+to+"&subject="+subject+"&content="+content,
            success: function (data) {
                if (data=="1"){
                    alert("修改成功");
                    draftboxShow(id);
                }else {
                    alert("修改失败");
                }
            }
        }
        $.ajax(obj);
    }
</script>