/**
 * 文件名称   : com.autozi.lihf.start.Start.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-7-25
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.web;

import org.mortbay.jetty.Server;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
public class Start {
	public static final int PORT = 8080;
	public static final String CONTEXT = "/";
	public static final String BASE_URL = "http://localhost:"+PORT+CONTEXT;

	public static void main(String[] args) throws Exception {
		System.setProperty("B2BCENTER_HOME", "C:/supply-chain");
		System.setProperty("java.awt.headless", "true");
		
		Server server = JettyUtils.buildDebugServer(PORT, CONTEXT);
		server.start();
 
		System.out.println(BASE_URL);
		System.out.println("Hit Enter in console to stop server");
		
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
 	}

}
