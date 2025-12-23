package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.util.IFuture;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.security.*;
import javax.baja.control.*;  // <--- เพิ่มบรรทัดนี้ครับ
@NiagaraType
@NiagaraProperty(name = "deviceName", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceAddress", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceDescription", type = "String", defaultValue = "")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraProperty(name = "deviceId", type = "int", defaultValue = "-1", flags = Flags.READONLY)
@NiagaraAction(name = "discoverPoints", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(name = "clearPoints", flags = Flags.SUMMARY)
@NiagaraAction(name = "refreshValues", flags = Flags.SUMMARY)
public class BMyPointDevice extends BDevice {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyPointDevice(733298069)1.0$ @*/
    /* Generated Fri Dec 19 13:56:48 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "deviceName"
////////////////////////////////////////////////////////////////

    public static final Property deviceName = newProperty(0, "", null);
    public String getDeviceName() { return getString(deviceName); }
    public void setDeviceName(String v) { setString(deviceName, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceAddress"
////////////////////////////////////////////////////////////////

    public static final Property deviceAddress = newProperty(0, "", null);
    public String getDeviceAddress() { return getString(deviceAddress); }
    public void setDeviceAddress(String v) { setString(deviceAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceDescription"
////////////////////////////////////////////////////////////////

    public static final Property deviceDescription = newProperty(0, "", null);
    public String getDeviceDescription() { return getString(deviceDescription); }
    public void setDeviceDescription(String v) { setString(deviceDescription, v, null); }

////////////////////////////////////////////////////////////////
// Property "protocol"
////////////////////////////////////////////////////////////////

    public static final Property protocol = newProperty(Flags.READONLY, "", null);
    public String getProtocol() { return getString(protocol); }
    public void setProtocol(String v) { setString(protocol, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceId"
////////////////////////////////////////////////////////////////

    public static final Property deviceId = newProperty(Flags.READONLY, -1, null);
    public int getDeviceId() { return getInt(deviceId); }
    public void setDeviceId(int v) { setInt(deviceId, v, null); }

////////////////////////////////////////////////////////////////
// Action "discoverPoints"
////////////////////////////////////////////////////////////////

    public static final Action discoverPoints = newAction(Flags.ASYNC | Flags.SUMMARY, null);
    public void discoverPoints() { invoke(discoverPoints, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearPoints"
////////////////////////////////////////////////////////////////

    public static final Action clearPoints = newAction(Flags.SUMMARY, null);
    public void clearPoints() { invoke(clearPoints, null, null); }

////////////////////////////////////////////////////////////////
// Action "refreshValues"
////////////////////////////////////////////////////////////////

    public static final Action refreshValues = newAction(Flags.SUMMARY, null);
    public void refreshValues() { invoke(refreshValues, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    @Override
    public Type getNetworkType() {
        return BMyUniversalNetwork.TYPE;
    }

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("MyPointDevice: Device started - " + getDeviceName());
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("MyPointDevice: Device stopped - " + getDeviceName());
        super.stopped();
    }

    @Override
    public void doPing() throws Exception {
        try {
            setFaultCause(null);
            setStatus(BStatus.ok);
        } catch (Exception e) {
            setFaultCause(e.getMessage());
            setStatus(BStatus.fault);
            throw e;
        }
    }

    @Override
    protected IFuture postPing() { return null; }

    // ==================== Point Discovery Logic ====================

    public void doDiscoverPoints() throws Exception {
        System.out.println("-------------------------------------------");
        System.out.println("Discovering points for: " + getDeviceName());

        try {
            String proto = detectProtocol();
            setProtocol(proto);
            int pointsCreated = 0;

            switch (proto) {
                case "bacnet": pointsCreated = discoverBACnetPoints(); break;
                case "modbus": pointsCreated = discoverModbusPoints(); break;
                case "http":   pointsCreated = discoverHTTPPoints(); break;
                default:       pointsCreated = createTestPoints();
            }

            System.out.println("Discovery completed: " + pointsCreated + " points created");
            System.out.println("-------------------------------------------");
        } catch (Exception e) {
            System.err.println("Discovery failed: " + e.getMessage());
            throw e;
        }
    }

    private String detectProtocol() {
        String desc = getDeviceDescription().toLowerCase();
        if (desc.contains("bacnet")) return "bacnet";
        if (desc.contains("modbus")) return "modbus";
        if (desc.contains("http")) return "http";
        return "unknown";
    }

    // --- BACnet Discovery (Smart Type Selection) ---
    // แก้ไขฟังก์ชันนี้ใน BMyPointDevice.java

    private int discoverBACnetPoints() throws Exception {
        System.out.println("Starting BACnet Discovery...");
        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        int targetDeviceId = parseDeviceIdFromName(getDeviceName());

        // พยายามดึง Object List (ซึ่งตอนนี้คืนค่าว่างเสมอ)
        List<Integer> objectIds = fetchBACnetObjectList(ip, port, targetDeviceId);

        // ✅ [แก้ไข] ถ้าดึง List ไม่ได้ ให้เปลี่ยนไปใช้โหมดสแกนหา (scanCommonObjects) แทนทันที
        if (objectIds.isEmpty()) {
            System.out.println("Object List is empty (Not implemented). Switching to Fallback Scan...");
            return scanCommonObjects(ip, port);
        }

        int count = 0;
        for (Integer rawId : objectIds) {
            String typeName = BACnetUtil.getObjectTypeName(rawId);
            int instance = BACnetUtil.getInstance(rawId);
            if (typeName.equals("Device")) continue;

            String pointName = typeName + "_" + instance;

            Type targetType = BMyProxyPoint.TYPE;
            if (typeName.contains("Binary")) {
                targetType = BMyBooleanPoint.TYPE;
            } else if (typeName.contains("Multi")) {
                targetType = BMyEnumPoint.TYPE;
            }

            if (addDynamicPoint(pointName, "bacnet", instance, targetType)) {
                if (targetType == BMyProxyPoint.TYPE) {
                    setPointRegisterType(pointName, typeName);
                }
                count++;
            }
        }
        return count;
    }

    private int scanCommonObjects(String ip, int port) {
        System.out.println("Scan fallback...");
        int count = 0;
        // AI=0, AO=1, AV=2 -> Numeric
        // BI=3, BO=4, BV=5 -> Boolean
        int[] types = {0, 1, 2, 3, 4, 5};

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(500);
            InetAddress addr = InetAddress.getByName(ip);

            for (int type : types) {
                for (int i = 0; i <= 5; i++) { // Scan 0-5
                    if (checkObjectExists(socket, addr, port, type, i)) {
                        String typeName = BACnetUtil.getObjectTypeName((type << 22));
                        String name = typeName + "_" + i;

                        // ✅ เลือก Type
                        Type targetType = (type >= 3) ? BMyBooleanPoint.TYPE : BMyProxyPoint.TYPE;

                        if (addDynamicPoint(name, "bacnet", i, targetType)) {
                            if (targetType == BMyProxyPoint.TYPE) {
                                setPointRegisterType(name, typeName);
                            }
                            count++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Scan error: " + e.getMessage());
        } finally {
            if (socket != null) socket.close();
        }
        return count;
    }

    // --- Modbus Discovery (Smart Type Selection) ---
    private int discoverModbusPoints() throws Exception {
        System.out.println("Discovering Modbus registers...");
        int count = 0;

        // 1. Discover Holding Registers (Numeric)
        for (int i = 0; i < 5; i++) {
            String propName = "HR" + i;
            // HR -> Numeric Point
            if (addDynamicPoint(propName, "modbus", i, BMyProxyPoint.TYPE)) {
                count++;
            }
        }

        // 2. Discover Coils (Boolean) - ลองสมมติว่ามี
        for (int i = 0; i < 5; i++) {
            String propName = "Coil" + i;
            // Coil -> Boolean Point
            if (addDynamicPoint(propName, "modbus", i, BMyBooleanPoint.TYPE)) {
                // ต้อง set registerType เป็น coil ด้วย ไม่งั้นมันจะไปอ่าน Holding
                setPointRegisterType(propName, "coil");
                count++;
            }
        }

        return count;
    }

    private int discoverHTTPPoints() throws Exception {
        System.out.println("Discovering HTTP endpoints...");
        int count = 0;
        String[] endpoints = {"temp", "humid"};
        for (int i = 0; i < endpoints.length; i++) {
            if (addDynamicPoint(endpoints[i], "http", i, BMyProxyPoint.TYPE)) {
                count++;
            }
        }
        return count;
    }

    private int createTestPoints() throws Exception {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (addDynamicPoint("Point" + i, "test", i, BMyProxyPoint.TYPE)) {
                count++;
            }
        }
        return count;
    }

    // ==================== Core: Add Point by Type ====================

    private boolean addDynamicPoint(String propName, String proto, int address, Type pointType) {
        try {
            BComponent pointsFolder = getPointsFolder();
            // เช็คว่ามี Point ชื่อนี้อยู่แล้วหรือไม่
            if (pointsFolder.get(propName) != null) {
                System.out.println("Point exists: " + propName);
                return false;
            }

            // ✅ แก้ไข 1: สร้าง Instance ใหม่ด้วยวิธีของ Niagara (Baja)
            // เอาตัวต้นแบบ (Instance) มา Copy สร้างตัวใหม่
            BControlPoint point = (BControlPoint) ((BValue) pointType.getInstance()).newCopy();
            // Set ค่าพื้นฐาน
            // หมายเหตุ: การใช้ set(String, BValue) จะทำงานได้ก็ต่อเมื่อ Point นั้นมี Property ชื่อนี้อยู่จริง
            try {
                point.set("protocol", BString.make(proto));
                point.set("registerAddress", BInteger.make(address));
            } catch (Exception e) {
                // กันเหนียว กรณี Point บางประเภทไม่มี Property พวกนี้
                System.out.println("Warning: Could not set properties on " + propName);
            }

            pointsFolder.add(propName, point);

            // ✅ แก้ไข 2: ใช้ getTypeName แทน getSimpleName
            System.out.println("Created " + pointType.getTypeName() + ": " + propName);
            return true;

        } catch (Exception e) {
            System.err.println("Failed to create point " + propName + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    // --- Helpers ---

    private void setPointRegisterType(String propName, String typeName) {
        try {
            BComponent pointsFolder = getPointsFolder();
            BComplex point = (BComplex) pointsFolder.get(propName);
            if (point != null) {
                // ใช้ set() เพื่อความชัวร์ เพราะบาง Class อาจไม่มี setter นี้
                try { point.set("registerType", BString.make(typeName)); } catch(Exception e){}
            }
        } catch (Exception e) {}
    }

    private BComponent getPointsFolder() {
        try {
            BComponent folder = (BComponent) get("Points");
            if (folder == null) {
                folder = new BComponent();
                add("Points", folder);
            }
            return folder;
        } catch (Exception e) { return null; }
    }

    public void doRefreshValues() throws Exception {
        System.out.println("RefreshValues is disabled.");
    }

    public void doClearPoints() throws Exception {
        System.out.println("Clearing all points...");
        BComponent pointsFolder = getPointsFolder();
        if (pointsFolder == null) return;

        SlotCursor cursor = pointsFolder.getSlots();
        List<String> toRemove = new ArrayList<>();

        while (cursor.next()) {
            toRemove.add(cursor.slot().getName());
        }

        for (String s : toRemove) {
            pointsFolder.remove(s);
        }
        System.out.println("Cleared " + toRemove.size() + " points.");
    }

    // ... (BACnet Util helpers: parseDeviceId, fetchList, checkObject - keep same as before) ...
    private int parseDeviceIdFromName(String name) {
        try { if (name.contains("_")) return Integer.parseInt(name.split("_")[1]); } catch (Exception e) {} return -1;
    }

    private List<Integer> fetchBACnetObjectList(String ip, int port, int deviceId) {
        return AccessController.doPrivileged((PrivilegedAction<List<Integer>>) () -> new ArrayList<>()); // Placeholder
    }

    private boolean checkObjectExists(DatagramSocket s, InetAddress a, int p, int t, int i) {
        try {
            byte[] tx = BACnetUtil.buildReadPropertyPacket(t, i, BACnetUtil.PROP_PRESENT_VALUE, (i%255));
            s.send(new DatagramPacket(tx, tx.length, a, p));
            byte[] rx = new byte[512];
            DatagramPacket pkt = new DatagramPacket(rx, rx.length);
            s.receive(pkt);
            return (rx[6] & 0xF0) == 0x30;
        } catch (Exception e) { return false; }
    }
}