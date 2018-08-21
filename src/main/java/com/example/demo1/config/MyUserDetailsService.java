package com.example.demo1.config;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Created by xiangjiangcheng on 2018/8/20 14:38.
 */



// @Component
// public class MyUserDetailsService implements UserDetailsService {
//
//     private Logger logger = LoggerFactory.getLogger(getClass());
//
//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         logger.info("用户的用户名: {}", username);
//         // TODO 根据用户名，查找到对应的密码，与权限
//
//         // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
//         User user = new User(username, "123456",
//                 AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//         return user;
//     }
// }