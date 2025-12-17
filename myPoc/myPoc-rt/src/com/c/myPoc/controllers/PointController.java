package com.c.myPoc.controllers;

import com.c.myPoc.models.PointDto;
import com.c.myPoc.services.PointService;
import com.cocoad.extension.service.ServiceCollection;
import com.cocoad.extension.servlet.HttpContext;
import com.cocoad.extension.servlet.HttpServletController;
import com.tridium.json.JSONArray;
import com.tridium.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@SuppressWarnings("serial")
public class PointController extends HttpServletController {

    private PointService getPointService() {
        return ServiceCollection.getInstance().getService(PointService.class);
    }

    @Override
    protected HttpServletResponse get(HttpContext context) throws IOException {
        String path = context.getPath();
        HttpServletRequest req = context.getRequest();

        try {
            // Root path - get all points
            if (path.equals("/")) {
                String customQuery = req.getParameter("query");
                String pointFilter = req.getParameter("points");
                Integer limit = getIntParam(req, "limit");

                Collection<PointDto> points = getPointService().getPoints(customQuery, pointFilter, limit);

                if (points == null) {
                    return notFound(context);
                }

                JSONArray array = new JSONArray();
                points.forEach(p -> array.put(p.toJSON()));

                return oK(context, array);
            }
            // Total count endpoint
            else if (path.equals("/total/")) {
                String filter = req.getParameter("points");
                int total = getPointService().getTotalPoints(filter);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("total", total);
                return oK(context, jsonObject);
            }
            // Specific point by name
            else {
                String[] pathParts = req.getPathInfo().split("/");
                if (pathParts.length != 2) {
                    return badRequest(context);
                }

                String pointName = pathParts[1];
                JSONObject point = getPoint(pointName, req);

                if (point == null) {
                    return notFound(context);
                }

                return oK(context, point);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return badRequest(context, new JSONObject(new HashMap<String, String>() {
                { put("reason", ex.toString()); }
            }));
        }
    }

    @Override
    protected HttpServletResponse post(HttpContext context) throws IOException {
        // Support POST for write operations in the future
        return get(context);
    }

    private JSONObject getPoint(String pointName, HttpServletRequest request) {
        try {
            PointDto dto = getPointService().getPointByName(pointName);
            return dto == null ? null : dto.toJSON();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private Integer getIntParam(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}