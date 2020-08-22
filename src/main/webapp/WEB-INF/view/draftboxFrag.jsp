<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/14
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-hover table-mail">
    <tbody>
    <c:if test="${empty drafts}">
        <tr>
            <td colspan="5" style="text-align: center">暂时没有草稿</td>
        </tr>
    </c:if>
    <c:if test="${not empty drafts}">
        <c:forEach var="draft" items="${drafts}">
            <tr>
                <td class="check-mail">
                    <input type="checkbox" name="draftboxCheckbox" class="i-checks" value="${draft.id}">
                </td>
                <td class="mail-ontact" onclick="draftboxShow(${draft.id})"><a href="###">${draft.to}</a></td>
                <td class="mail-subject" onclick="draftboxShow(${draft.id})"><a href="###">${draft.subject}</a></td>
                <td class=""><i class="fa fa-paperclip"></i></td>
                <td class="text-right mail-date">${draft.date}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
<input type="hidden" name="draftboxCount" value="${count}">
<input type="hidden" name="draftboxPageTotal" value="${pageTotal}">
<script>
    function draftboxShow(id) {
        var obj = {
            url:"./DraftInboxShowServlet",
            data:"id="+id,
            success: function (data) {
                console.log(data)
                $("#draftbox_list").html(data);
            }
        }
        $.ajax(obj);
    }
</script>