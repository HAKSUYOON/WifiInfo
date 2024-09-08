<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="zerobase.wifi.model.WifiInfoModel"%>
<%@page import="java.util.*" %>
<%@page import="zerobase.wifi.dto.WifiInfoDto" %>
<html>
<head>
	<title>와이파이 정보 구하기</title>
	<link href="res/css/main.css" rel="stylesheet" />
	<script>
		function getvalue(){
			var lat = document.getElementById('inputLat').value;
			var lnt = document.getElementById('inputLnt').value;
			var urll = '?lat=' + lat +'&lnt=' + lnt;
			return urll;
		}
	</script>
</head>
<body>
	<%
		String inputLat = request.getParameter("lat") == null ? "0.0" : request.getParameter("lat");
		String inputLnt = request.getParameter("lnt") == null ? "0.0" : request.getParameter("lnt");
	%>
	<h1>와이파이 정보 구하기</h1>
	<%@include file="inc_menu.jsp"%>
	<div>
		<form>
		<span>LAT:</span>
		<input type="text" id="inputLat" value=<%=inputLat%>>
			
		<span>LNT:</span>
		<input type="text" id="inputLnt" value=<%=inputLnt%>>
	
		<input type="button" value="위치 가져오기"></input>
		<input type="button" value="근처 WIFI 정보 보기" onclick="document.location.href=getvalue()"></input>
		</form>
	</div>
	<br>

	<table class="table-list">
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
			<%
				if(!("0.0").equals(inputLat) && !("0.0").equals(inputLnt)) {
					WifiInfoModel wifiInfoModel = new WifiInfoModel();
					List<WifiInfoDto> list = wifiInfoModel.selectNearWifiInfo(inputLat, inputLnt);
					
					if (list != null) {
						for (WifiInfoDto wifiInfoDto : list) {
			%>				
							<tr>
							<td><%=wifiInfoDto.getDistance()%></td>
							<td><%=wifiInfoDto.getX_swifi_mgr_no()%></td>
							<td><%=wifiInfoDto.getX_swifi_wrdofc()%></td>
							<td><%=wifiInfoDto.getX_swifi_main_nm()%></td>
							<td><%=wifiInfoDto.getX_swifi_adres1()%></td>
							<td><%=wifiInfoDto.getX_swifi_adres2()%></td>
							<td><%=wifiInfoDto.getX_swifi_instl_floor()%>(층)</td>
							<td><%=wifiInfoDto.getX_swifi_instl_ty()%></td>
							<td><%=wifiInfoDto.getX_swifi_instl_mby()%></td>
							<td><%=wifiInfoDto.getX_swifi_svc_se()%></td>
							<td><%=wifiInfoDto.getX_swifi_cmcwr()%></td>
							<td><%=wifiInfoDto.getX_swifi_cnstc_year()%></td>
							<td><%=wifiInfoDto.getX_swifi_inout_door()%></td>
							<td><%=wifiInfoDto.getX_swifi_remars3()%></td>
							<td><%=wifiInfoDto.getLat()%></td>
							<td><%=wifiInfoDto.getLnt()%></td>
							<td><%=wifiInfoDto.getWork_dttm()%></td>
						</tr>
						
						<%}%>
					<%}%>
				<%} else {%>
				<tr>
					<td class="td-center" colspan='17' > 위치 정보를 입력한 후에 조회해 주세요. </td>
				</tr>
				<%}%>			
		</tbody>
	</table>


</body>
</html>