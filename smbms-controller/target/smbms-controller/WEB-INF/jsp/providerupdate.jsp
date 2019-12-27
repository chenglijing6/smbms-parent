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
              action="${pageContext.request.contextPath}/sys/provider/updatesave.html">
            <input type="hidden" name="id" value="${provider.id}">
            <div>
                <label>供应商编码：</label>
                <input type="text" name="proCode" id="proCode" value="${provider.proCode}">
                <font color="red"></font>
                <span id="spanTip1"></span>
            </div>
            <div>
                <label>供应商名称：</label>
                <input type="text" name="proName" id="proName" value="${provider.proName}">
                <font color="red"></font>
            </div>
            <div>
                <label>联系人：</label>
                <input type="text" name="proContact" id="proContact" value="${provider.proContact}">
                <font color="red"></font>
            </div>
            <div>
                <label>联系电话：</label>
                <input type="text" name="proPhone" id="proPhone" value="${provider.proPhone}">
                <font color="red"></font>
            </div>
            <div>
                <label>联系地址：</label>
                <input type="text" name="proAddress" id="proAddress" value="${provider.proAddress}">
                <font color="red"></font>
            </div>
            <div>
                <label>传真：</label>
                <input type="text" name="proFax" id="proFax" value="${provider.proFax}">
                <font color="red"></font>
            </div>
            <div>
                <label>描述：</label>
                <input type="text" name="proDesc" id="proDesc" value="${provider.proDesc}">
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <input type="submit" value="保存">
                <input type="reset" value="返回">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/provideradd.js"></script>
