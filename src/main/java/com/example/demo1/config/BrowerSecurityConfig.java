package com.example.demo1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 自定义身份验证类（用于重写WebSecurityConfigurerAdapter默认配置）
 * @Configuration     表示这是一个配置类
 * @EnableWebSecurity    允许security
 * configure()     该方法重写了父类的方法，用于添加用户与角色
 *
 * <p>
 * Created by xiangjiangcheng on 2018/8/20 14:37.
 */
@Configuration
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

    /**
     * 重写该方法，设定用户访问权限
     * 用户身份可以访问 订单相关API
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/orders/**").hasRole("USER")    //用户权限
                .antMatchers("/users/**").hasRole("ADMIN")    //管理员权限
                .and()
                .formLogin()
                .loginPage("/login")    //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();        //暂时禁用CSRF，否则无法提交表单
    }


    /**
     * 重写该方法，添加自定义用户
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 从内存中获取
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user1").password(new BCryptPasswordEncoder().encode("123456")).roles("ADMIN", "USER")
                .and()
                .withUser("user2").password(new BCryptPasswordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("user3").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }
}
