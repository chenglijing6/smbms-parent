<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>用户管理页面</span>
    </div>

    <script type="text/javascript">
        //导出excel
        function exportXls() {
            $("#myTable").tableExport({"type": "excel", "separator": ";", "escape": "false"});
        }

        //使用工具类导出excel
        function exportExcel() {
            location.href = "${pageContext.request.contextPath}/sys/user/downloadExcel";
        }

        //下载excel模板
        function exportExcel1() {
            location.href = "${pageContext.request.contextPath}/sys/user/downloadExcel1?filename=model.xlsx";

        }

        function importExcel(node) {
            $("#uploadExcel").click;
            var val = node.value;//获取文件名
            alert(val);
            //判空
            if (val == "" || val == null) {
                alert("请您选择文件！");
                return false;
            }
            var tail = val.substr(val.lastIndexOf(".") + 1);
            if (tail != "xls" && tail != "xlsx") {
                alert("请您上传excel文件！");
                return false;
            }
            //满足条件才能提交
            node.parentNode.submit();
        }
    </script>

    <div class="search">
        <form method="post" action="${pageContext.request.contextPath}/sys/user/userlist.html">
            <input name="method" value="query" class="input-text" type="hidden">
            <span>用户名：</span>
            <input name="queryname" class="input-text" type="text" value="${queryname}">
            <span>用户角色：</span>
            <select name="queryuserrole">
                <option value="">--请选择--</option>
                <%--   <c:forEach items="${roleList1}" var="role" varStatus="status">
                       <option value="${role.id}" <c:if test="${role.id == queryUserRole}">selected="selected"</c:if> >${role.roleName}</option>
                   </c:forEach>--%>
                <option value="1" <c:if test="${queryuserrole==1}">selected="selected"</c:if>>系统管理员</option>
                <option value="2" <c:if test="${queryuserrole==2}">selected="selected"</c:if>>经理</option>
                <option value="3" <c:if test="${queryuserrole==3}">selected="selected"</c:if>>普通员工</option>
            </select>
            <input type="hidden" name="pageIndex" value="1"/>
            <input type="submit" value="查 询" id="searchbutton">
            <c:if test="${userSession.userRole != 3 }">
                <a href="${pageContext.request.contextPath}/sys/user/useradd.html">添加用户</a>
            </c:if>
        </form>
    </div>
    <!--用户-->
    <table id="myTable" class="providerTable" cellpadding="0" cellspacing="0">
        <tr class="firstTr">
            <th width="10%">用户编码</th>
            <th width="20%">用户名称</th>
            <th width="10%">性别</th>
            <th width="10%">年龄</th>
            <th width="10%">电话</th>
            <th width="10%">用户角色</th>
            <th width="30%">操作</th>
        </tr>
        <c:forEach var="user" items="${userList }" varStatus="status">
            <tr>
                <td>
                    <span>${user.userCode }</span>
                </td>
                <td>
                    <span>${user.userName }</span>
                </td>
                <td>
							<span>
								<c:if test="${user.gender==1}">男</c:if>
								<c:if test="${user.gender==2}">女</c:if>
							</span>
                </td>
                <td>
                    <span>${user.age}</span>
                </td>
                <td>
                    <span>${user.phone}</span>
                </td>
                <td>
                    <span>${user.userRoleName}</span>
                </td>
                <td>
                    <span><a class="viewUser" href="javascript:;" userid=${user.id} username=${user.userName }><img
                            src="${pageContext.request.contextPath }/statics/images/read.png" alt="查看" title="查看"/></a></span>
                    <c:if test="${userSession.userRole == 1 or userSession.userRole== 2}">
                        <span><a class="modifyUser" href="javascript:;"
                                 userid=${user.id } username=${user.userName }><img
                                src="${pageContext.request.contextPath }/statics/images/xiugai.png" alt="修改"
                                title="修改"/></a></span>
                        <c:if test="${user.userRole == 3}">
                            <span><a class="deleteUser" href="javascript:;"
                                     userid=${user.id } username=${user.userName }><img
                                    src="${pageContext.request.contextPath }/statics/images/schu.png" alt="删除"
                                    title="删除"/></a></span>
                        </c:if>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
    <input type="hidden" name="pageIndex" value="1"/>
    <c:import url="rollpage.jsp">
        <c:param name="totalCount" value="${totalCount}"/>
        <c:param name="currentPageNo" value="${currentPageNo}"/>
        <c:param name="totalPageCount" value="${totalPageCount}"/>
    </c:import>
    <%--利用js直接导出--%>
    <div id="excel" class="right">
        <%--使用tableExport.js导出excel，但是有局限性--%>
        <%-- <input type="button" value="导出excel"  onclick="exportXls()">--%>
        <%--使用工具类导出excel，比较灵活--%>
        <input type="button" value="使用工具类导出excel" onclick="exportExcel()">
        <!-- 导入excel前，先提供excel模板下载 -->
        <input type="button" value="下载excel模板" onclick="exportExcel1()">
        <%--导入excel--%>
        <form action="${pageContext.request.contextPath}/sys/user/uploadExcel.html"
              method="post" enctype="multipart/form-data">
            <input type="file" id="uploadExcel" name="uploadExcel"/>
            <input type="button" value="导入excel" onclick="importExcel(this.previousElementSibling)"/>
        </form>
    </div>


    <div class="providerAdd" id="userView" style="display: none">
        <div>
            <label>用户编码：</label>
            <input type="text" id="v_userCode" value="" readonly="readonly">
        </div>
        <div>
            <label>用户名称：</label>
            <input type="text" id="v_userName" value="" readonly="readonly">
        </div>
        <div>
            <label>用户性别：</label>
            <input type="text" id="v_gender" value="" readonly="readonly">
        </div>
        <div>
            <label>出生日期：</label>
            <input type="text" Class="Wdate" id="v_birthday"
                   readonly="readonly" onclick="WdatePicker();">
        </div>
        <div>
            <label>创建日期：</label>
            <input type="text" Class="Wdate" id="v_creationDate"
                   readonly="readonly" onclick="WdatePicker();">
        </div>
        <div>
            <label>用户电话：</label>
            <input type="text" id="v_phone" value="" readonly="readonly">
        </div>
        <div>
            <label>用户地址：</label>
            <input type="text" id="v_address" value="" readonly="readonly">
        </div>
        <div>
            <label>用户角色：</label>
            <input type="text" id="v_userRoleName" value="" readonly="readonly">
        </div>
    </div>
