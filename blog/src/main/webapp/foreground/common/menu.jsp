<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	function checkData(){
		var input=document.getElementById("input").value.trim();
		if(input==null || input==""){
			alert("请输入您要查询的关键字！");
			return false;
		}else{
			return true;
		}
	}

</script>
<div class="row">
		<div class="col-md-12" style="padding-top: 10px">
			<nav class="navbar navbar-default">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>
			      <a class="navbar-brand" href="${pageContext.request.contextPath}/index.html"><font color="black"><strong>首页</strong></font></a>
			    </div>
			
			    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			      <ul class="nav navbar-nav">
			        <li><a href="${pageContext.request.contextPath}/download.html"><font color="black"><strong>学习资料</strong></font></a></li>
					  <li><a href="${pageContext.request.contextPath}/download.html"><font color="black"><strong>开源项目推荐</strong></font></a></li>
					  <li><a href="${pageContext.request.contextPath}/download.html"><font color="black"><strong>行业资讯</strong></font></a></li>
					  <li><a href="${pageContext.request.contextPath}/blogger/aboutMe.html"><font color="black"><strong>关于站长</strong></font></a></li>
				  </ul>
			      <form action="${pageContext.request.contextPath}/blog/search.html" class="navbar-form navbar-right" role="search" method="post" onsubmit="return checkData()">
			        <div class="form-group">
			          <input type="text" id="input" name="input" value="${input }" class="form-control" placeholder="请输入关键字">
			        </div>
			        <button type="submit" class="btn btn-default" >搜索</button>
			      </form>
			    </div>
			  </div>
			</nav>
		</div>
	</div>