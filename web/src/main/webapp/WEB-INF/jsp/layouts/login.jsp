<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${empty user}">
        <%@include file="login-input.jsp"%>
    </c:when>
    <c:otherwise>
        <div class="col">
            <ul class="list-group">
                <li class="list-group-item"><a href="${pageContext.request.contextPath}/chat.do">Chat (Websocket API)</a></li>
                <li class="list-group-item">Application item 1</li>
                <li class="list-group-item">Application item 2</li>
            </ul>
        </div>
    </c:otherwise>
</c:choose>

