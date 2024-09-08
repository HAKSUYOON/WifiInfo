<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import ="zerobase.wifi.service.WifiApiComponent" %>
<html>
<head>
	<title>와이파이 정보 구하기</title>
	<link href="res/css/main.css" rel="stylesheet"/>
</head>
<body>
	<%
		int count = WifiApiComponent.getWipiInfoList();
	 %>
	
	 <% if (count > 0) {%>
	 	<div class="result-div">
		<h1 class="api-action"><%=count%>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
		<p class="nothing"></p>
			<a href="index.jsp">홈 으로 가기</a>
		</div>
	 <% }else { %>
		<div class="result-div">
	 	<h1 class="api-action">Error</h1>
	 	<p class="nothing"></p>
			<a href="index.jsp">홈 으로 가기</a>
		</div>
	 <% } %>
	
</body>
</html>