package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.permission.dao.PermissionMapper;
import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.service.UserPermissionService;
import com.lcdt.userinfo.model.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/11/23.
 */
@RequestMapping("/debug")
@RestController
public class DebugController implements ApplicationContextAware {

	@Autowired
	UserPermissionService permissionService;
	@Autowired
	PermissionMapper mapper;
	ExpressionParser expressionParser = new SpelExpressionParser();
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	@GetMapping("/a")
	@PreAuthorize("hasAnyAuthority('update_company_info')")
	public String nullException(){
		String a = null;
		System.out.println(1/0);
		return a;
	}

	@RequestMapping("/logininuser")
	public String allLoginUser(HttpSession session,Long userId,Long companyId){
		session.setAttribute("demo","demo");
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();

		//接受到通知需要更新时
		return JSON.toJSONString(allPrincipals);
	}


	@RequestMapping("/per")
	public String notSavePermissions(HttpServletRequest request) {

		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext == null) {
			return null;
		}
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);


		Map<String, HandlerMapping> stringHandlerMethodMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext, HandlerMapping.class, true, false);
		List<String> notSavePermissions = new ArrayList<String>();
		List<String> savePermissions = new ArrayList<String>();


//		String result = (String) expression.getValue();
		Root2 root2 = new Root2();
		EvaluationContext context = new StandardEvaluationContext(root2);
		for (HandlerMapping handlerMapping : stringHandlerMethodMap.values()) {

			if (handlerMapping instanceof RequestMappingHandlerMapping) {
				RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
				Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
				for (HandlerMethod method : handlerMethods.values()) {
					Method method1 = method.getMethod();

					PreAuthorize annotation = method1.getAnnotation(PreAuthorize.class);
					if (annotation == null) {
						continue;
					}
					String value = annotation.value();
					if (value == null) {
						continue;
					}


					Expression expression = expressionParser.parseExpression(value);

					Boolean result = (Boolean) expression.getValue(context);

					String authority = root2.getAuthority();


					List<Permission> permissions = mapper.selectByPermissionValue(authority);
					if (!CollectionUtils.isEmpty(permissions)) {
						savePermissions.add(authority);
					} else {
						notSavePermissions.add(authority);
					}
				}
			}

		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("notSave", notSavePermissions);
		return jsonObject.toString();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	static class Root2 {

		private String role;
		private String authority;

		public boolean hasAuthority(String authority) {
			this.authority = authority;
			return true;
		}

		public boolean hasRole(String role) {
			this.role = role;
			return false;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}
	}
}
