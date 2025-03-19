package com.fantaf1.fantaf1_user.model.enums;

import lombok.Getter;

@Getter
public enum FantaF1Role {
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    USER("USER"),
    OBSERVER("OBSERVER");

    private final String role;

    FantaF1Role(String role) {
        this.role = role;
    }
}
