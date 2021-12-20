package com.mcbanners.apigateway.security;

import java.util.Arrays;

public enum ProtectedRoute {
    USER_SESSION_VALIDATE("/user/session/validate"),
    BANNER_SAVE("/banner/saved/save/**"),
    BANNER_MANAGE_SAVED("/banner/manage_saved/**");

    private final String antPath;

    ProtectedRoute(String antPath) {
        this.antPath = antPath;
    }

    public String getAntPath() {
        return antPath;
    }

    public static String[] getAntPaths() {
        return Arrays.stream(values()).map(ProtectedRoute::getAntPath).toArray(String[]::new);
    }
}
