function deleteHistory(ID) {
	$.ajax({
	           url: "http://localhost:8080/templete/history,jsp",
	           data: {id : ID},
	           success: function (response) {
	               alert(response["삭제 완료"])
	               window.location.reload()
	           },
			   error: function (response) {
				   alert(response["삭제 실패"])
			   }
	       });
}