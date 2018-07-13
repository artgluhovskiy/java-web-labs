package org.art.web.labs.model.chat;

public enum Role {
    USER("user"),
    ADMIN("admin");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }
}
