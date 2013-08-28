<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
<link href='./includes/sample.css' rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PWP-LDAP Tool</title>

<script type='text/javascript' src='/PWPLDAPTool/dwr/engine.js'></script>
<script type='text/javascript'
	src='/PWPLDAPTool/dwr/interface/UserDtls.js'></script>
<script type='text/javascript' src='/PWPLDAPTool/dwr/util.js'></script>

<script language=JavaScript type=text/javascript>

    //Check whether email address exist in the environment
	function isUserExist() {
    var email = document.form.emailid;
    email.value = email.value.replace(/\s/g, "");
    if(document.form.envList.value=="")
    {
      alert("Please Select Environment");
      document.form.envList.focus();
      return false;
    }
    else if(email.value=="")
    {
      alert("Please entre Email Address");
      document.form.emailid.focus();
      return false;
    }
    return;
    }

</script>


</head>
<body class="MainBody">
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

<form:form name="form" method="post" action="getUserByMail.do"
	commandName="welcome" onsubmit="return isUserExist()">

	<div align="center" class="body">
	<table width="50%" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>
			<table class="TableBigBox" width="300" height="60%" align="center">
				<tr>
					<td width='100%' align='left' class="OrangeHeading" nowrap>Select
					Environment* :</td>

					<td><select name="envList">
						<option value=""
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">Select</option>
						<!-- 
						<option value="mtdevContextSource"
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">MT
						DEV</option>
						
						<option value="gskdevContextSource"
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">GSK
						DEV</option>
						
						<option value="mtstgContextSource"
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">MT
						STG</option>
						 -->
						<option value="mtprdContextSource"
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">MT
						PROD</option>
						<option value="gskprdContextSource"
							style="background-color: #CDC9C8; font-family: Calibri, Helvetica, sans-serif">GSK
						PROD</option>

					</select></td>

				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td>
			<table class="TableBigBox" width="300" height="60%" align="center">
				<tr></tr>
				<tr>
					<td width='100%' align='left' class="OrangeHeading" nowrap>Please
					Enter Email Address :</td>
				</tr>
				<tr>
					<td>
					<table width="100%" border="0" align="center" cellpadding="3"
						cellspacing="2">
						<tr>
							<td align="left" class="txtnormal3">Emailaddress* :</td>
							<td><input name="emailid" type="text" class="form_txtbox"
								maxlength="50" width=25px></td>
						</tr>
						<tr>
							<td colspan="2">
							<P align="center"><input type="submit" value="Submit"
								class="cupid-blue"></P>
							</td>
						</tr>
					</table>
			</table>
			</td>
		</tr>
	</table>
	</div>
</form:form>
</body>
</html>