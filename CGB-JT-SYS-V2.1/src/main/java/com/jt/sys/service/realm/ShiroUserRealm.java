package com.jt.sys.service.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.sys.dao.SysMenuDao;
import com.jt.sys.dao.SysRoleMenuDao;
import com.jt.sys.dao.SysUserDao;
import com.jt.sys.dao.SysUserRoleDao;
import com.jt.sys.entity.SysUser;
/**
 * 安全框架业务逻辑层的实现没有接口,只有实现类
 * Realm为Shiro框架中核心业务组件之一
 * 通过此对象可以完成数据业务的获取以及封装
 * @author UID
 *
 */
@Service
public class ShiroUserRealm extends AuthorizingRealm {
	@Autowired
	private SysMenuDao sysMenuDao;
    @Autowired
	private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    
    /**
     * 设置凭证(Credentials)匹配器
     */
	@Override
	public void setCredentialsMatcher(
			CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher cMatcher=
				new HashedCredentialsMatcher();
//		设置加密算法
		cMatcher.setHashAlgorithmName("MD5");
		//设置加密的次数(这个次数应该与保存密码时那个加密次数一致)
		//cMatcher.setHashIterations(5);
		super.setCredentialsMatcher(cMatcher);
	}
	
	/**负责完成认证领域信息的获取以及封装
	 *   token-->
	 * subject->SecurityManager->Authentication->realm
	 * */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
//			把令牌token从controller传进来
			AuthenticationToken token) 
	    		 throws AuthenticationException {
		System.out.println("==doGetAuthenti ationInfo==");
		//1.从token对象获取用户名(用户输入的)
//		UsernamePasswordToken upToken=
//		(UsernamePasswordToken)token;
//		String username=upToken.getUsername();
		String username = (String)token.getPrincipal();
		//System.out.println("token : "+token);
		//2.基于用户名查询用户信息并进行身份校验
		//System.out.println("username = "+username);
		SysUser user= sysUserDao.findUserByUserName(username);
		if(user==null)
		throw new AuthenticationException("此用户不存在");
		if(user.getValid()==0)
		throw new AuthenticationException("此用户已被禁用");
		//3.对用户信息进行封装,获取盐值
		ByteSource credentialsSalt=
				ByteSource.Util.bytes(user.getSalt());
		//进行密码验证
	/*	SysUser user2 = new SysUser();
		user2.setId(user.getId());
		user2.setUsername(user.getUsername());*/
		SimpleAuthenticationInfo info=
		new SimpleAuthenticationInfo(
				user,//principal 用户身份?传进来的?还是从数据库中取的
				user.getPassword(),
				//hashedCredentials已加密的凭证,数据库里取出的
				credentialsSalt,//credentialsSalt 密码加密时使用的盐
				getName());//realmName 当前方法所在对象的名字
		//这个info竟然是一个SysUser对象
		//System.out.println("info = "+info);
//		应该是把user/info返回给认证管理器,SecurityManager让它来比对
		return info;//返回给谁?认证管理器
	}
	/**负责完成用户权限领域信息的获取以及封装*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
		PrincipalCollection principals) {
		System.out.println("==doGetAuthorizationInfo===");
		//获取用户身份对象
		SysUser user=(SysUser)principals.getPrimaryPrincipal();
		//1.基于用户id查找角色id
		List<Integer> roleIds=
		sysUserRoleDao.findRoleIdsByUserId(user.getId());
		//System.out.println("roleIds="+roleIds);
		//2.基于角色id查找菜单id
		Integer[] array={};
		List<Integer>  menuIds=
		sysRoleMenuDao.findMenuIdsByRoleId(
//				把roleIds list集合转换成数组
				roleIds.toArray(array));
		//3.基于菜单id查找权限标识
		
		List<String> permissions=
		sysMenuDao.findPermissions(menuIds.toArray(array));
		//4.对权限标识进行去重和空的操作
		Set<String> set=new HashSet<String>();
		for(String permission:permissions){
			if(!StringUtils.isEmpty(permission)){
				set.add(permission);
			}
		}
		//5.对权限标识信息进行封装
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		System.out.println("授权的info = "+info.getStringPermissions());
		return info;//返回给授权管理器对象
		}
	
	
}
