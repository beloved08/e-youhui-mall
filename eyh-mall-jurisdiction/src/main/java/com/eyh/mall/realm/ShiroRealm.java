package com.eyh.mall.realm;

import com.eyh.mall.entity.user.Admin;
import com.eyh.mall.feign.client.user.UserApiClient;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 李平
 * @Date 2023-1-27
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserApiClient userApiClient;

    /**
     * 自定义授权方法
     * @param principalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

       return null;
    }

    /**
     * 自定义登录认证方法
     * @param authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = authenticationToken.getPrincipal().toString();

        Admin admin = userApiClient.selectAdmin(account);
        if (admin != null && admin.getFrozen() != 1){
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    admin.getPassword(),
                    ByteSource.Util.bytes("slat"),
                    account
            );
            return info;
        }
        return null;
    }
}
