package com.vivek.firstmed.authentication_service.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.stream.Collectors;
import com.vivek.firstmed.authentication_service.entity.User;


@Getter
public class AppUserDetails implements UserDetails {

    private final User user;

    public AppUserDetails(User user) { this.user = user; }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toSet());
    }
    // @Override public String getPassword() { return user.getPassword(); }
    // @Override public String getUsername() { return user.getUsername(); }
    // @Override public boolean isAccountNonExpired() { return true; }
    // @Override public boolean isAccountNonLocked() { return user.isAccountNonLocked(); }
    // @Override public boolean isCredentialsNonExpired() { return true; }
    // @Override public boolean isEnabled() { return user.isEnabled(); }

    // public String getUserId() { return user.getUserId(); }
}
    