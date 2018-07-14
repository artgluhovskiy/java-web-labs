<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<div class="item-container login">
    <h3 class="item-header">Sign in:</h3>
    <div class="row">
        <div class="col">
            <form action="${pageContext.request.contextPath}/login.do" method="post">
                Enter your login: <input type="text" name="login">
                <button type="submit" class="btn btn-primary btn-md">Submit</button>
            </form>
        </div>
    </div>
</div>