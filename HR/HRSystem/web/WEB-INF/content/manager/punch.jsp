<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/3
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���Ӵ�</title>
    <link href="images/css.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="mgrheader.jsp"%>
<table width="960" align="center"
       background="${pageContext.request.contextPath}/images/bodybg.jpg">
    <tr>
        <td colspan="3"><br/><br/><div class="mytitle">���Ӵ�ϵͳ</div></td>
    </tr>
    <tr>
        <td colspan="3" style="text-align : center;">
            <br/>
            <!-- ��punchIsValidΪ1��3ʱ�����ϰ�� -->
            <s:if test="punchIsValid==1
	|| punchIsValid==3">
                <s:form action="employeeCome" theme="simple">
                    <s:submit value="�ϰ��"/>
                </s:form>
            </s:if>
            <!-- ��punchIsValidΪ2��3ʱ�����°�� -->
            <s:if test="punchIsValid==2
	|| punchIsValid==3">
                <s:form action="employeeLeave" theme="simple">
                    <s:submit value="�°��"/>
                </s:form>
            </s:if>
            <br/>
        </td>
    </tr>
</table>
<%@include file="../footer.jsp"%>
</body>
</html>
