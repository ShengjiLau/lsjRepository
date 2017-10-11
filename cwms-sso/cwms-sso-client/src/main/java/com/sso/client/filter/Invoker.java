package com.sso.client.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ss on 2017/9/28.
 */
public interface Invoker {

	void invoker(HttpServletRequest request, HttpServletResponse response);

}
