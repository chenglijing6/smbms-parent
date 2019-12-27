<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/statics/css/style.css"/>
</head>
<body class="login_bg">
<section class="loginBox">
    <header class="loginHeader">
        <h1>超市订单管理系统</h1>
    </header>
    <section class="loginCont">
        <form class="loginForm" action="${pageContext.request.contextPath }/dologin.html" name="actionForm"
              id="actionForm" method="post">
            <div class="info">${error}</div>
            <div class="inputbox">
                <label>用户名：</label>
                <input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名"/>
            </div>
            <div class="inputbox">
                <label>密码：</label>
                <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码"/>
            </div>
            <div class="subBtn">
                <%-- <input type="button" name="add" id="add" value="登录" >--%>
                <input type="submit" value="登录"/>
                <%--   <input id="btn" type="button" name="button" value="登录"/>--%>
                <input type="reset" value="重置"/>
            </div>
        </form>
    </section>
</section>
<script src="${pageContext.request.contextPath }/statics/js/jquery-3.4.1.js"></script>
<script src="${pageContext.request.contextPath }/statics/js/jquery.validate.js"></script>
<script src="${pageContext.request.contextPath }/statics/js/messages_zh.js"></script>
<script type="text/javascript">
    $(function () {
        $("#loginForm").validate({
            rules: {
                userCode: {
                    required: true
                },
                userPassword: {
                    required: true,
                }
            },
            messages: {
                userCode: {
                    required: "不能为空",

                },
                userPassword: {
                    required: "不能为空",
                    digits: "必须是数字",
                    minlength: "长度大于等于6",
                }
            }
        });

    });
</script>
</body>
</html>

