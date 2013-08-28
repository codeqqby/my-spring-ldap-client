<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<SCRIPT language=JavaScript type=text/javascript>

	//test the username element of a form
	function isValidUsername(form){
	var username = form.j_username;
	var password = form.j_password;
    username.value = username.value.replace(/\s/g, "");
    password.value = password.value.replace(/\s/g, "");
	if (username.value == "") {
        alert('Please enter Username.');
        form.j_username.focus();
        return false;
	}else if (password.value == ""){
	alert('Please enter Password.');
        form.j_password.focus();
        return false;
        }
	return true;
	}
	
	//perform a login for the LoginForm
	function doLogin(form) {
    //test username
    if (!isValidUsername(form)) {
        return;
    }
    form.submit();
    }
	</SCRIPT>
<body onload="document.getElementById('username').focus();">
<div><jsp:include flush="true" page="header.jsp"></jsp:include></div>
<form name="LoginForm" method="POST"
	action="<c:url value='j_spring_security_check' />">
<div align="center" class="body">
<table>
	<TR>
		<TD id=signIn vAlign=top width="90%" class="LoginBox" align="left"><SPAN
			class=signinhead><img src='images/signin.png' alt="Sign In"
			align="top" width="18" height="18"> Sign In</SPAN><BR>
		<TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
			<TBODY>
				<TR>
					<TD>
					<TABLE border=0 cellSpacing=2 cellPadding=4 width="100%">
						<TBODY>
							<TR>
								<TD height=25 colSpan=2 noWrap><SPAN class=txtnormal>Please
								enter your user name and password.</SPAN></TD>
							</TR>
							<tr>
								<td align=right valign="middle" class="txtnormal">Username&nbsp;&nbsp;</td>
								<td height="27" align=left valign="top" class="txtnormal">
								<input type="text" name="j_username" size="20" maxlength="80"
									id="username"
									value='<c:if test="${not empty param.error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' /></td>
							</tr>
							<tr>
								<td align=right valign="middle" height="27"><span
									class="txtnormal">Password&nbsp;&nbsp;</span></td>
								<td align=left valign="top" class="txtnormal" height="27">
								<input type="password" name="j_password" value="" size="20"
									maxlength="255" id="password"></td>
							</tr>
							<tr>
								<td align= "center" colspan="2"><a
									href="javascript:doLogin(document.LoginForm);"
									onMouseOver="self.status='Press to Sign In'; return true">
								<img src='./images/login.png' height="24" border=0 alt="Sign In"
									align="middle"> </a></td>
							</tr>

							<c:if test="${not empty param['error']}">
								<tr>
									<td colspan="2" class="invalidcredentials" align="left">Your
									login attempt was not successful, please try again.<br />
									<b>Reason:</b><c:out
										value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.</td>
								</tr>
							</c:if>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</TD>

	</TR>
</table>
</div>
</form>

</body>
</html>