package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.collection.*;
import javax.baja.web.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@NiagaraType
public class BMyApiServlet extends BWebServlet {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ com.c.myPoc.BMyApiServlet(2979906276)1.0 @*/
    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyApiServlet.class);
    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    /**
     * ✅ ลบ @Override ออก เพื่อแก้ Error "Method does not override..."
     * แต่ยังคง method นี้ไว้เพื่อรับ Request จาก Niagara
     */
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        // แปลงเป็น HTTP objects
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        String method = httpReq.getMethod();

        // Router: เช็ค Method แล้วส่งไปทำงานต่อ
        if ("GET".equalsIgnoreCase(method)) {
            handleRequest(httpReq, httpResp); // ใช้ Logic เดียวกัน
        }
        else if ("POST".equalsIgnoreCase(method)) {
            handleRequest(httpReq, httpResp); // รองรับ POST เพื่อแก้ 405
        }
        else if ("OPTIONS".equalsIgnoreCase(method)) {
            doOptions(httpReq, httpResp);
        }
        else {
            // Method อื่นๆ ที่ไม่รองรับ ส่ง 405
            httpResp.sendError(405, "Method Not Allowed");
        }
    }

    // Helper method: จัดการ CORS
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setCORSHeaders(resp);
        resp.setStatus(200);
    }

    // Helper method: รวม Logic การทำงานหลัก (เปลี่ยนชื่อจาก doGet เป็น handleRequest)
    protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        setCORSHeaders(resp);

        // Disable Cache ป้องกัน Browser จำค่าเก่า
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        PrintWriter out = resp.getWriter();

        try {
            String query = req.getParameter("query");
            String points = req.getParameter("points");
            String action = req.getParameter("action");

            if ("health".equals(action)) {
                out.println("{\"status\":\"ok\",\"service\":\"myApiServlet\",\"timestamp\":" + System.currentTimeMillis() + "}");
                return;
            }

            String json = executeBQL(query, points);
            out.println(json);

        } catch (Exception e) {
            resp.setStatus(500);
            out.println("{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}");
            e.printStackTrace();
        }
    }

    private void setCORSHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private String executeBQL(String customQuery, String pointFilter) {
        try {
            String bql = (customQuery != null && !customQuery.isEmpty())
                    ? customQuery
                    : "station:|slot:/|bql:select name, out.value, slotPath from control:ControlPoint";

            BOrd ord = BOrd.make(bql);
            BObject resolved = ord.resolve(Sys.getStation(), null).get();

            if (!(resolved instanceof BITable)) {
                return "{\"success\":false,\"error\":\"Result is not a table\"}";
            }

            BITable result = (BITable) resolved;
            StringBuilder json = new StringBuilder();
            json.append("{\"success\":true,\"timestamp\":").append(System.currentTimeMillis()).append(",\"data\":[");

            Column[] cols = result.getColumns().list();
            TableCursor cursor = result.cursor();
            boolean first = true;
            int count = 0;

            while (cursor.next()) {
                if (pointFilter != null && !pointFilter.isEmpty()) {
                    String pointName = cursor.cell(cols[0]).toString(); // Assumes name is 1st col
                    if (!matchesFilter(pointName, pointFilter)) continue;
                }

                if (!first) json.append(",");
                json.append("{");

                for (int i = 0; i < cols.length; i++) {
                    if (i > 0) json.append(",");
                    json.append("\"").append(cols[i].getName()).append("\":\"")
                            .append(escapeJson(cursor.cell(cols[i]).toString())).append("\"");
                }
                json.append("}");
                first = false;
                count++;
            }

            json.append("],\"count\":").append(count).append("}");
            return json.toString();

        } catch (Exception e) {
            return "{\"success\":false,\"error\":\"" + escapeJson(e.getMessage()) + "\"}";
        }
    }

    private boolean matchesFilter(String pointName, String filter) {
        if (filter == null || pointName == null) return true;
        String[] filters = filter.split(",");
        for (String f : filters) {
            if (pointName.toLowerCase().contains(f.trim().toLowerCase())) return true;
        }
        return false;
    }

    private String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}