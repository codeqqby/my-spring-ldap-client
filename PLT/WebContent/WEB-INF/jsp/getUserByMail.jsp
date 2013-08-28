<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script language=JavaScript type=text/javascript>
function enableUser()
{
var env = document.getElementById('env');
var email = document.getElementById('emailid');
    document.form.action = "enableUser.do";
    return true;    
}

function unlockUser()
{
var env = document.getElementById('env');
var email = document.getElementById('emailid');
    document.form.action = "unLockUser.do";
    return true;    
}
</script>
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
		<td align="left" class="OrangeHeading">Welcome
		${loggedUser.loggedUser}&nbsp; <a
			href='<c:url value="/j_spring_security_logout"/>'>Logout</a></td>
	</tr>
</table>
<br>
</div>
<form:form name="form" method="post">
	<div align="center" class="body2">
	<table class="TableBigBoxForEnv" width="50%" border="2" align="center"
		cellpadding="0" cellspacing="0">
		<tr>
			<td width='100%' align='left' class="OrangeHeading" nowrap="nowrap">Environment:
			${user.environment} <input type="hidden" value="${user.environment}"
				name="env" id="env"></td>
		</tr>
	</table>

	<table class="TableBigBox" width="50%" border="2" align="center"
		cellpadding="0" cellspacing="0">
		<tr class="tbl_oddrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Display
			name</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.displayName}">
				--
		</c:if> ${user.displayName}</td>
		</tr>
		<tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Email
			Address</td>
			<td align="left" class="txtnormal" width="15%" id="email"
				nowrap="nowrap"><c:if test="${empty user.emailAddress}">
				--
		</c:if> ${user.emailAddress} <input type="hidden"
				value="${user.emailAddress}" name="emailid" id="emailid"></td>
		</tr>
		<tr class="tbl_oddrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Description</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.description}">
				--
		</c:if> ${user.description}</td>
		</tr>
		<tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">MemberOF</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.memberOfLst}">				--
		</c:if> <c:forEach items="${user.memberOfLst}" var="memberOf">
    		${memberOf}
				<hr />
			</c:forEach></td>
		</tr>

		<tr class="tbl_oddrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Account
			Creation Date</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.createTimeStamp}">
				--
		</c:if> ${user.createTimeStamp}</td>
		</tr>

		<tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Password
			Last Set</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.pwdLastSet}">
				--
		</c:if> ${user.pwdLastSet}</td>
		</tr>

		<tr class="tbl_oddrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Last
			Logon Time</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.lastLogon}">
				--
		</c:if> ${user.lastLogon}</td>
		</tr>

		<tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Last
			Bad Password Date</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.badPasswordTime}">
				--
		</c:if> ${user.badPasswordTime}</td>
		</tr>

		<!-- <tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Phone</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.phone}">
					--
		</c:if> ${user.phone}</td>
		</tr>  -->
		<tr class="tbl_oddrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Bad
			Password Count</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><c:if
				test="${empty user.badPwdCount}">
				--
		</c:if> ${user.badPwdCount}</td>
		</tr>

		<tr class="tbl_evenrow_center">
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap">Account
			Status</td>
			<td align="left" class="txtnormal" width="15%" nowrap="nowrap"><span><c:choose>
				<c:when test="${user.isUserDisabled==''}">--
      </c:when>
				<c:when test="${user.isUserDisabled=='Enabled'}">
					<img src="././images/check.png" alt="Enabled" width="15"
						height="15" align="middle" />  ${user.isUserDisabled}
				</c:when>
				<c:when test="${user.isUserDisabled=='Disabled'}">
					<img src="././images/delete.png" alt="Disabled" width="15"
						height="15" align="middle" /> ${user.isUserDisabled}		
					<input type="submit" onClick="return enableUser();" value="Enable"
						class="cupid-blue-btn">
				</c:when>
			</c:choose> </span>
			<hr>
			<span><c:choose>
				<c:when test="${user.isAccountLocked==''}">--
      </c:when>
				<c:when test="${user.isAccountLocked=='ACTIVE'}">
					<img src="././images/Unlock2.png" alt="Active" width="20"
						height="20" align="middle" />  Not locked
				</c:when>
				<c:when test="${user.isAccountLocked=='LOCKED'}">
					<img src="././images/Lock2.png" alt="Locked" width="20" height="20"
						align="middle" /> Account is locked since - ${user.lockOutTime}
					<input type="submit" onClick="return unlockUser();"
						value="Unlock Account" class="cupid-blue-btn">
				</c:when>
			</c:choose></span></td>
		</tr>

	</table>
	<table>
		<tr></tr>
		<tr>
			<td><input type="button"
				onClick="window.location='<%=contextURL%>/welcome.do'" value="Back"
				class="cupid-blue"></td>
		</tr>
	</table>
	</div>
</form:form>
</body>
</html>
