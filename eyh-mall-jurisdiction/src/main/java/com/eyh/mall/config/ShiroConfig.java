package com.eyh.mall.config;

import com.eyh.mall.realm.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李平
 * @Date 2023-1-27
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private ShiroRealm shiroRealm;

    @Value("${admin.login}")
    private String login;

    /**
     * 配置Shiro安全管理器
     * @return DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        // 创建 DefaultWebSecurityManager 对象
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 创建加密对象，设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密方式 MD5
        matcher.setHashAlgorithmName("md5");
        // 设置迭代加密次数
        matcher.setHashIterations(3);
        // 将加密对象存储到 shiroRealm 中
        shiroRealm.setCredentialsMatcher(matcher);
        // 将 shiroRealm 存入到 DefaultWebSecurityManager 对象中
        defaultWebSecurityManager.setRealm(shiroRealm);
        // 返回
        return defaultWebSecurityManager;
    }

    /**
     * 配置 Shiro 内置过滤拦截器
     * @return DefaultShiroFilterChainDefinition
     */
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 设置不认证即可访问的资源
         /*
            anon:无需认证就可以访问
            authc:必须认证了才能访问
            user:必须拥有 记住我 功能才能用
            perms:拥有对某个资源的权限才能访问
            role:拥有某个角色权限才能访问
         */
        definition.addPathDefinition(login,"anon");
        definition.addPathDefinition("/jurisdiction/logout","anon");

        return definition;
    }

    // /**
    //  * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions),
    //  * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
    //  * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)
    //  * @return DefaultAdvisorAutoProxyCreator
    //  */
    // @Bean
    // public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    //     DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    //     defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
    //     return defaultAdvisorAutoProxyCreator;
    // }
    //
    // /**
    //  * 开启aop注解支持,解决spingboot环境中使用Shiro注解(如@RequiresRoles,@RequiresPermissions)无效
    //  * @param securityManager
    //  * @return AuthorizationAttributeSourceAdvisor
    //  */
    // @Bean
    // public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
    //         org.apache.shiro.mgt.SecurityManager securityManager) {
    //     AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    //     sourceAdvisor.setSecurityManager(securityManager);
    //     return sourceAdvisor;
    // }

}
