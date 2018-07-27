
$().ready( function() {	
	var $allCheck = $("#allCheck");// 全选复选框
	var $idsCheck = $('[name=ids]:checkbox');// ID复选框
	var $deleteButton = $("#deleteButton");// 删除按钮
	var $searchButton =  $("#searchButton");// 查询按钮
	
	var $listForm = $("#listForm");// 列表表单
	var $pageNumber = $("#pageNumber");// 当前页码

	 // 全选  
     $allCheck.click(function(){
    	 var isChecked = $idsCheck.is(":checked"); 
    	 if (isChecked == false) {
 			$idsCheck.prop('checked',true);
 			$deleteButton.prop("disabled", false);
 		} else {
 			$idsCheck.prop('checked',false);
 			$deleteButton.prop("disabled", true);
 		}
     });  

   // 无复选框被选中时,删除按钮不可用
 	$idsCheck.click( function() {
 		var $idsChecked = $("[name='ids']:checked");
 		if ($idsChecked.size() > 0) {
 			$deleteButton.prop("disabled", false);
 		} else {
 			$deleteButton.prop("disabled", true)
 		}
 	});
 	
 	// 查找
	$searchButton.click( function() {
		if($pageNumber){//如果有分页插件则重置
			$pageNumber.val("1");
		}
		$listForm.submit();
	});
		
});
	  
// 批量删除
function deleteAll(url) {
  var $idsCheckedCheck = $("[name='ids']:checked");
  var $deleteButton = $("#deleteButton");// 删除按钮
  Modal.register(['confirm','alert']);
  Modal.confirm("您确定要删除吗？",{
    relatedTarget: this,
	onConfirm: function(options) {
	  $.ajax({
	    url: url,
		data: $idsCheckedCheck.serialize(),
		dataType: "json",
		async: false,
		beforeSend: function(data) {
		  $deleteButton.prop("disabled", true);
		},
		success: function(data) {
		  if (data.status == "success") {
			$idsCheckedCheck.parent().parent().remove();
		  }else{
			$deleteButton.prop("disabled", false);
		  }
		  Modal.alert(data.message);
		}
	  });
	}
  });
} 

//单行删除
function deleteOne(url, index) {
  Modal.register(['confirm','alert']);
  Modal.confirm("您确定要删除吗？",{
    relatedTarget: this,
	onConfirm: function(options) {
      $.ajax({
    	url: url,
		dataType: "json",
		async: false,
		success: function(data) {
		  if (data.status == "success") {
			  $("#listTr" + index).remove();
		  }
		  Modal.alert(data.message);
		}
	  });
	}
  });
}

//更新
function updateOne(url) {
  Modal.register(['confirm','alert']);
  Modal.confirm("您确定要更新吗？",{
    relatedTarget: this,
	onConfirm: function(options) {
      $.ajax({
    	url: url,
		dataType: "json",
		async: false,
		success: function(data) {
		  Modal.alert(data.message);
		  window.history.back(-1); 
		}
	  });
	}
  });
}

function returnMonery(url) {
	  Modal.register(['confirm','alert']);
	  Modal.confirm("您确定要退款吗？",{
	    relatedTarget: this,
		onConfirm: function(options) {
	      $.ajax({
	    	url: url,
			dataType: "json",
			async: false,
			success: function(data) {
			  Modal.alert(data.message);
			  window.location.reload(); 
			}
		  });
		}
	  });
	}
function orderDetail(url){
	  var obj = window; 
	  var iWidth=1200; //弹出窗口的宽度;
	  var iHeight=600; //弹出窗口的高度;
	  var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
	  var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	  myWindow =window.open(url,"Child","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft+",alwaysRaised=yes,z-look=yes,location=yes"); 
	  myWindow.focus();	
}