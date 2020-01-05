package com.mcbanners.apigateway;

import java.util.Arrays;

public enum OpenRoute {
    BANNER_SERVICE("/banner/svc/**"),
    BANNER_SERVER("/banner/server/**"),
    BANNER_RESOURCE("/banner/resource/**"),
    BANNER_AUTHOR("/banner/author/**");

    private final String ant;

    OpenRoute(String ant) {
        this.ant = ant;
    }

    public String getAnt() {
        return ant;
    }

    public static String[] ants() {
        return Arrays.stream(OpenRoute.values()).map(or -> or.ant).toArray(String[]::new);
    }
}
