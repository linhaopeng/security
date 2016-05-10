package com.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role implements java.io.Serializable,GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8240348843068403342L;
	private Integer id;
	private String name;
	private String detail;
	private Set<Account> accounts = new HashSet<Account>(0);
	
	private String authority;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "native")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DETAIL", nullable = false, length = 100)
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "account_role", schema = "", joinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "aid", nullable = false, updatable = false) })
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	/**
	 * 返回角色名称
	 */
	@Transient
	public String getAuthority() {
		return name;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

}
