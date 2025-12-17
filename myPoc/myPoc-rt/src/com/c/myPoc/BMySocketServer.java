package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.collection.*;
import java.io.*;
import java.net.*;
import java.security.*; // ✅ เพิ่ม Import นี้สำคัญมากสำหรับการขอสิทธิ์เปิด Port

@NiagaraType
public class BMySocketServer extends BComponent {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMySocketServer(2979906276)1.0$ @*/
    /* Generated Wed Dec 10 11:08:24 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMySocketServer.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private ServerSocket serverSocket;
    private Thread serverThread;

    // ✅ เพิ่ม volatile เพื่อความปลอดภัยเมื่อทำงานหลาย Thread
    private volatile boolean isRunning = false;

    // ตั้งค่า Port ที่จะเปิด (อย่าให้ซ้ำกับ Niagara หรือ Windows)
    private int PORT = 8088;

    @Override
    public void started() throws Exception {
        super.started();
        startServer();
    }

    @Override
    public void stopped() throws Exception {
        stopServer();
        super.stopped();
    }

    private void startServer() {
        if (isRunning) return;
        isRunning = true;

        // รันใน Thread แยก เพื่อไม่ให้ Niagara ค้าง
        serverThread = new Thread(() -> {

            // ✅ ใช้ AccessController.doPrivileged เพื่อขอสิทธิ์เปิด Port (แก้ Access Denied)
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                try {
                    serverSocket = new ServerSocket(PORT);
                    System.out.println("✅ MySocketServer started on port " + PORT);

                    while (isRunning) {
                        try {
                            // รอคนเชื่อมต่อเข้ามา...
                            Socket clientSocket = serverSocket.accept();
                            handleClient(clientSocket);
                        } catch (IOException e) {
                            if(isRunning) System.out.println("Accept Error: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Server Start Error: " + e.getMessage());
                }
                return null;
            });

        });

        serverThread.start();
    }

    private void stopServer() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void handleClient(Socket client) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            // 1. อ่าน Request (อ่านทิ้งไปเพื่อให้ Socket ขยับ)
            String line = in.readLine();
            // System.out.println("Request: " + line); // Uncomment ถ้าอยากดู Log

            // 2. เตรียมข้อมูล JSON
            String jsonResponse = runBQLScanner();

            // 3. สร้าง HTTP Response Header
            out.println("HTTP/1.1 200 OK");
            // ✅ เพิ่ม charset=UTF-8 เพื่อรองรับภาษาไทยและอักขระพิเศษ
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println("Access-Control-Allow-Origin: *"); // CORS ให้ React ยิงเข้ามาได้
            out.println("Connection: close");
            out.println(""); // บรรทัดว่างคั่น Header

            // 4. ส่ง JSON
            out.println(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // ปิด Socket ของ Client ทันทีเมื่อส่งเสร็จ
            try { client.close(); } catch (IOException e) {}
        }
    }

    // Logic การดึงข้อมูล
    private String runBQLScanner() {
        try {
            String bql = "station:|slot:/|bql:select name, out.value, slotPath from control:ControlPoint";
            BOrd ord = BOrd.make(bql);
            BITable result = (BITable) ord.resolve(Sys.getStation(), null).get();

            StringBuilder json = new StringBuilder("[");

            // ✅ แก้ไข: ใช้ getColumns() เฉยๆ ไม่ต้องมี .list() เพราะมันคืนค่าเป็น Column[] อยู่แล้ว
            Column[] cols = result.getColumns().list();

            TableCursor cursor = result.cursor();
            boolean first = true;

            while (cursor.next()) {
                if (!first) json.append(",");
                json.append("{");
                for (int i = 0; i < cols.length; i++) {
                    if (i > 0) json.append(",");

                    String colName = cols[i].getName();
                    // ดึงค่าและ Escape เครื่องหมายคำพูด
                    String val = cursor.cell(cols[i]).toString().replace("\"", "\\\"");

                    json.append("\"").append(colName).append("\":\"").append(val).append("\"");
                }
                json.append("}");
                first = false;
            }
            json.append("]");
            return json.toString();
        } catch (Exception e) {
            return "[{\"error\":\"" + e.getMessage() + "\"}]";
        }
    }
}