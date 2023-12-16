package com.employment.pojo.vo;

import com.employment.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @program: StudentEmploymentSystem
 * @ClassName LoginUserVo
 * @author: c9noo
 * @create: 2023-11-06 19:26
 * @Version 1.0
 * 封装成UserDetails对象 进行返回，包含两个部分 user的信息  和 权限信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserVo implements UserDetails {

    private User user;

    private List<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
