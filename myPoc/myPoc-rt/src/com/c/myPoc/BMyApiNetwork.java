package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.collection.*;
import javax.baja.driver.*; // ✅ Import นี้สำคัญ
import javax.baja.status.*;
import java.io.*;
import java.net.*;
import java.security.*;

@NiagaraType
public class BMyApiNetwork extends BDeviceNetwork {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyApiNetwork(2979906276)1.0$ @*/
    /* Generated Wed Dec 10 11:08:24 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyApiNetwork.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private ServerSocket serverSocket;
    private Thread serverThread;
    private volatile boolean isRunning = false;
    private int PORT = 8088;

    // -----------------------------------------------------------------
    // ✅ ส่วนที่ต้อง Implement ตามกฎของ BDeviceNetwork
    // -----------------------------------------------------------------

    @Override
    public Type getDeviceType() {
        // คืนค่า Type ของ Device
        return BDevice.TYPE;
    }

    @Override
    public Type getDeviceFolderType() {
        // ✅ เพิ่มตัวนี้ครับ: คืนค่า Type ของ Folder ที่ใช้เก็บ Device
        return BDeviceFolder.TYPE;
    }

    // -----------------------------------------------------------------

    @Override
    public void started() throws Exception {
        super.started();
        if (getEnabled()) {
            startServer();
        } else {
            setStatus(BStatus.disabled);
        }
    }

    @Override
    public void stopped() throws Exception {
        stopServer();
        super.stopped();
    }

    @Override
    public void changed(Property p, Context cx) {
        super.changed(p, cx);
        if (p.equals(enabled)) {
            if (getEnabled()) {
                startServer();
            } else {
                stopServer();
            }
        }
    }

    private void startServer() {
        if (isRunning) return;

        setStatus(BStatus.down);
        isRunning = true;

        serverThread = new Thread(() -> {
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                try {
                    serverSocket = new ServerSocket(PORT);
                    System.out.println("✅ MyApiNetwork Started on port " + PORT);

                    setStatus(BStatus.ok);

                    while (isRunning) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            handleClient(clientSocket);
                        } catch (IOException e) {
                            if(isRunning) System.out.println("Accept Error: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    System.out.println("❌ Cannot Bind Port " + PORT + ": " + e.getMessage());
                    setStatus(BStatus.fault);
                    isRunning = false;
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
        } catch (IOException e) { /* ignore */ }

        setStatus(BStatus.disabled);
        System.out.println("🛑 MyApiNetwork Stopped");
    }

    private void handleClient(Socket client) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true)
        ) {
            String line = in.readLine();

            String jsonResponse = runBQLScanner();

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println("Access-Control-Allow-Origin: *");
            out.println("Connection: close");
            out.println("");
            out.println(jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { client.close(); } catch (IOException e) {}
        }
    }

    private String runBQLScanner() {
        try {
            String bql = "station:|slot:/|bql:select name, out.value, slotPath from control:ControlPoint";
            BOrd ord = BOrd.make(bql);
            BITable result = (BITable) ord.resolve(Sys.getStation(), null).get();

            StringBuilder json = new StringBuilder("[");
            Column[] cols = result.getColumns().list();
            TableCursor cursor = result.cursor();
            boolean first = true;

            while (cursor.next()) {
                if (!first) json.append(",");
                json.append("{");
                for (int i = 0; i < cols.length; i++) {
                    if (i > 0) json.append(",");
                    String colName = cols[i].getName();
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