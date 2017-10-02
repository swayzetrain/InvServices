package com.swayzetrain.inventory.common.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_Id")
	@Null(groups = {New.class}, message = "userid must be null for this request")
	private Integer userid;
	
	@Column(name = "username")
	@Size(min=1, max=25)
	@NotNull(groups = {New.class}, message = "username is required for this request")
	private String username;
	
	@Column(name = "password")
	@Size(min=8, max=255)
	@NotNull(groups = {New.class}, message = "password is required for this request")
	private String password;
	
	@Column(name = "date_Created")
	@Null(groups = {New.class}, message = "datecreated must be null for this request")
	private Timestamp datecreated;
	
	@Column(name = "date_Modified")
	@Null(groups = {New.class}, message = "datecreated must be null for this request")
	private Timestamp datemodified;
	
	@Column(name = "enabled", nullable = true)
	@NotNull(groups = {New.class}, message = "enabled is required for this request")
	private boolean enabled;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getDatecreated() {
		return datecreated;
	}

	public void setDatecreated(Timestamp datecreated) {
		this.datecreated = datecreated;
	}

	public Timestamp getDatemodified() {
		return datemodified;
	}

	public void setDatemodified(Timestamp datemodified) {
		this.datemodified = datemodified;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public interface New {
		
	}
}
