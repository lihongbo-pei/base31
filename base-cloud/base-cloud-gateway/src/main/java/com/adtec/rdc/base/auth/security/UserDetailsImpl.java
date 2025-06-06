package com.adtec.rdc.base.auth.security;

import com.adtec.rdc.base.common.enums.UserStatusEnum;
import com.adtec.rdc.base.common.model.bo.SysUser;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author: JTao
 * @date: 2018/10/9 10:09
 * @description: security 用户对象
 */
@Data
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -2636609458742965698L;
    
	private String userId;
    private String username;
    private String password;
    private String status;

    public UserDetailsImpl(SysUser userVo) {
        this.userId = userVo.getUserId();
        this.username = userVo.getLoginName();
        this.password = userVo.getLoginPwd();
        this.status = userVo.getDelFlag();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !StringUtils.equals(UserStatusEnum.LOCK.getCode(), status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(UserStatusEnum.NORMAL.getCode(), status);
    }
}
