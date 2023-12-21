package com.seidor.inventario.util;

import com.seidor.inventario.util.NavigationUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

public class ResourceServletClient {
	
		public static String getResource(String url, String urlParameters) {
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection)obj.openConnection();
				
				//add reuqest header
				con.setRequestMethod("POST");
		
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				if (urlParameters != null) wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
		
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				return response.toString();
			} catch (Exception ex) { 
				ex.printStackTrace();
			}
			return null;
		}
		
		public static void openModalStylesResource(String resourcePath, Component comp){
			openModalStylesResource(resourcePath, comp, null);
		}
		
		public static void openModalStylesResource(String resourcePath, Component comp, HashMap<String, Object> params){
			String zulCode = getStylesResource(resourcePath);
			if (zulCode != null) {
				NavigationUtil.openModalWindowDirectly(comp, zulCode, params);
			}
		}
		
		public static void loadStylesResource(String resourcePath, Component comp){
			loadStylesResource(resourcePath, comp, null);
		}
		
		public static void loadStylesResource(String resourcePath, Component comp, HashMap<String, Object> params){
			String zulCode = getStylesResource(resourcePath);
			if (zulCode != null) {
				Executions.getCurrent().createComponentsDirectly(zulCode, "zul", comp, params);
			}
		}
		
		public static String getStylesResource(String resourcePath){
			String stylesUrl = "http://localhost/style/";
			if (resourcePath != null && !resourcePath.contains("resourcePath=")) resourcePath = "resourcePath=" + resourcePath; 
			if (stylesUrl != null) {
				stylesUrl = stylesUrl + "/resource";
				return getResource(stylesUrl, resourcePath);
			}
			
			return null;
		}
		

}
