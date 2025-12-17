package com.cocoad.extension.servlet;

import com.cocoad.extension.exception.ContentTypeException;
import netscape.javascript.JSObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public abstract class HttpServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/";
        }

        if (!path.endsWith("/")) {
            path += "/";
        }
        HttpServletResponse response = get(new HttpContext(req, resp, path));
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null) {
            path = "/";
        }

        if (!path.endsWith("/")) {
            path += "/";
        }
        HttpServletResponse response = post(new HttpContext(req, resp, path));
        response.getWriter().flush();
    }

    protected HttpServletResponse get(HttpContext context) throws IOException {
        return notFound(context);
    }

    protected HttpServletResponse post(HttpContext context) throws IOException {
        return notFound(context);
    }

    protected HttpServletResponse oK(HttpContext context, Object object) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_OK);
        context.getResponse().setContentType("application/json");
        context.getResponse().setCharacterEncoding("utf-8");
        context.getResponse().getWriter().println(object);
        return context.getResponse();
    }

    protected HttpServletResponse oK(HttpContext context) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_OK);
        context.getResponse().setCharacterEncoding("utf-8");
        context.getResponse().getWriter().println();
        return context.getResponse();
    }

    protected HttpServletResponse notFound(HttpContext context) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
        return context.getResponse();
    }

    protected HttpServletResponse noContent(HttpContext context) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_NO_CONTENT);
        return context.getResponse();
    }

    protected HttpServletResponse badRequest(HttpContext context,Object object) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        context.getResponse().setContentType("application/json");
        context.getResponse().setCharacterEncoding("utf-8");
        context.getResponse().getWriter().println(object);
        return context.getResponse();
    }

    protected HttpServletResponse badRequest(HttpContext context) throws IOException {
        context.getResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return context.getResponse();
    }

    protected void EnsureContentTypeJson(HttpContext context) throws ContentTypeException {
        if (!context.getRequest().getContentType().equals("application/json")) {
            throw new ContentTypeException("application/json");
        }
    }

    protected HttpServletResponse stream(HttpContext context, Iterable<JSObject> objects) throws IOException {
        ServletOutputStream output = context.getResponse().getOutputStream();
        context.getResponse().setStatus(HttpServletResponse.SC_OK);
        context.getResponse().setContentType("application/json");
        context.getResponse().setCharacterEncoding("utf-8");
        for (JSObject json : objects){
            output.println(json.toString());
            output.flush();
        }
        return context.getResponse();
    }
}
