<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href='././includes/sample.css' rel="stylesheet" type="text/css" />
<title>PWP-LDAP Tool</title>
</head>
<body class="MainBody">
<%
	String contextURL = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>

<div><jsp:include flush="true" page="header.jsp"></jsp:include>
<table align="center">
	<tr class="txtnormal" align="left">
		<td align="left" class="OrangeHeading">Welcome ${loggedUser.loggedUser}&nbsp; <a
			href='<c:url value="/j_spring_security_logout"/>'>Logout</a></td>
	</tr>
</table>
<br>
</div>

<div align="center" class="body1">
<table class="TableBigBox" align="center">
	<tr class="tbl_evenrow_center">
		<td align="center" class="txtnormal" nowrap="nowrap">${error.errorMsg}</td>
	</tr>
	<tr></tr>
	<tr align="center">
		<td><input type="button"
			onClick="window.location='<%=contextURL%>/welcome.do'" value="Back"
			class="cupid-blue"></td>
	</tr>
</table>
</div>
</body>
</html>