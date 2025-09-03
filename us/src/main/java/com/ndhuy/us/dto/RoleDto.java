package com.ndhuy.us.dto;

public class RoleDto {
    private RoleDto() {
    }

    public record RoleCreateInput(
            String name,
            String description) {
    }
    public record RoleQueryInput(
            String name) {
    }
    public record RoleQueryOut(
         
            String name,
            String description) {
    }
}
