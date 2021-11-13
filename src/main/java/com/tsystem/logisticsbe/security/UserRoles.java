package com.tsystem.logisticsbe.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserRoles {

    DRIVER(Set.of(Permission.PERMISSION_READ)),
    MANAGER(Set.of(Permission.PERMISSION_READ, Permission.PERMISSION_WRITE));


    private final Set<Permission> permissions;

    UserRoles(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {
        return getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
