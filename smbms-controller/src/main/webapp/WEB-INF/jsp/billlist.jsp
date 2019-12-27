<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<!--主体内容-->
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>账单管理页面</span>
    </div>
    <div class="search">
        <form method="post" action="${pageContext.request.contextPath}/sys/bill/billlist.html">
            <span>商品名称：</span>
            <input type="text" placeholder="请输入商品的名称" name="productName" value="${productName}"/>
            <span>供应商：</span>
            <select name="providerId" id="providerId">
                <option value="">--请选择--</option>
                <c:if test="${providerList != null }">
                    <c:forEach items="${providerList}" var="provider" varStatus="status">
                        <option value="${provider.id}"
                                <c:if test="${provider.id ==providerId}">selected="selected"</c:if>>${provider.proName}</option>
                    </c:forEach>
                </c:if>
            </select>
            <span>是否付款：</span>
            <select name="isPayment">
                <option value="">--请选择--</option>
                <option value="2" <c:if test="${isPayment==2}">selected="selected"</c:if>>已付款</option>
                <option value="1" <c:if test="${isPayment==1}">selected="selected"</c:if>>未付款</option>
            </select>
            <input type="hidden" name="pageIndex" value="1"/>
            <input type="submit" value="查询"/>
            <c:if test="${userSession.userRole !=3}">
                <a href="${pageContext.request.contextPath}/sys/bill/addBill.html">添加订单</a>
            </c:if>
        </form>
    </div>
    <!--账单表格 样式和供应商公用-->
    <table class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">账单编码</th>
            <th width="20%">商品名称</th>
            <th width="10%">供应商</th>
            <th width="10%">账单金额</th>
            <th width="10%">是否付款</th>
            <th width="10%">创建时间</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="bill" items="${billList }" varStatus="status">
            <tr>
                <td>${bill.billCode}</td>
                <td>${bill.productName}</td>
                <td>${bill.providerName}</td>
                <td>${bill.totalPrice}</td>
                <c:if test="${bill.isPayment==1}">
                    <td>未支付</td>
                </c:if>
                <c:if test="${bill.isPayment==2}">
                    <td>已支付</td>
                </c:if>
                <td><fmt:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/></td>
                <td>
                    <span><a class="viewBill00" href="javascript:;" billid=${bill.id}><img
                            src="${pageContext.request.contextPath}/statics/images/read.png" alt="查看"
                            title="查看"/></a></span>
                    <c:if test="${userSession.userRole == 1 or userSession.userRole==2}">
                        <span><a class="modifyBill00" href="javascript:;" billid=${bill.id}><img
                                src="${pageContext.request.contextPath}/statics/images/xiugai.png" alt="修改" title="修改"/></a></span>
                        <span><a class="deleteBill00" href="javascript:;" billid=${bill.id} ;
                                 isPayment=${bill.isPayment}><img
                                src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除" title="删除"/></a></span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>

    <div class="providerAdd" id="billView" style="display: none">
        <div>
            <label>订单编号：</label>
            <input type="text" id="v_billCode" value="" readonly="readonly"/>
        </div>
        <div>
            <label>商品名称：</label>
            <input type="text" id="v_productName" value="" readonly="readonly"/>
        </div>
        <div>
            <label>商品单位：</label>
            <input type="text" id="v_productUnit" value="" readonly="readonly"/>
        </div>
        <div>
            <label>商品数量：</label>
            <input type="text" id="v_productCount" value="" readonly="readonly"/>
        </div>
        <div>
            <label>总金额：</label>
            <input type="text" id="v_totalPrice" value="" readonly="readonly"/>
            <%--<input type="text" Class="Wdate" id="v_creationDate" readonly="readonly" onclick="WdatePicker();">--%>
        </div>
        <div>
            <label>供应商：</label>
            <input type="text" id="v_providerName" value="" readonly="readonly"/>
        </div>
        <div>
            <label>是否付款：</label>
            <input type="text" id="v_isPayment" value="" readonly="readonly"/>
        </div>
        <div>
            <label>创建时间：</label>
            <input type="text" id="v_creationDate" readonly="readonly" value=""/>
        </div>
    </div>
</div>
</section>

<!--点击删除按钮后弹出的页面-->

<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/billlist.js"></script>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>

<script type="text/javascript">
    $(function () {
        $(".viewBill00").on("click", function () {
            var obj = $(this);
            if ($("#billView").css("display") == "none") {
                $("#billView").show();
                $.ajax({
                    url: path + "/sys/bill/viewBill",
                    type: "GET",
                    data: {id: obj.attr("billid")},
                    dataType: "json",
                    success: function (data) {
                        $("#v_billCode").val(data.billCode);
                        $("#v_productName").val(data.productName);
                        $("#v_productUnit").val(data.productUnit);
                        $("#v_productCount").val(data.productCount);
                        $("#v_totalPrice").val(data.totalPrice);
                        $("#v_providerName").val(data.providerName);
                        if (data.isPayment == 1) {
                            $("#v_isPayment").val("未支付");
                        } else if (data.isPayment == 2) {
                            $("#v_isPayment").val("已支付");
                        }
                        $("#v_creationDate").val(data.creationDate);
                    },
                    error: function (data) {
                        alert("error!!!");
                    }
                })
            } else {
                $("#billView").hide();
            }
        });

        $(".modifyBill00").on("click", function () {
            var obj = $(this);
            window.location.href = path + "/sys/bill/updateBill.html?id=" + obj.attr("billid");
        });

        $(".deleteBill00").on("click", function () {
            var obj = $(this);
            var isPayment = obj.attr("isPayment");
            if (isPayment == 2) {
                if (confirm("你确定要删除该账单吗？")) {
                    //  window.location.href=path+"/sys/bill/deleteBill.html?id="+obj.attr("billid");
                    $.ajax({
                        type: "GET",
                        url: path + "/sys/bill/deletebill",
                        data: {billid: obj.attr("billid")},
                        dataType: "json",
                        success: function (data) {
                            if (data == true) {//删除成功：移除删除行
                                alert("删除成功");
                                // obj.parents("tr").remove();
                                window.location.reload();
                            } else if (data == false) {//删除失败
                                alert("对不起，删除该账单失败");
                            }/*else if(data.delResult == "notexist"){
						alert("对不起，用户【"+obj.attr("username")+"】不存在");
					}*/
                        },
                        error: function (data) {
                            alert("对不起，删除失败");
                        }
                    })
                }
            } else if (isPayment == 1) {
                alert("对不起，该账单未付款，不能删除！");
            }
        })
    });
</script>

