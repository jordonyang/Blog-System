<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css">
<script type="text/javascript">
    SyntaxHighlighter.all();
    
    function showOtherComment(){
    	$(".otherComment").show();
    }
    
    function loadimage(){
		document.getElementById("randImage").src="${pageContext.request.contextPath}/image.jsp?"+Math.random();
	}
    
    function submitData(){
    	var content=$("#content").val();
    	var imageCode=$("#imageCode").val();
    	if(content==null || content==""){
    		alert("请输入评论内容！");
    	}else if(imageCode==null || imageCode==""){
    		alert("请填写验证码！");
    	}else{
    		$.post("${pageContext.request.contextPath}/comment/save.do",{"content":content,'imageCode':imageCode,'blog.blogId':'${blog.blogId}'},function(result){
    			if(result.success){
    				window.location.reload();
    				alert("评论成功，待审核通过后显示！");
    			}else{
    				alert(result.msg);
    			}
    		},"json");
    	}
    }
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="data_list">
	<div class="data_list_title">
		<img src="/static/images/blog_show_icon.png"/>
		博客信息
	</div>
	<div>
		<div class="blog_title"><h3><strong>${blog.title }</strong></h3></div>
		<div class="blog_share">
			<div class="bshare-custom"><a title="分享到微信" class="bshare-weixin"></a><a title="分享到QQ空间" class="bshare-qzone"></a><a title="分享到QQ好友" class="bshare-qqim"></a><a title="分享到新浪微博" class="bshare-sinaminiblog"></a><a title="分享到人人网" class="bshare-renren"></a><a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a><span class="BSHARE_COUNT bshare-share-count">0</span></div><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/button.js#style=-1&amp;uuid=&amp;pophcol=3&amp;lang=zh"></script><a class="bshareDiv" onclick="javascript:return false;"></a><script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
		</div>
		<div class="blog_info">
			发布时间：『 <fmt:formatDate value="${blog.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>』&nbsp;&nbsp;博客类别：${blog.blogType.typeName }&nbsp;&nbsp;阅读(${blog.clicks }) 评论(${blog.commentCount })
		</div>
		<div class="blog_content">
		${blog.content }
		</div>
		<div class="blog_keyword">
			<font><strong>关键字：</strong></font>
			<c:choose>
				<c:when test="${keywords==null }">
					&nbsp;&nbsp;无
				</c:when>
				<c:otherwise>
					<c:forEach var="keyword" items="${keywords }">
						&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/blog/search.html?input=${keyword}" target="_blank">${keyword }</a>&nbsp;&nbsp;
						
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="blog_lastAndNextPage">
			${pageCode }
		</div>
	</div>
</div>

<div class="data_list">
	<div class="data_list_title">
		<img src="/static/images/comment_icon.png"/>
		评论信息
		<c:if test="${commentList.size()>10 }">
			<a href="javascript:showOtherComment()" style="float: right;padding-right: 40px;">显示所有评论</a>
		</c:if>
	</div>
	<div class="commentDatas">
		<c:choose>
			<c:when test="${commentList.size()==0 }">
				暂无评论
			</c:when>
			<c:otherwise>
				<c:forEach var="comment" items="${commentList}" varStatus="status">
					<c:choose>
						<c:when test="${status.index<10 }">
							<div class="comment">
								<span><font>${status.index+1 }楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }：</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;]</span>
							</div>
						</c:when>
						<c:otherwise>
							<div class="otherComment">
								<span><font>${status.index+1 }楼&nbsp;&nbsp;&nbsp;&nbsp;${comment.userIp }：</font>${comment.content }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.releaseDate }" type="date" pattern="yyyy-MM-dd HH:mm"/>&nbsp;]</span>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<%--<div class="data_list" >--%>
<%--<iframe id="uyan_ifr_4655907" scrolling="no" frameborder="0" style="height: 454px; display: block !important; width: 100% !important; border: 0px none !important; overflow: hidden !important;" src="http://api.v2.uyan.cc/v4/comment/?uid=1847247&amp;frameid=4655907&amp;du=&amp;su=www.7han.net%2Fdianying%2Fkehuanpian%2F7c9e00317564201c%2F&amp;pic=&amp;url=http%3A%2F%2Fwww.7han.net%2Fdianying%2Fkehuanpian%2F7c9e00317564201c%2F&amp;title=%E3%80%8A%E5%AE%88%E6%8A%A4%E8%80%85%3A%E4%B8%96%E7%BA%AA%E6%88%98%E5%85%83%2F%D0%97%D0%B0%D1%89%D0%B8%D1%82%D0%BD%D0%B8%D0%BA%D0%B8%E3%80%8B%E7%99%BE%E5%BA%A6%E4%BA%91%E7%9B%98%E5%9C%A8%E7%BA%BF%E8%A7%82%E7%9C%8B%E4%B8%8E%E8%BF%85%E9%9B%B7%E7%94%B5%E9%A9%B4%E4%B8%8B%E8%BD%BD%20-%20%E7%A7%91%E5%B9%BB%E7%89%87%20-%20%E4%B8%83%E6%B1%89%E5%BD%B1%E8%A7%86&amp;t=1503975600052"></iframe>--%>
<%--</div--%>

        <!-- UY BEGIN -->
<div id="uyan_frame"></div>
<script type="text/javascript" src="http://v2.uyan.cc/code/uyan.js"></script>
<!-- UY END -->


<%--<div class="data_list" >--%>
	<%--<div class="data_list_title">--%>
		<%--<img src="/static/images/publish_comment_icon.png"/>--%>
		<%--发表评论--%>
	<%--</div>--%>
	<%--<div class="publish_comment">--%>
			<%--<div>--%>
				<%--<textarea style="width: 100%" rows="3" id="content" name="content" placeholder="来说两句吧..."></textarea>--%>
			<%--</div>--%>
			<%--<div class="verCode">--%>
				<%--验证码：<input type="text" value="" name="imageCode"  id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>&nbsp;<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="/image.jsp" width="60" height="20" border="1" align="absmiddle"> --%>
			<%--</div>--%>
			<%--<div class="publishButton">--%>
				<%--<button class="btn btn-primary" type="button" onclick="submitData()">发表评论</button>--%>
			<%--</div>--%>
		<%--</form>--%>
	<%--</div>--%>
</div>
