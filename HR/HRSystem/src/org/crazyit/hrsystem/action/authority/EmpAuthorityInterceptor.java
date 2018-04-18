package org.crazyit.hrsystem.action.authority;

import com.opensymphony.xwork2.*;

import com.opensymphony.xwork2.interceptor.*;

import org.crazyit.hrsystem.action.WebConstant;

public class EmpAuthorityInterceptor extends AbstractInterceptor
{
	public String intercept(ActionInvocation invocation)
		throws Exception
	{
		System.out.println("EmpAuthorityInterceptor...");

		ActionContext ctx = ActionContext.getContext();

		String level = (String)ctx.getSession()
			.get(WebConstant.LEVEL);

		if (level != null && (level.equals(WebConstant.EMP_LEVEL)
			|| level.equals(WebConstant.MGR_LEVEL)))
		{
			return invocation.invoke();
		}
		return Action.LOGIN;
	}
}