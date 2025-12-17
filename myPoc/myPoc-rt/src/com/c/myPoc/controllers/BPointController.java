package com.c.myPoc.controllers;

import com.c.myPoc.models.PointDto;
import com.c.myPoc.services.PointService;
import com.cocoad.extension.service.ServiceCollection;
import com.tridium.json.JSONArray;
import com.tridium.json.JSONObject;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.web.*;
import java.util.Collection;

/**
 * Point API Controller - BWebServlet version
 * Register this in Workbench: WebService > Servlets > New > BPointController
 * Name it as "pointApi"
 */
@NiagaraType
public class BPointController extends BWebServlet {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.controllers.BPointController(2979906276)1.0$ @*/
/* Generated Mon Dec 15 11:59:58 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPointController.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private PointService getPointService() {
        return ServiceCollection.getInstance().getService(PointService.class);
    }

    @Override
    public void doGet(WebOp op) throws Exception {
        String path = op.getRequest().getPathInfo();
        if (path == null) path = "/";

        try {
            // Root path - get all points
            if (path.equals("/")) {
                String customQuery = op.getRequest().getParameter("query");
                String pointFilter = op.getRequest().getParameter("points");
                Integer limit = getIntParam(op, "limit");

                Collection<PointDto> points = getPointService().getPoints(customQuery, pointFilter, limit);

                if (points == null) {
                    op.getResponse().setStatus(404);
                    return;
                }

                JSONArray array = new JSONArray();
                points.forEach(p -> array.put(p.toJSON()));

                sendJson(op, array);
                return;
            }
            // Total count endpoint
            else if (path.equals("/total/") || path.equals("/total")) {
                String filter = op.getRequest().getParameter("points");
                int total = getPointService().getTotalPoints(filter);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("total", total);
                sendJson(op, jsonObject);
                return;
            }
            // Specific point by name
            else {
                String[] pathParts = path.split("/");
                if (pathParts.length >= 2) {
                    String pointName = pathParts[1];
                    PointDto point = getPointService().getPointByName(pointName);

                    if (point == null) {
                        op.getResponse().setStatus(404);
                        return;
                    }

                    sendJson(op, point.toJSON());
                    return;
                }
            }

            op.getResponse().setStatus(400);

        } catch (Exception ex) {
            ex.printStackTrace();
            op.getResponse().setStatus(500);
            JSONObject error = new JSONObject();
            error.put("error", ex.getMessage());
            sendJson(op, error);
        }
    }

    @Override
    public void doPost(WebOp op) throws Exception {
        doGet(op);
    }

    private void sendJson(WebOp op, JSONArray array) throws Exception {
        op.setContentType("application/json");
        op.getWriter().write(array.toString());
    }

    private void sendJson(WebOp op, JSONObject json) throws Exception {
        op.setContentType("application/json");
        op.getWriter().write(json.toString());
    }

    private Integer getIntParam(WebOp op, String paramName) {
        String value = op.getRequest().getParameter(paramName);
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