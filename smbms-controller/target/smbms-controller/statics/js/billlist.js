var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj) {
    $.ajax({
        type: "GET",
        url: path + "/jsp/bill.do",
        data: {method: "delbill", billid: obj.attr("billid")},
        dataType: "json",
        success: function (data) {
            if (data.delResult == "true") {//删除成功：移除删除行
                cancleBtn();
                obj.parents("tr").remove();
            } else if (data.delResult == "false") {//删除失败
                //alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                changeDLGContent("对不起，删除订单【" + obj.attr("billcc") + "】失败");
            } else if (data.delResult == "notexist") {
                //alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                changeDLGContent("对不起，订单【" + obj.attr("billcc") + "】不存在");
            }
        },
        error: function (data) {
            alert("对不起，删除失败");
        }
    });
}

function openYesOrNoDLG() {
    $('.zhezhao').css('display', 'block');
    $('#removeBi').fadeIn();
}

function cancleBtn() {
    $('.zhezhao').css('display', 'none');
    $('#removeBi').fadeOut();
}

function changeDLGContent(contentStr) {
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function () {

    $(".viewBill0").on("click", function () {
        alert(34567);
        /*var obj = $(this);
        $.ajax({
            url:path+"/sys/bill/viewBill",
            type:"GET",
            data:{id:obj.attr("billid")},
            dataType:"json",
            success:function(data){
                $("#v_billCode").val(data.billCode);
                $("#v_productName").val(data.productName);
                $("#v_productUnit").val(data.productName);
                $("#v_productCount").val(data.productCount);
                $("#v_totalPrice").val(data.totalPrice);
                $("#v_providerName").val(data.providerName);
                if(data.isPayment==1){
                    $("#v_isPayment").val("未支付");
                }else if(data.isPayment==2){
                    $("#v_isPayment").val("已支付");
                }
                $("#v_creationDate").val(data.creationDate);
            },
            error:function(data){
                alert("error!!!");
            }
        })*/
    });

    $(".modifyBill").on("click", function () {
        var obj = $(this);
        window.location.href = path + "/jsp/bill.do?method=modify&billid=" + obj.attr("billid");
    });
    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteBill(billObj);
    });

    $(".deleteBill").on("click", function () {
        billObj = $(this);
        changeDLGContent("你确定要删除订单【" + billObj.attr("billcc") + "】吗？");
        openYesOrNoDLG();
    });

    /*$(".deleteBill").on("click",function(){
        var obj = $(this);
        if(confirm("你确定要删除订单【"+obj.attr("billcc")+"】吗？")){
            $.ajax({
                type:"GET",
                url:path+"/bill.do",
                data:{method:"delbill",billid:obj.attr("billid")},
                dataType:"json",
                success:function(data){
                    if(data.delResult == "true"){//删除成功：移除删除行
                        alert("删除成功");
                        obj.parents("tr").remove();
                    }else if(data.delResult == "false"){//删除失败
                        alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
                    }else if(data.delResult == "notexist"){
                        alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
                    }
                },
                error:function(data){
                    alert("对不起，删除失败");
                }
            });
        }
    });*/
});