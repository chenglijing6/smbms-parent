var userCode = null;
var userPassword = null;
var addBtn = null;

$(function () {
    userCode = $("#userCode");
    userPassword = $("#userPassword");
    addBtn = $("#add");
    userCode.blur(function () {
        if (userCode.val() == null) {
            validateTip(userCode.next(), {"color": "red"}, "不能为空", false);
        }
    });
    userPassword.blur(function () {
        if (userPassword.val() == null) {
            validateTip(userPassword.next(), {"color": "red"}, "不能为空", false);
        }
    });

    addBtn.bind("click", function () {
        /*if(userCode.attr("validateStatus") != "true"){
            userCode.blur();
        }else if(userPassword.attr("validateStatus") != "true"){
            userPassword.blur();
        }else{
            $("#actionForm").submit();
        }*/
        $("#actionForm").submit();
    });


});