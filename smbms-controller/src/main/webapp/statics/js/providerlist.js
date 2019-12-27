var providerObj;

//供应商管理页面上点击删除按钮弹出删除框(providerlist.jsp)
/*function deleteProvider(obj){
	$.ajax({
		type:"GET",
		url:path+"/jsp/provider.do",
		data:{method:"delprovider",proid:obj.attr("proid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
				changeDLGContent("对不起，删除供应商【"+obj.attr("proname")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
				changeDLGContent("对不起，供应商【"+obj.attr("proname")+"】不存在");
			}else{
				//alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}*/

function openYesOrNoDLG() {
    $('.zhezhao').css('display', 'block');
    $('#removeProv').fadeIn();
}

function cancleBtn() {
    $('.zhezhao').css('display', 'none');
    $('#removeProv').fadeOut();
}

function changeDLGContent(contentStr) {
    var p = $(".removeMain").find("p");
    p.html(contentStr);
}

$(function () {
    $(".viewProvider0").on("click", function () {
        var obj = $(this);
        if ($("#providerView").css("display") == "none") {
            $("#providerView").show();
            $.ajax({
                type: "GET",
                url: path + "/sys/provider/viewProvider",
                data: {id: obj.attr("id")},
                dataType: "json",
                success: function (result) {
                    $("#v_proCode").val(result.proCode);
                    $("#v_proName").val(result.proName);
                    $("#v_proContact").val(result.proContact);
                    $("#v_proPhone").val(result.proPhone);
                    $("#v_proFax").val(result.proFax);
                    $("#v_proDesc").val(result.proDesc);
                },
                error: function (result) {
                    alert("查看失败！");
                }
            })
        } else {
            $("#providerView").hide();
        }
    });

    $(".modifyProvider0").on("click", function () {
        var obj = $(this);
        window.location.href = path + "/sys/provider/updateprovider.html?id=" + obj.attr("id");
    });

    $('#no').click(function () {
        cancleBtn();
    });

    $('#yes').click(function () {
        deleteProvider(providerObj);
    });

    /*	$(".deleteProvider").on("click",function(){
            providerObj = $(this);
            changeDLGContent("你确定要删除供应商【"+providerObj.attr("proname")+"】吗？");
            openYesOrNoDLG();
        });*/

    $(".deleteProvider0").on("click", function () {
        var obj = $(this);
        /*	var obj1=$(this.nextElementSibling);*/
        /*	var message=obj1.value;*/
        if (confirm("你确定要删除供应商【" + obj.attr("proname") + "】吗？")) {
            /*window.location.href = path + "/sys/provider/deleteprovider.html?id=" + obj.attr("providerid");*/
            /*if(message !=null){
                alert(message);
            }*/
            $.ajax({
                type: "POST",
                url: path + "/sys/provider/deleteprovider",
                data: {providerId: obj.attr("providerId")},
                dataType: "json",
                success: function (data) {
                    if (data.message == "YES") {//删除成功：移除删除行
                        //obj.parents("tr").remove();
                        alert("删除成功");
                        window.location.reload();
                    } else if (data.message == "NO") {//删除失败
                        alert("对不起，删除供应商【" + obj.attr("proname") + "】失败");
                    } else if (data.message == "SORRY") {
                        alert("对不起，该供应商有未支付订单，不能删除");
                    }
                },
                error: function (data) {
                    alert("对不起，删除失败");
                }
            });
        }
    });
});