package com.yang.www.service.impl;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.yang.www.service.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yang.www.po.Blog;
import com.yang.www.po.BlogType;
import com.yang.www.po.Blogger;
import com.yang.www.po.Link;
//import com.yang.service.BlogTypeService;
//import com.java1234.service.BloggerService;
import com.yang.www.service.LinkService;

/**
 * 将一些常用的信息放到application中，增强系统性能
 */
@Component
public class InitComponent implements ServletContextListener,ApplicationContextAware{

	private static ApplicationContext applicationContext;

	public void contextInitialized(ServletContextEvent sce) {

		ServletContext application=sce.getServletContext();
		BloggerService bloggerService=(BloggerService) applicationContext.getBean("bloggerService");
		Blogger blogger=bloggerService.find();
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);

		LinkService linkService=(LinkService) applicationContext.getBean("linkService");
		List<Link> linkList=linkService.getLinks(null);
		application.setAttribute("linkList", linkList);

		BlogTypeService blogTypeService=(BlogTypeService) applicationContext.getBean("blogTypeService");
		List<BlogType> blogTypeCountList=blogTypeService.getBlogTypes();
		application.setAttribute("blogTypeCountList", blogTypeCountList);

		BlogService blogService=(BlogService) applicationContext.getBean("blogService");
		List<Blog> blogCountList=blogService.groupByDate();
		application.setAttribute("blogCountList", blogCountList);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext=applicationContext;
	}
}
