package com.example.demo1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 自定义身份验证类（用于重写WebSecurityConfigurerAdapter默认配置） 核心认证配置
 * @Configuration     表示这是一个配置类
 * @EnableWebSecurity    允许security
 * configure()     该方法重写了父类的方法，用于添加用户与角色
 *
 * <p>
 * Created by xiangjiangcheng on 2018/8/20 14:37.
 */
@Configuration
// @EnableWebSecurity(debug = true)
@EnableWebSecurity
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    //     http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
    //             .and()
    //             .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
    //             .anyRequest()               // 任何请求,登录后可以访问
    //             .authenticated();
    // }
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationProvider securityProvider;

    @Override
    protected UserDetailsService userDetailsService() {
        //自定义用户信息类
        return this.userDetailsService;
    }

    /**
     * 重写该方法，设定用户访问权限
     * 用户身份可以访问 订单相关API
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/orders/**").hasAnyRole("USER", "ADMIN")    //用户权限
                .antMatchers("/user/**").hasRole("ADMIN")    //管理员权限
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .antMatchers(HttpMethod.GET, "/entries/**", "/articles/**").permitAll()
                // 静态资源
                .antMatchers("/css/**", "/js/**", "/image/**").permitAll()
                .anyRequest().authenticated() //除上面外的所有请求 其余全部需要鉴权认证,登录后可以访问
                .and()
                .formLogin()
                .loginPage("/login")    //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
                .successHandler(new AuthenticationSuccessHandler() {
                    // 通过SecurityContextHolder获取目前登录的用户信息，然后将其放到session中（不建议如此处理）然后将页面重定向到首页中。
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
                            throws IOException, ServletException {
                        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        if (principal != null && principal instanceof UserDetails) {
                            UserDetails user = (UserDetails) principal;
                            System.out.println("loginUser:"+user.getUsername());
                            //维护在session中
                            arg0.getSession().setAttribute("userDetail", user);
                            arg1.sendRedirect("/greeting");
                        }
                    }
                })
                //失败处理
                .failureHandler(new AuthenticationFailureHandler() {

                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
                            throws IOException, ServletException {
                        System.out.println("error："+authenticationException.getMessage());
                        response.sendRedirect("/login?error");
                    }
                })
                //设置默认登录成功跳转页面
                // .defaultSuccessUrl("/index").failureUrl("/login?error").permitAll()
                .permitAll()
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                // .key("")
                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/custom-logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/login")
                .permitAll()  //注销行为任意访问
                .and()
                .csrf().disable();        //暂时禁用CSRF，否则无法提交表单
    }

    /**
     * 重写该方法，添加自定义用户
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //自定义AuthenticationProvider
        auth.authenticationProvider(securityProvider);

        //inMemoryAuthentication 从内存中获取
        // auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
        //         .withUser("user1").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN", "USER")
        //         .and()
        //         .withUser("user2").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
        //         .and()
        //         .withUser("user3").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }
}
