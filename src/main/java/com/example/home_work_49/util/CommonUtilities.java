package com.example.home_work_49.util;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtilities {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
