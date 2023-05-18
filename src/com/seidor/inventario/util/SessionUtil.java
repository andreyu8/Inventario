package com.seidor.inventario.util;

import java.util.ArrayList;

import com.seidor.inventario.constants.RoleConstants;
import com.seidor.inventario.model.PerfilUsuario;
import com.seidor.inventario.model.Usuario;
//import com.seidor.officereserv.constant.RoleConstants;
//import com.seidor.officereserv.constant.SystemPropertiesConstants;
//import com.seidor.officereserv.context.ApplicationContextUtil;
//import com.seidor.officereserv.dao.LoginDAO;
//import com.seidor.officereserv.model.Login;
//import com.seidor.officereserv.model.SystemProperties;
//import com.seidor.officereserv.model.Users;

import org.zkoss.zk.ui.Sessions;

public class SessionUtil {
	
	public static void setLoggedUserId(Integer detailId) {
		Sessions.getCurrent().setAttribute("userId", detailId);
	}

	public static Integer getLoggedUserId() {
		try {
			return (Integer)Sessions.getCurrent().getAttribute("userId");
		} catch (Exception ex){
			return null;
		}
	}
	
	//sucursal
	public static void setSucursaldUserId(String sucursal) {
		Sessions.getCurrent().setAttribute("sucursal", sucursal);
	}

	public static String getSucursaldUserId() {
		try {
			return (String)Sessions.getCurrent().getAttribute("sucursal");
		} catch (Exception ex){
			return null;
		}
	}
	

	public static void setSucursalId(Integer sucursalId) {
		Sessions.getCurrent().setAttribute("sucursalId", sucursalId);
	}

	public static Integer getSucursalId() {
		try {
			return (Integer)Sessions.getCurrent().getAttribute("sucursalId");
		} catch (Exception ex){
			return null;
		}
	}
	
	public static Usuario getEmptyLoggedUser(){
		Usuario user = new Usuario();
		user.setIdUsuario(getLoggedUserId());
		return user;
	}
	
	public static void setLoggedUserName(String name) {
		Sessions.getCurrent().setAttribute("userName", name);
	}
	
	public static String getLoggedUserName() {
		return (String)Sessions.getCurrent().getAttribute("userName");
	}
	
	public static void setDetailIdentifier(Integer detailId) {
		Sessions.getCurrent().setAttribute("detailId", detailId);
	}

	public static Integer getDetailIdentifier() {
		return (Integer)Sessions.getCurrent().getAttribute("detailId");
	}
	
	public static void setSessionAttribute(String key, Object object){
		Sessions.getCurrent().setAttribute(key, object);
	}
	
	public static Object getSessionAttribute(String key){
		return Sessions.getCurrent().getAttribute(key);
	}

	public static void invalidate() {
		Sessions.getCurrent().invalidate();
	}

	public static void maxInactiveInterval(int maxTime){

		Sessions.getCurrent().setMaxInactiveInterval(maxTime);
	}

	public static void destroy() {
//		SystemProperties sp = SystemPropertiesUtil.getProperty(SystemPropertiesConstants.SYSTEM_GENERAL_SAVELOGIN, SystemPropertiesConstants.CATEGORY_SYSTEM_GENERAL);
//		Boolean saveLogin = sp.getBoolValue();
		
//		if(saveLogin != null && saveLogin) {
//			LoginDAO loginDao = (LoginDAO) ApplicationContextUtil.getBean("loginDao", LoginDAO.class);
//			Long loginId = (Long) Sessions.getCurrent().getAttribute("loginId");
//			Login login = loginDao.get(loginId);
//			if(login != null) {
//				login.setLogout(new Date());
//				loginDao.update(login);
//			}
//		}
		
		Sessions.getCurrent().setAttribute("userId", null);
		Sessions.getCurrent().setAttribute("clientLogged", null);
		Sessions.getCurrent().setAttribute("detailId", null);
		Sessions.getCurrent().setAttribute("userLogged", null);
		Sessions.getCurrent().setAttribute(RoleConstants.USER_ROLES, null);
		Sessions.getCurrent().setAttribute("loginId", null);
	}
	
		
	public static void setUserRoles(ArrayList<PerfilUsuario> roles) {
		Sessions.getCurrent().setAttribute(RoleConstants.USER_ROLES, roles);
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<PerfilUsuario> getUserRoles(){
		return (ArrayList<PerfilUsuario>)Sessions.getCurrent().getAttribute(RoleConstants.USER_ROLES);
	}
	
	
}
