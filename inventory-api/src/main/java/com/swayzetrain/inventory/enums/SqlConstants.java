package com.swayzetrain.inventory.enums;

public class SqlConstants {

	public final static String FIND_USER_QUERY = "select username,password,enabled from users where username = ?";
	public final static String FIND_ROLES_QUERY = "select u.username, r.role_Name from users u, role r, userrole ur where u.user_id=ur.user_id and ur.role_Id=r.role_Id and u.username = ?";
	
}
