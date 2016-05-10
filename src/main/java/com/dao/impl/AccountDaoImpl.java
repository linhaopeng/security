package com.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.model.Account;

@Component("accountDao")
public class AccountDaoImpl implements UserDetailsService{
	@Resource
	private SessionFactory sessionFactory;

	// 获取当前用户与之对应的角色集合
	public Account getJoinRole(String login) {
		Session session = sessionFactory.getCurrentSession();
		return (Account) session.createQuery("FROM Account a LEFT JOIN FETCH a.roles WHERE a.login=:login").setString("login", login).uniqueResult();
	}
	
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring.xml","spring-*.xml");
		System.out.println(context);
		AccountDaoImpl accountDaoImpl=(AccountDaoImpl)context.getBean("accountDao");
		Account account=accountDaoImpl.getJoinRole("admin01");
		System.out.println(account.getRoles().size());
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return getJoinRole(username);
	}
}
