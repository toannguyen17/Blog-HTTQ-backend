package com.httq.model;


import com.httq.services.user.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailCustom implements UserDetails {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private User user;

    public UserDetailCustom(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        log.info(grantedAuthorities.toString());

        if (user.getRoles() != null){
            user.getRoles().forEach(role -> {
                log.info(role.name());
                grantedAuthorities.add(new SimpleGrantedAuthority(role.name()));
            });
        }
        return grantedAuthorities;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public User getUserDetails() {
        return user;
    }

    public User getUser() {
        return user;
    }
}
