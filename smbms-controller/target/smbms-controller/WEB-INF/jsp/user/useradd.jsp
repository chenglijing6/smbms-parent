<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/calendar/WdatePicker.js"></script>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 用户添加页面</span>
    </div>
    <!--使用spring的表单控件创建form表单, modelAttribute作用是绑定模型属性 -->
    <fm:form method="post" modelAttribute="user"><br/>
        <fm:errors path="userCode"></fm:errors>
        用户编码:<fm:input path="userCode"/><br/>
        <fm:errors path="userName"></fm:errors>
        用户名称:<fm:input path="userName"/><br/>
        <fm:errors path="userPassword"></fm:errors>
        用户密码:<fm:password path="userPassword"/><br/>
        <fm:errors path="birthday"></fm:errors>
        用户生日:<fm:input path="birthday" Class="Wdate" id="birthday" name="birthday"
                       readonly="readonly" onclick="WdatePicker()"/><br/>
        用户地址:<fm:input path="address"/><br/>
        用户电话:<fm:input path="phone"/><br/>
        用户角色:
        <fm:radiobutton path="userRole" value="1"/>系统管理员
        <fm:radiobutton path="userRole" value="2"/>经理
        <fm:radiobutton path="userRole" value="3" checked="checked"/>普通员工
        <br/>
        <input type="submit" value="保存"/>
    </fm:form>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
