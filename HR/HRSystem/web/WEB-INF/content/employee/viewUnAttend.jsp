<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/16
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=gb2312" language="java" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>查看自己的非正常出勤</title>
</head>
<body>
<%@include file="../header.jsp"%>
<%@include file="empheader.jsp"%>
<table width="960" align="center"
       background="${pageContext.request.contextPath}/images/bodybg.jpg">
    <tr>
        <td>
            <table width="80%" border="0" align="center" bgcolor="#cccccc">
                <tr bgcolor="#e1e1e1" >
                    <td colspan="4" ><div class="mytitle">当前用户：<s:property value="#session.user"/></div></td>
                </tr>
                <tr bgcolor="#e1e1e1" >
                    <td colspan="4" >您只能查看最近三天的非正常打卡，如有异议，请立即向经理申请</td>
                </tr>
                <tr class="pt11" height="45">
                    <td width="29%"><b>打卡日期</b></td>
                    <td width="20%"><b>异动名称</b></td>
                    <td width="26%"><b>打卡时间</b></td>
                    <td width="25%">&nbsp;</td>
                </tr>
                <!-- 迭代输出ValueStack中的books对象，其中status是迭代的序号 -->
                <s:iterator value="unAttend" status="index">
                    <%--index.odd判断当前索引是否偶数--%>
                    <s:if test="#index.odd == true">
                        <tr style="background-color:#dddddd" class="pt11" height="32">
                    </s:if>
                    <s:else>
                        <tr class="pt11" height="32">
                    </s:else>
                    <td><s:property value="dutyDay"/></td>
                    <td><s:property value="unType"/></td>
                    <td><s:property value="time"/></td>
                    <td><a href='appChange.action?attid=<s:property value="id"/>'>申请改变</a></td>
                    </tr>
                </s:iterator>
            </table>
        </td>
    </tr>
</table>
<%@include file="../footer.jsp"%>
</body>
</html>
