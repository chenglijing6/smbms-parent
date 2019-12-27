<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面 >> 账单修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post"
              action="${pageContext.request.contextPath}/sys/bill/saveBill.html">
            <input type="hidden" name="id" value="${bill.id}">
            <div class="">
                <label>订单编码：</label>
                <input type="text" name="billCode" id="billCode" value="${bill.billCode}" readonly/>
                <span>*请输入订单编码</span>
            </div>
            <div>
                <label>商品名称：</label>
                <input type="text" name="productName" id="productName" value="${bill.productName}" required/>
                <span>*请输入商品名称</span>
            </div>
            <div>
                <label>商品描述：</label>
                <input type="text" name="productDesc" id="productDesc" value="${bill.productDesc}" required/>
                <span>*请输入商品单位</span>
            </div>
            <div>
                <label>商品单位：</label>
                <input type="text" name="productUnit" id="productUnit" value="${bill.productUnit}" required/>
                <span>*请输入商品单位</span>
            </div>
            <div>
                <label>商品数量：</label>
                <input type="text" name="productCount" id="productCount" value="${bill.productCount}" required/>
                <span>*请输入大于0的正自然数，小数点后保留2位</span>
            </div>
            <div>
                <label>总金额：</label>
                <input type="text" name="totalPrice" id="totalPrice" value="${bill.totalPrice}" required/>
                <span>*请输入大于0的正自然数，小数点后保留2位</span>
            </div>
            <div>
                <label>供应商：</label>
                <select name="providerId">
                    <option value="">--请选择--</option>
                    <c:if test="${providerList != null }">
                        <c:forEach items="${providerList}" var="provider" varStatus="status">
                            <option value="${status.count}"
                                    <c:if test="${status.count==bill.providerId}">selected="selected"</c:if> >${provider.proName}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <span>*请选择供应商</span>
            </div>
            <div>
                <label>是否付款：</label>
                <select name="isPayment">
                    <option>--请选择</option>
                    <option value="1"
                            <c:if test="${bill.isPayment==1}">selected="selected"</c:if> >未支付
                    </option>
                    <option value="2" <c:if test="${bill.isPayment==2}">selected="selected"</c:if>>已支付</option>
                </select>
            </div>
            <div class="providerAddBtn">
                <input type="submit" value="保存">
                <input type="reset" id="back" name="back" value="返回">
            </div>
        </form>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/useradd.js"></script>
