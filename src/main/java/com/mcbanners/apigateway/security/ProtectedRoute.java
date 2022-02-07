package com.mcbanners.apigateway.security;

import java.util.Arrays;

public enum ProtectedRoute {
    USER_SESSION_VALIDATE("/user/session/validate", false),
    BANNER_SAVE("/banner/saved/save/**", true),
    BANNER_MANAGE_SAVED("/banner/manage_saved/**", false);

    private final String antPath;
    private final boolean optional;

    ProtectedRoute(String antPath, boolean optional) {
        this.antPath = antPath;
        this.optional = optional;
    }

    public String getAntPath() {
        return antPath;
    }

    public boolean isOptional() {
        return optional;
    }

    public static String[] getAntPaths() {
        return Arrays.stream(values()).map(ProtectedRoute::getAntPath).toArray(String[]::new);
    }
}
