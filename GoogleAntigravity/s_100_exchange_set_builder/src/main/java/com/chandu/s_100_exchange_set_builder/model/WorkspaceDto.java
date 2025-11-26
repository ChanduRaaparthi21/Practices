package com.chandu.s_100_exchange_set_builder.model;

import jakarta.validation.constraints.NotBlank;

public class WorkspaceDto {

    @NotBlank(message = "Workspace path is required")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
