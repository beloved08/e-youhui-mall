package com.eyh.mall.filter;

import com.alibaba.fastjson.JSON;
import com.eyh.mall.entity.common.Result;
import com.eyh.mall.entity.common.TransactionCode;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 李平
 * @Date 2023-1-27
 */
public class CORSAuthenticationFilter extends FormAuthenticationFilter {

    public CORSAuthenticationFilter() {
        super();
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            return true;
        }
        return super.isAccessAllowed(request,response,mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        writer.write(JSON.toJSONString(Result.err(TransactionCode.NO_LOGIN_CODE,TransactionCode.NO_LOGIN_MSG)));
        writer.close();
        return false;
    }
}
