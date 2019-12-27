<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post"
              enctype="multipart/form-data" action="${pageContext.request.contextPath}/sys/user/addsave.html">
            <input type="hidden" name="method" value="add">
            <div>
                <label for="userCode">用户编码：</label>
                <input type="text" name="userCode" id="userCode" value="">
                <font color="red"></font>
                <span id="spanTip1"></span>
            </div>
            <div>
                <label for="userName">用户名称：</label>
                <input type="text" name="userName" id="userName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="userPassword">用户密码：</label>
                <input type="password" name="userPassword" id="userPassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="ruserPassword">确认密码：</label>
                <input type="password" name="ruserPassword" id="ruserPassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>用户性别：</label>
                <select name="gender" id="gender">
                    <option value="1" selected="selected">男</option>
                    <option value="2">女</option>
                </select>
            </div>
            <div>
                <label for="birthday">出生日期：</label>
                <input type="text" Class="Wdate" id="birthday" name="birthday"
                       readonly="readonly" onclick="WdatePicker();">
                <font color="red"></font>
            </div>
            <div>
                <label for="phone">用户电话：</label>
                <input type="text" name="phone" id="phone" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="address">用户地址：</label>
                <input name="address" id="address" value="">
            </div>
            <div>
                <label>用户角色：</label>
                <!-- 列出所有的角色分类 -->
                <!-- 异步先注释掉 -->
                <!-- <select name="userRole" id="userRole"></select> -->
                <select name="userRole" id="userRole">
                    <option value="1">系统管理员</option>
                    <option value="2">经理</option>
                    <option value="3" selected="selected">普通用户</option>
                </select>
                <font color="red"></font>
            </div>
            <div>
                <!-- 优化表单控件 -->
                <input type="hidden" id="errorinfo" value="${uploadfileError }"/>
                <label for="a_idPicPath">证件照：</label>
                <input type="file" name="attachs" id="a_idPicPath"/>
                <span id="spanTips" style="color:red"></span>
            </div>
            <div>
                <!-- 多文件上传 -->
                <input type="hidden" id="errorinfo_wp" value="${uploadwpError }"/>
                <label for="a_idPicPath">工作照：</label>
                <input type="file" name="attachs" id="a_workPicPath"/>
                <span id="spanTips_wp" style="color:red"></span>
            </div>
            <div class="providerAddBtn">
                <input type="button" name="add" id="add" value="保存">
                <input type="button" id="back" name="back" value="返回">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
<script type="text/javascript">

    $("#userCode").bind("blur", function () {
        //ajax后台验证--userCode是否已存在
        var userCode = $("#userCode").val();
        /* alert(9999);*/
        $.ajax({
            type: "GET",//请求类型
            url: path + "/sys/user/ucexist.html",//请求的url
            data: {"userCode": userCode},//请求参数,json格式
            dataType: "json",//ajax接口（请求url）返回的数据类型，json格式
            success: function (data) {//data：返回数据（json对象）
                /* alert(data.userCode);*/
                var msg = eval("(" + data + ")");
                if (msg.userCode == "exist") {//账号已存在，错误提示
                    validateTip($("#spanTip1"), {"color": "red"}, imgNo + " 该用户账号已存在", false);
                } else if (msg.userCode == "noexist") {//账号可用，正确提示
                    validateTip($("#spanTip1"), {"color": "green"}, imgYes + " 该账号可以使用", true);
                }
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                validateTip(userCode.next(), {"color": "red"}, imgNo + " 您访问的页面不存在", false);
            }
        });
    })/*.bind("focus",function(){
		//显示友情提示
		validateTip(userCode.next(),{"color":"#666666"},"* 用户编码是您登录系统的账号",false);
	}).focus();*/


</script>
