package com.yang.www.utils;


import com.yang.www.po.Blog;

public class PageUtil {

	public static String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager'>");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page-1)+"&input="+q+"'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/blog/search.html?page="+(page+1)+"&input="+q+"'>下一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}

	public static String getUpAndDownPageCode(Blog lastBlog, Blog nextBlog, String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getBlogId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getBlogId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}

		if(nextBlog==null || nextBlog.getBlogId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getBlogId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}

	public static String genPagination(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
		long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		if (totalPage == 0) {
			return "未查询到数据";
		} else {
			StringBuffer pageCode = new StringBuffer();
			pageCode.append("<li><a href='" + targetUrl + "?page=1&" + param + "'>首页</a></li>");
			if (currentPage > 1) {
				pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage - 1) + "&" + param + "'>上一页</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='" + targetUrl + "?page=" + (currentPage - 1) + "&" + param + "'>上一页</a></li>");
			}
			for (int i = currentPage - 2; i <= currentPage + 2; i++) {
				if (i < 1 || i > totalPage) {
					continue;
				}
				if (i == currentPage) {
					pageCode.append("<li class='active'><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i + "</a></li>");
				} else {
					pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i + "</a></li>");
				}
			}
			if (currentPage < totalPage) {
				pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage + 1) + "&" + param + "'>下一页</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='" + targetUrl + "?page=" + (currentPage + 1) + "&" + param + "'>下一页</a></li>");
			}
			pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "&" + param + "'>尾页</a></li>");
			return pageCode.toString();
		}
	}
}
