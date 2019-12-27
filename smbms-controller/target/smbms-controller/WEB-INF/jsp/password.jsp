<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/WEB-INF/jsp/common/head.jsp" %>

<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>密码修改页面</span>
    </div>
    <div class="password">
        <div class="providerAdd">
            <form id="updatePwd" action="${pageContext.request.contextPath}/sys/user/savepwd.html" method="post">
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="">
                    <label for="oldPassword">旧密码：</label>
                    <input type="hidden" name="id" id="id" value="${userSession.id}"/>
                    <input type="password" name="oldPassword" id="oldPassword" value="" required/>
                    <span></span>
                </div>
                <div>
                    <label for="newPassword">新密码：</label>
                    <input type="password" name="newPassword" id="newPassword" value="" required/>
                    <span>*请输入新密码</span>
                </div>
                <div>
                    <label for="reNewPassword">确认新密码：</label>
                    <input type="password" name="reNewPassword" id="reNewPassword" value="" required/>
                    <span>*请输入新确认密码，保证和新密码一致</span>
                </div>
                <div class="providerAddBtn">
                    <!--<a href="#">保存</a>-->
                    <input type="submit" value="保存"/>
                    <%-- <input type="button" value="保存" onclick="history.back(-1)"/>--%>
                </div>
            </form>
        </div>
    </div>
</div>
</section>
<script src="${pageContext.request.contextPath}/statics/js/time.js"></script>
<%--<script src="${pageContext.request.contextPath}/statics/js/pwdmodify.js"></script>--%>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<footer class="footer">
    超市管理系统
</footer>
<script type="text/javascript">
    $(function () {
        var oldpassword=$("#oldPassword");
        var id=$("#id").val();
        oldpassword.on("blur",function(){
        $.ajax({
            type:"GET",
            url:path+"/sys/user/pwdmodify.html",
            data:{oldpassword:oldpassword.val(),id:id},
            dataType:"json",
            success:function(data){
                var data=JSON.parse(data);
                alert(data.message);
                if(data.message == "true"){//旧密码正确
                    oldpassword.next().html("<span style='color:green'>密码正确</span>");
                  //  validateTip(oldpassword.next(),{"color":"green"},imgYes,true);
                }else if(data.message == "false"){//旧密码输入不正确
                    oldpassword.next().html("<span style='color:red'>原密码输入不正确</span>");
                   // validateTip(oldpassword.next(),{"color":"red"},imgNo + " 原密码输入不正确",false);
                /*}else if(data.result == "sessionerror"){//当前用户session过期，请重新登录
                    validateTip(oldpassword.next(),{"color":"red"},imgNo + " 当前用户session过期，请重新登录",false);*/
                }else if(data.message == "error"){//旧密码输入为空
                    oldpassword.next().html("<span style='color:red'>请输入旧密码</span>");
                   // validateTip(oldpassword.next(),{"color":"red"},imgNo + " 请输入旧密码",false);
                }
            },
            error:function(data){
                //请求出错
                oldpassword.next().html("<span style='color:red'>请求错误</span>");
                //validateTip(oldpassword.next(),{"color":"red"},imgNo + " 请求错误",false);
            }
        });
    }).on("focus",function(){
        validateTip(oldpassword.next(),{"color":"#666666"},"* 请输入原密码",false);
    });

        var newpassword = $("#newPassword");
        newpassword.on("focus", function () {
            validateTip(newpassword.next(), {"color": "#666666"}, "* 密码长度必须是大于6小于10", false);
        }).on("blur", function () {
            if (newpassword.val() != null && newpassword.val().length >= 6
                && newpassword.val().length <= 10) {
                validateTip(newpassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(newpassword.next(), {"color": "red"}, imgNo + " 密码输入不符合规范，请重新输入", false);
            }
        });

        var rnewpassword = $("#reNewPassword");
        rnewpassword.on("focus", function () {
            validateTip(rnewpassword.next(), {"color": "#666666"}, "* 请输入与上面一致的密码", false);
        }).on("blur", function () {
            if (rnewpassword.val() != null && rnewpassword.val().length > 6
                && rnewpassword.val().length < 20 && newpassword.val() == rnewpassword.val()) {
                validateTip(rnewpassword.next(), {"color": "green"}, imgYes, true);
            } else {
                validateTip(rnewpassword.next(), {"color": "red"}, imgNo + " 两次密码输入不一致，请重新输入", false);
            }
        });

    })


</script>