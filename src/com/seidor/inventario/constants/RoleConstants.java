package com.seidor.inventario.constants;

import java.util.HashMap;

public class RoleConstants {

	public static HashMap<Integer, String> ROLE_MAP = new HashMap<Integer, String>();

	public static final String ROLE_LIST = "roleList";
	public static final String USER_ROLES = "userRoles";
	
	
	public static final String ADMIN = "ADMIN";
	
	public static final Integer KEY_ADMIN = 1;
	
	static {
		ROLE_MAP.put(KEY_ADMIN, ADMIN);
	}
	
}