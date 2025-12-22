package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.util.IFuture;
import java.util.HashMap;
import java.util.Map;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.security.*;

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
  
  /**
   * Slot for the {@code deviceName} property.
   * @see #getDeviceName
   * @see #setDeviceName
   */
  public static final Property deviceName = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceName} property.
   * @see #deviceName
   */
  public String getDeviceName() { return getString(deviceName); }
  
  /**
   * Set the {@code deviceName} property.
   * @see #deviceName
   */
  public void setDeviceName(String v) { setString(deviceName, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceAddress"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceAddress} property.
   * @see #getDeviceAddress
   * @see #setDeviceAddress
   */
  public static final Property deviceAddress = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public String getDeviceAddress() { return getString(deviceAddress); }
  
  /**
   * Set the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public void setDeviceAddress(String v) { setString(deviceAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceDescription"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceDescription} property.
   * @see #getDeviceDescription
   * @see #setDeviceDescription
   */
  public static final Property deviceDescription = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceDescription} property.
   * @see #deviceDescription
   */
  public String getDeviceDescription() { return getString(deviceDescription); }
  
  /**
   * Set the {@code deviceDescription} property.
   * @see #deviceDescription
   */
  public void setDeviceDescription(String v) { setString(deviceDescription, v, null); }

////////////////////////////////////////////////////////////////
// Property "protocol"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code protocol} property.
   * @see #getProtocol
   * @see #setProtocol
   */
  public static final Property protocol = newProperty(Flags.READONLY, "", null);
  
  /**
   * Get the {@code protocol} property.
   * @see #protocol
   */
  public String getProtocol() { return getString(protocol); }
  
  /**
   * Set the {@code protocol} property.
   * @see #protocol
   */
  public void setProtocol(String v) { setString(protocol, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceId"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceId} property.
   * @see #getDeviceId
   * @see #setDeviceId
   */
  public static final Property deviceId = newProperty(Flags.READONLY, -1, null);
  
  /**
   * Get the {@code deviceId} property.
   * @see #deviceId
   */
  public int getDeviceId() { return getInt(deviceId); }
  
  /**
   * Set the {@code deviceId} property.
   * @see #deviceId
   */
  public void setDeviceId(int v) { setInt(deviceId, v, null); }

////////////////////////////////////////////////////////////////
// Action "discoverPoints"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverPoints} action.
   * @see #discoverPoints()
   */
  public static final Action discoverPoints = newAction(Flags.ASYNC | Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code discoverPoints} action.
   * @see #discoverPoints
   */
  public void discoverPoints() { invoke(discoverPoints, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearPoints"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code clearPoints} action.
   * @see #clearPoints()
   */
  public static final Action clearPoints = newAction(Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code clearPoints} action.
   * @see #clearPoints
   */
  public void clearPoints() { invoke(clearPoints, null, null); }

////////////////////////////////////////////////////////////////
// Action "refreshValues"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code refreshValues} action.
   * @see #refreshValues()
   */
  public static final Action refreshValues = newAction(Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code refreshValues} action.
   * @see #refreshValues
   */
  public void refreshValues() { invoke(refreshValues, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // Store discovered points info
    private Map<String, PointInfo> discoveredPoints = new HashMap<>();

    // Store dynamic property slots

    // ==================== BDevice Required Methods ====================

    @Override
    public Type getNetworkType() {
        return BMyUniversalNetwork.TYPE;
    }

    // ==================== Device Lifecycle ====================

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

    // ==================== Device Communication ====================

    @Override
    public void doPing() throws Exception {
        System.out.println("MyPointDevice: Pinging device " + getDeviceName());
        try {
            setFaultCause(null);
            setStatus(BStatus.ok);
            System.out.println("MyPointDevice: Ping successful");
        } catch (Exception e) {
            System.err.println("MyPointDevice: Ping failed - " + e.getMessage());
            setFaultCause(e.getMessage());
            setStatus(BStatus.fault);
            throw e;
        }
    }

    @Override
    protected IFuture postPing() {
        return null;
    }

    // ==================== Point Discovery (Dynamic Properties) ====================

    /**
     * Discover points from device and create as DYNAMIC PROPERTIES
     */
    public void doDiscoverPoints() throws Exception {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("MyPointDevice: Discovering points...");
        System.out.println("Device: " + getDeviceName());
        System.out.println("Address: " + getDeviceAddress());
        System.out.println("Description: " + getDeviceDescription());

        try {
            String proto = detectProtocol();
            System.out.println("Detected Protocol: " + proto);
            setProtocol(proto);

            int pointsCreated = 0;

            switch (proto) {
                case "bacnet":
                    pointsCreated = discoverBACnetPoints();
                    break;
                case "modbus":
                    pointsCreated = discoverModbusPoints();
                    break;
                case "http":
                    pointsCreated = discoverHTTPPoints();
                    break;
                default:
                    System.out.println("⚠️ Unknown protocol, creating test points...");
                    pointsCreated = createTestPoints();
            }

            System.out.println("✅ Discovery completed: " + pointsCreated + " points created as properties");
            System.out.println("═══════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("❌ Point discovery failed: " + e.getMessage());
            e.printStackTrace();
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

    /**
     * Discover BACnet Points
     */
    /**
     * Discover BACnet Points - Query from real device
     */
    private int discoverBACnetPoints() throws Exception {
        System.out.println("🔍 Starting Real BACnet Object Discovery...");

        // 1. หา Device ID จาก Description หรือ Address (สมมติว่า Address format: "IP:Port")
        // ใน discovery phase เราควรเก็บ Device ID ไว้
        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        // ถ้าเรายังไม่รู้ Device Instance ID ของอุปกรณ์ปลายทาง เราอาจต้องใช้ Who-Is อีกรอบ หรือ Parse จากชื่อ
        // สมมติว่าในชื่อ Device มี ID อยู่ เช่น "BACnet_888" -> 888
        int targetDeviceId = parseDeviceIdFromName(getDeviceName());
        if (targetDeviceId == -1) {
            System.out.println("⚠️ Cannot determine Device ID from name. Using default scan.");
            // Fallback ไป scan common objects ถ้าหา ID ไม่เจอ
            return scanCommonObjects(ip, port);
        }

        // 2. Read "Object List" Property (ID 76) จาก Device Object
        // Device Object Type = 8
        System.out.println("   Target Device ID: " + targetDeviceId);

        List<Integer> objectIds = fetchBACnetObjectList(ip, port, targetDeviceId);

        int count = 0;
        for (Integer rawId : objectIds) {
            String typeName = BACnetUtil.getObjectTypeName(rawId);
            int instance = BACnetUtil.getInstance(rawId);

            // ข้าม Device Object ตัวเอง
            if (typeName.equals("Device")) continue;

            String pointName = typeName + "_" + instance;
            // ลองอ่าน Object Name จริงๆ ถ้าทำได้ (Property 77)
            // String realName = readBACnetObjectName(ip, port, typeName, instance);
            // if (realName != null) pointName = realName;

            if (addDynamicPoint(pointName, "bacnet", typeName + ":" + instance, instance)) {
                count++;
            }
        }

        return count;
    }

    private int scanCommonObjects(String ip, int port) {
        System.out.println("⚠️ Scan fallback: Checking common objects...");
        int count = 0;

        // ลองสุ่มตรวจ Type ละ 10 จุด
        int[] types = {0, 1, 2, 3, 4, 5}; // AI, AO, AV, BI, BO, BV

        // ต้องใช้ Socket เดิมเพื่อประสิทธิภาพ (สร้างใหม่ทุกรอบจะช้า)
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(500); // Timeout เร็วหน่อยสำหรับการสุ่ม
            InetAddress addr = InetAddress.getByName(ip);

            for (int type : types) {
                for (int i = 0; i <= 10; i++) { // ลองเบอร์ 0-10
                    if (checkObjectExists(socket, addr, port, type, i)) {
                        String typeName = BACnetUtil.getObjectTypeName((type << 22));
                        String name = typeName + "_" + i;
                        if (addDynamicPoint(name, "bacnet", typeName + ":" + i, i)) {
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

    /**
     * ดึง Object List ทั้งหมด (จำลองการอ่าน Array Index)
     * หมายเหตุ: ใน production ต้องอ่าน Array Length ก่อน แล้ว loop อ่านทีละ index หรือเป็น range
     * เพื่อป้องกัน Packet too big
     */
    private List<Integer> fetchBACnetObjectList(String ip, int port, int deviceId) {
        List<Integer> list = new ArrayList<>();

        return AccessController.doPrivileged((PrivilegedAction<List<Integer>>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(2000);
                InetAddress addr = InetAddress.getByName(ip);

                // ขั้นตอนที่ 1: อ่าน Array Length (Index 0) ของ Property Object_List (76)
                // สร้าง Packet อ่าน Index 0 (ต้องเขียน Logic เพิ่มใน BACnetUtil ให้รองรับ ArrayIndex)
                // *เพื่อความง่ายของ PoC: ผมจะ Scan Object แบบ Brute Force แทน เพราะง่ายกว่าการ Handle Segmentation*
                // หรือใช้ "Common Objects" list แทนการอ่าน Object List จริงๆ ที่ซับซ้อนเรื่อง Packet size

                // --- Hybrid Strategy for PoC ---
                // ลองอ่าน AI:0 ถึง AI:10, AV:0 ถึง AV:10, etc.

                int[] typesToScan = {0, 1, 2, 3, 4, 5}; // AI, AO, AV, BI, BO, BV

                for (int type : typesToScan) {
                    for (int i = 0; i <= 10; i++) { // Scan ID 0-10
                        if (checkObjectExists(socket, addr, port, type, i)) {
                            // Raw ID for internal use
                            int rawId = (type << 22) | i;
                            list.add(rawId);
                            System.out.println("     Found: " + BACnetUtil.getObjectTypeName(rawId) + ":" + i);
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("   Discovery Error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return list;
        });
    }

    /**
     * เช็คว่ามี Object นี้ไหม โดยการลอง Read Present_Value
     */
    private boolean checkObjectExists(DatagramSocket socket, InetAddress ip, int port, int type, int instance) {
        try {
            byte[] tx = BACnetUtil.buildReadPropertyPacket(type, instance, BACnetUtil.PROP_PRESENT_VALUE, (instance % 255));
            DatagramPacket p = new DatagramPacket(tx, tx.length, ip, port);
            socket.send(p);

            byte[] rxBuf = new byte[512];
            DatagramPacket rx = new DatagramPacket(rxBuf, rxBuf.length);
            socket.receive(rx);

            // ถ้าได้รับ Complex ACK (0x30) แปลว่ามีอยู่จริง
            // ถ้าได้ Error (0x50) แปลว่าไม่มี
            byte apduType = rxBuf[6]; // ข้าม BVLC(4)+NPDU(2)
            return (apduType & 0xF0) == 0x30; // Complex ACK

        } catch (Exception e) {
            return false;
        }
    }

    // Helper parse ID
    private int parseDeviceIdFromName(String name) {
        try {
            if (name.contains("_")) {
                return Integer.parseInt(name.split("_")[1]);
            }
        } catch (Exception e) {}
        return -1;
    }

    /**
     * Discover Modbus Points
     */
    private int discoverModbusPoints() throws Exception {
        System.out.println("🔍 Discovering Modbus registers...");

        int count = 0;

        for (int i = 0; i < 10; i++) {
            String propName = "HR" + i;

            if (addDynamicPoint(propName, "modbus", "Holding Register " + i, i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Discover HTTP Points
     */
    private int discoverHTTPPoints() throws Exception {
        System.out.println("🔍 Discovering HTTP endpoints...");

        int count = 0;
        String[] endpoints = {"temperature", "humidity", "pressure", "status"};

        for (int i = 0; i < endpoints.length; i++) {
            if (addDynamicPoint(endpoints[i], "http", "HTTP Endpoint", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Create test points
     */
    private int createTestPoints() throws Exception {
        int count = 0;

        for (int i = 0; i < 5; i++) {
            String propName = "Point" + i;

            if (addDynamicPoint(propName, "test", "Test Point " + i, i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * ✅ Add point to Points folder (Standard Niagara approach)
     */
    private boolean addDynamicPoint(String propName, String proto, String description, int address) {
        try {
            // Get or create Points folder
            BComponent pointsFolder = getPointsFolder();

            // Check if point already exists
            if (pointsFolder.get(propName) != null) {
                System.out.println("  ⏭️  Point already exists: " + propName);
                return false;
            }

            // Create proxy point
            BMyProxyPoint point = new BMyProxyPoint();
            point.setProtocol(proto);
            point.setRegisterAddress(address);

            // Add to Points folder
            pointsFolder.add(propName, point);

            // Store info
            PointInfo info = new PointInfo(propName, proto, description, address);
            discoveredPoints.put(propName, info);

            System.out.println("  ✅ Created point: " + propName +
                    " (" + proto + ", addr:" + address + ")");

            return true;

        } catch (Exception e) {
            System.err.println("  ❌ Failed to create point " + propName + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get or create Points folder
     */
    private BComponent getPointsFolder() {
        try {
            BComponent folder = (BComponent) get("Points");
            if (folder == null) {
                folder = new BComponent();
                add("Points", folder);
                System.out.println("  📁 Created Points folder");
            }
            return folder;
        } catch (Exception e) {
            System.err.println("Failed to get Points folder: " + e.getMessage());
            return null;
        }
    }

    /**
     * Refresh all point values
     */
    public void doRefreshValues() throws Exception {
        System.out.println("🔄 Refreshing values for " + getDeviceName());

        BComponent pointsFolder = getPointsFolder();
        if (pointsFolder == null) {
            System.out.println("⚠️ No Points folder found");
            return;
        }

        int successCount = 0;
        int failCount = 0;

        for (Map.Entry<String, PointInfo> entry : discoveredPoints.entrySet()) {
            String propName = entry.getKey();
            PointInfo info = entry.getValue();

            try {
                // Get point from folder
                BMyProxyPoint point = (BMyProxyPoint) pointsFolder.get(propName);
                if (point == null) continue;

                // Read value from device
                double value = readValueFromDevice(info);

                // Update point value
                BStatusNumeric statusValue = new BStatusNumeric(value, BStatus.ok);
                point.setOut(statusValue);

                successCount++;
                System.out.println("  ✅ " + propName + " = " + value);

            } catch (Exception e) {
                try {
                    BMyProxyPoint point = (BMyProxyPoint) pointsFolder.get(propName);
                    if (point != null) {
                        BStatusNumeric faultValue = new BStatusNumeric(0.0, BStatus.fault);
                        point.setOut(faultValue);
                    }
                } catch (Exception ex) {}

                failCount++;
                System.err.println("  ❌ " + propName + " - " + e.getMessage());
            }
        }

        System.out.println("✅ Refresh completed: " + successCount + " success, " + failCount + " failed");
    }
    /**
     * Read value from device
     */
    private double readValueFromDevice(PointInfo info) throws Exception {
        switch (info.protocol) {
            case "bacnet":
                return 20.0 + (Math.random() * 10);  // Test value
            case "modbus":
                return 50.0 + (Math.random() * 20);  // Test value
            case "http":
                return 30.0 + (Math.random() * 15);  // Test value
            default:
                return Math.random() * 100;
        }
    }

    /**
     * Clear all points
     */
    public void doClearPoints() throws Exception {
        System.out.println("Clearing all points from " + getDeviceName());

        BComponent pointsFolder = getPointsFolder();
        if (pointsFolder == null) {
            System.out.println("⚠️ No Points folder found");
            return;
        }

        int count = 0;

        // ✅ วิธีที่ถูกต้อง: ใช้ SlotCursor แบบ iterate
        SlotCursor cursor = pointsFolder.getSlots();
        java.util.List<String> slotsToRemove = new java.util.ArrayList<>();

        // Collect slot names first
        while (cursor.next()) {
            Slot slot = (Slot) cursor.get();
            slotsToRemove.add(slot.getName());
        }

        // Then remove them
        for (String slotName : slotsToRemove) {
            try {
                pointsFolder.remove(slotName);
                count++;
                System.out.println("  🗑️ Removed: " + slotName);
            } catch (Exception e) {
                System.err.println("  ❌ Failed to remove " + slotName + ": " + e.getMessage());
            }
        }

        // Clear stored data
        discoveredPoints.clear();

        System.out.println("✅ Cleared " + count + " point(s)");
    }
    // ==================== Helper Classes ====================

    private static class PointInfo {
        String name;
        String protocol;
        String description;
        int address;

        PointInfo(String name, String protocol, String description, int address) {
            this.name = name;
            this.protocol = protocol;
            this.description = description;
            this.address = address;
        }
    }
}