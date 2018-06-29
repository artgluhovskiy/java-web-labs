<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html lang="en">
    <head>
        <title>Java Web Project</title>
        <%@ include file="resources/headerResources.jsp" %>
    </head>
    <body onload="disconnect();">

        <tiles:insertAttribute name="header"/>

        <div class="container">
            <div class="row">
                <tiles:insertAttribute name="body"/>
            </div>
        </div>

        <tiles:insertAttribute name="footer"/>
        <tiles:insertAttribute name="resources"/>

    </body>
</html>
