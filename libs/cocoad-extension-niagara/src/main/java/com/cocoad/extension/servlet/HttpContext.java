package com.cocoad.extension.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpContext {
    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getPath() {
        return path;
    }

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String path;

    public HttpContext(HttpServletRequest request, HttpServletResponse response, String path)
    {
        this.request = request;
        this.response = response;
        this.path = path;
    }
}
