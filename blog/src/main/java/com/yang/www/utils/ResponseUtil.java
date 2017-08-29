package com.yang.www.utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public static void write(HttpServletResponse response,Object o){
		response.setContentType("text/html;charset=utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.println(o.toString());
			out.flush();
			out.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
