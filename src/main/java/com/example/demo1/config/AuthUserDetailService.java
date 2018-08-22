package com.example.demo1.config;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 自定义用户身份信息
 *
 * <p>
 * Created by xiangjiangcheng on 2018/8/20 14:38.
 */

import com.example.demo1.entity.User;
import com.example.demo1.entity.UserRole;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户身份认证服务类
 * */
@Service("userDetailsService")
public class AuthUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        try {
            User user = userMapper.findByUsername(name);
            if(user != null) {
                List<UserRole> urs = userRoleMapper.findByUserId(user.getId());
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for(UserRole ur : urs) {
                    String roleName = ur.getRole().getName();
                    SimpleGrantedAuthority grant = new SimpleGrantedAuthority(roleName);
                    authorities.add(grant);
                }
                //封装自定义UserDetails类
                userDetails = new MyUserDetails(user, authorities);
            } else {
                throw new UsernameNotFoundException("该用户不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;
    }

}