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
        // 这个获取表单输入中返回的用户名
        String username = token.getName();
        // 这个是表单中输入的密码
        // String password = (String) token.getPrincipal();

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

        // //这里我们还要判断密码是否正确，实际应用中，我们的密码一般都会加密，以Md5加密为例
        // Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
        // //这里第个参数，是salt
        // 就是加点盐的意思，这样的好处就是用户的密码如果都是123456，由于盐的不同，密码也是不一样的，就不用怕相同密码泄漏之后，不会批量被破解。
        // String encodePwd=md5PasswordEncoder.encodePassword(password, userName);
        // //这里判断密码正确与否
        // if(!userInfo.getPassword().equals(encodePwd))
        // {
        // throw new BadCredentialsException("密码不正确");
        // }
        // //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，这里为了方便我接下去的判断，我就不用加密了。
        //
        //
        //对密码进行 md5 加密  loginPassword是登录密码
        // String loginPassword = (String) token.getCredentials();
        //
        // String md5Password = DigestUtils.md5DigestAsHex(loginPassword.getBytes());

        //判断密码是否正确
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
