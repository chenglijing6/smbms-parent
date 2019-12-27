<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>供应商管理页面 >> 供应商添加页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post"
              action="${pageContext.request.contextPath}/sys/provider/addsave.html">
            <input type="hidden" name="method" value="add">
            <div>
                <label>供应商编码：</label>
                <input type="text" name="proCode" id="proCode" value="">
                <font color="red"></font>
                <span id="spanTip1"></span>
            </div>
            <div>
                <label>供应商名称：</label>
                <input type="text" name="proName" id="proName" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>联系人：</label>
                <input type="text" name="proContact" id="proContact" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>联系电话：</label>
                <input type="text" name="proPhone" id="proPhone" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>联系地址：</label>
                <input type="text" name="proAddress" id="proAddress" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>传真：</label>
                <input type="text" name="proFax" id="proFax" value="">
                <font color="red"></font>
            </div>
            <div>
                <label>描述：</label>
                <input type="text" name="proDesc" id="proDesc" value="">
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
