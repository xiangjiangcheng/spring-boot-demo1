package com.example.demo1.config;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 自定义认证服务
 *
 * support方法检查authentication的类型是不是这个AuthenticationProvider支持的，这里我简单地返回true，就是所有都支持，这里所说的authentication为什么会有多个类型，是因为多个AuthenticationProvider可以返回不同的Authentication。
 *
 * public Authentication authenticate(Authentication authentication) throws AuthenticationException 方法就是验证过程。
 *
 * 如果AuthenticationProvider返回了null，AuthenticationManager会交给下一个支持authentication类型的AuthenticationProvider处理。
 *
 * 这里定义完 需要在配置文件（BrowerSecurityConfig）里面配置 auth.authenticationProvider(securityProvider);
 * <p>
 * <p>
 * Created by xiangjiangcheng on 2018/8/22 9:14.
 */
@Service("securityProvider")
public class SecurityProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public SecurityProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // if (!isMatch(authentication)) {
        //     return null;
        // }
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        UserDetails userDetails = null;

        if (username != null) {
            userDetails = userDetailsService.loadUserByUsername(username);
        }
        System.out.println("$$" + userDetails);

        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        } else if (!userDetails.isEnabled()) {
            System.out.println("用户已被禁用");
            throw new DisabledException("用户已被禁用");
        } else if (!userDetails.isAccountNonExpired()) {
            System.out.println("账号已过期");
            throw new LockedException("账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            System.out.println("账号已被锁定");
            throw new LockedException("账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            System.out.println("凭证已过期");
            throw new LockedException("凭证已过期");
        }

        String password = userDetails.getPassword();
        //与authentication里面的credentials相比较
        if (!password.equals(token.getCredentials())) {
            throw new BadCredentialsException("Invalid username/password");
        }
        //授权
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return true;
    }

    private boolean isMatch(Authentication authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
