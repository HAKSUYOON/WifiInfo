<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="zerobase.wifi.dto.WifiInfoDto" %>
<%@ page import="zerobase.wifi.dto.PosHistoryDto" %>
<%@ page import="zerobase.wifi.model.WifiInfoModel" %>
<%@ page import="zerobase.wifi.model.PosHistoryModel" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>위치 히스토리 목록</title>
	<link href="res/css/main.css" rel="stylesheet" />
	<script src="res/js/history.js"></script>	
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<%@include file="inc_menu.jsp"%>		
	<%
		PosHistoryModel posHistoryModel = new PosHistoryModel();
		List<PosHistoryDto> historyList = posHistoryModel.selectHistory();
		
		String paramID = request.getParameter("id");
		if (paramID != null) {
			posHistoryModel.deleteHistory(paramID);
		}
	%>
	
	<table class="table-list">
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<% if (!historyList.isEmpty()) {%>
				<%for (PosHistoryDto posHistoryDto : historyList) {%>
					<tr>
						<td><%=posHistoryDto.getId()%></td>
						<td><%=posHistoryDto.getInsertLat()%></td>
						<td><%=posHistoryDto.getInsertLnt()%></td>
						<td><%=posHistoryDto.getViewDate()%></td>
						<td><button onclick="deleteHistory(<%=posHistoryDto.getId()%>)">삭제</button></td>
					</tr>
				<%}%>
			<%} else {%>
					<tr>
						<td class="td-center" colspan='5'>조회하신 이력이 없습니다.</td>
					</tr>
				<%}%>			
		</tbody>
	</table>
	
</body>
</html>