package com.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "privilege", schema = "")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Privilege {

	// Fields

	private Integer id;
	private String name;

	private Set<Role> roles;

	// private Collection<ConfigAttribute> roles;

	// @ManyToMany
	// @JoinTable(name = "privilege_role",
	// joinColumns = { @JoinColumn(name = "pid") },
	// inverseJoinColumns = { @JoinColumn(name = "rid") })
	// public Collection<ConfigAttribute> getRoles() {
	// return roles;
	// }
	//
	// public void setRoles(Collection<ConfigAttribute> roles) {
	// this.roles = roles;
	// }

	/** default constructor */
	public Privilege() {
	}

	@ManyToMany
	@JoinTable(name = "privilege_role", schema = "", joinColumns = { @JoinColumn(name = "pid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "rid", nullable = false, updatable = false) })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/** full constructor */
	public Privilege(String name) {
		this.name = name;
	}

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

}
