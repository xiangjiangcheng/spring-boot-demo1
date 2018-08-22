package com.example.demo1.config;

import com.example.demo1.entity.User;
import com.example.demo1.model.UserConstant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 自定义用户身份信息
 *
 * <p>
 * Created by xiangjiangcheng on 2018/8/21 17:25.
 */
public class MyUserDetails implements UserDetails{
    
    private static final long serialVersionUID = 1L;

    // 用户信息
    private User user;

    // 用户角色
    private Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.getState().equals(UserConstant.STATE_ACCOUNTEXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.getState().equals(UserConstant.STATE_LOCK);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.getState().equals(UserConstant.STATE_TOKENEXPIRED);
    }

    @Override
    public boolean isEnabled() {
        return this.user.getState().equals(UserConstant.STATE_NORMAL);
    }
}
