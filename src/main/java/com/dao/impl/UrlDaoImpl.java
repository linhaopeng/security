package com.dao.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.model.Role;
import com.model.Url;

//通过URL地址获取相应权限然后在获取相应的角色集合
@Component("urlDao")
// 需要实现FilterInvocationSecurityMetadataSource接口
public class UrlDaoImpl implements FilterInvocationSecurityMetadataSource {
	@Resource
	private SessionFactory sessionFactory;

	// 通过URL地址获取相应权限然后在获取相应的角色集合
	public Url getRoleByUrl(String url) {
		String hql = "FROM Url u JOIN FETCH u.privilege up JOIN FETCH up.roles WHERE u.url=:url";
		Session session = sessionFactory.openSession();
		return (Url) session.createQuery(hql).setString("url", url).uniqueResult();
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml", "spring-*.xml");
		UrlDaoImpl urlDao = (UrlDaoImpl) context.getBean("urlDao");
		Url url = urlDao.getRoleByUrl("/user/save.jsp");
		System.out.println(url.getUrl());
		System.out.println(url.getPrivilege().getName());
		System.out.println(url.getPrivilege().getRoles());
	}

	// 此方法就是通过url地址获取 角色信息的方法
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// 获取当前的URL地址
		System.out.println("object的类型为:" + object.getClass());
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String url = filterInvocation.getRequestUrl();
		System.out.println("访问的URL地址为(包括参数):" + url);
		url = filterInvocation.getRequest().getServletPath();
		System.out.println("访问的URL地址为:" + url);
		Url urlObject = getRoleByUrl(url);
		System.out.println("urlObject:" + urlObject);
		if (urlObject != null && urlObject.getPrivilege() != null) {
			Set<Role> roles = urlObject.getPrivilege().getRoles();
			Collection<ConfigAttribute> c = new HashSet<ConfigAttribute>();
			c.addAll(roles);
			return c; // 将privilege中的roles改为Collection<ConfigAttribute>
		} else {
			// 如果返回为null则说明此url地址不需要相应的角色就可以访问, 这样Security会放行
			return null;
		}
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	// 如果为真则说明支持当前格式类型,才会到上面的 getAttributes 方法中
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		// 需要返回true表示支持
		return true;
	}
}
