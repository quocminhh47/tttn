package com.nashtech.ecommercialwebsite.services.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nashtech.ecommercialwebsite.data.entity.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

@Getter @Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    private boolean isEnabled;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl( String email,
                            String password,
                           boolean isEnabled,
                           Collection<? extends GrantedAuthority> authorities) {

        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Account user) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(user.getRole().getRoleName().equalsIgnoreCase("admin"))
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if(user.getRole().getRoleName().equalsIgnoreCase("user"))
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                user.getIsEnabled(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEnabled;
    }

}
