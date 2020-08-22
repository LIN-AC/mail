<%--
  Created by IntelliJ IDEA.
  User: Lin
  Date: 2020/8/12
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-hover table-mail">
    <tbody>
        <c:if test="${empty emails}">
            <tr>
                <td colspan="5" style="text-align: center">暂时没有邮件</td>
            </tr>
        </c:if>
        <c:if test="${not empty emails}">
            <c:forEach var="email" items="${emails}">
                <tr>
                    <td class="check-mail">
                        <input type="checkbox" class="i-checks">
                    </td>
                    <td class="mail-ontact"><a href="mail_detail.html">${email.to}</a></td>
                    <td class="mail-subject"><a href="mail_detail.html">${email.subject}</a></td>
                    <td class=""><i class="fa fa-paperclip"></i></td>
                    <td class="text-right mail-date">${email.date}</td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>
<input type="hidden" name="outboxCount" value="${count}">
<input type="hidden" name="outboxPageTotal" value="${pageTotal}">