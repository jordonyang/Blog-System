package com.yang.www.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.yang.www.po.Blogger;
import com.yang.www.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm{

	private BloggerService bloggerService;
	@Autowired
	public void setBloggerService(BloggerService bloggerService){
		this.bloggerService=bloggerService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String) token.getPrincipal();
		Blogger blogger=bloggerService.getBloggerByName(userName);
		if(blogger!=null){
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(blogger.getName(), blogger.getPassword(), "blog");
			return authcInfo;
		}else{
			return null;			
		}
	}

}
