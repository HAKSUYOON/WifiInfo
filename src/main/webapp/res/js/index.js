/*
function getLocation() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(showPosition);
	}
}

function showPosition(position) {
	console.log(position.coords.latitude);
	console.log(position.coords.longitude);

	document.getElementById('inputLat').value = position.coords.latitude;
	document.getElementById('inputLnt').value = position.coords.longitude;
}

window.addEventListener('load', function (event) {

	document.getElementById('getPositionButton').addEventListener('click', function (event) {
		getLocation();
	});

});
http프로토콜에서는 geolocation을 지원하지 않는다. https프로토콜로 변경 필요*/

let selectNearWifiInfoBtn = document.getElementById("selectNearWifiInfoBtn");

window.onload = function() {
	inputLat = document.getElementById("inputLat").value;
	inputLnt = document.getElementById("inputLnt").value;
}

selectNearWifiInfoBtn.addEventListener('click', function() {
	let paramLat = document.getElementById("inputLat").value;
	let paramLnt = document.getElementById("inputLnt").value;
	
	if (paramLat != "" || paramLnt != "") {
		location.assign("http://localhost:8080/templete/index.jsp?lat=" + paramLat + "&lnt=" + paramLnt);
	} else {
		alert("위치 정보를 입력해주세요.")
	}
})