</div>
</section>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/userlist.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/jquery-1.8.3.min.js"></script>
<script src="${pageContext.request.contextPath}/statics/js/jquery.base64.js"></script>
<script src="${pageContext.request.contextPath}/statics/js/tableExport.js"></script>
<script type="text/javascript">
    $(function () {
        $(".viewUser").on("click", function () {
            //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
            var obj = $(this);
            /*$("#userView").toggle();*/
            if ($("#userView").css("display") == "none") {
                $("#userView").show();
                $.ajax({
                    type: "GET",
                    url: path + "/sys/user/view",
                    data: {id: obj.attr("userid")},
                    dataType: "json",
                    success: function (result) {
                        if (result == "failed") {
                            alert("操作过时！");
                        } else if (result == "nodata") {
                            alert("没有数据!");
                        } else {
                            $("#v_userCode").val(result.userCode);
                            $("#v_userName").val(result.userName);
                            if (result.gender == "1") {
                                $("#v_gender").val("男");
                            } else if (result.gender == "2") {
                                $("#v_gender").val("女");
                            }
                            $("#v_birthday").val(result.birthday);
                            $("#v_creationDate").val(result.creationDate);
                            $("#v_phone").val(result.phone);
                            $("#v_address").val(result.address);
                            $("#v_userRoleName").val(result.userRoleName);
                        }
                    },
                    error: function (data) {
                        alert("error!!!");
                    }
                });
            } else {
                $("#userView").hide();
            }
        });
        $(".deleteUser").on("click", function () {
            var obj = $(this);
            if (confirm("你确定要删除用户【" + obj.attr("username") + "】吗？")) {
                //     window.location.href=path+"/sys/user/deleteUser?id="+obj.attr("userid");
                $.ajax({
                    type: "GET",
                    url: path + "/sys/user/deleteUser",
                    data: {id: obj.attr("userid")},
                    dataType: "json",
                    success: function (flag) {
                        if (flag == true) {//删除成功：移除删除行
                            //  obj.parents("tr").remove();
                            alert("删除成功");
                            window.location.reload();
                        } else if (flag == false) {//删除失败
                            alert("对不起，删除用户【" + obj.attr("username") + "】失败");
                        }/*else if(data.delResult == "notexist"){
						alert("对不起，用户【"+obj.attr("username")+"】不存在");
					}*/
                    },
                    error: function (data) {
                        alert("对不起，删除失败");
                    }
                });
            }
        });
    })
</script>