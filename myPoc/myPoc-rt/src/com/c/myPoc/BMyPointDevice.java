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
        System.out.println("-------------------------------------------");
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
                    System.out.println("Unknown protocol, creating test points...");
                    pointsCreated = createTestPoints();
            }

            System.out.println("Discovery completed: " + pointsCreated + " points created as properties");
            System.out.println("-------------------------------------------");

        } catch (Exception e) {
            System.err.println("Point discovery failed: " + e.getMessage());
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
     * Discover BACnet Points - Query from real device
     */
    private int discoverBACnetPoints() throws Exception {
        System.out.println("Starting Real BACnet Object Discovery...");

        // 1. Determine Device ID
        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        int targetDeviceId = parseDeviceIdFromName(getDeviceName());
        if (targetDeviceId == -1) {
            System.out.println("Cannot determine Device ID from name. Using default scan.");
            return scanCommonObjects(ip, port);
        }

        System.out.println("   Target Device ID: " + targetDeviceId);

        // 2. Read Object List
        List<Integer> objectIds = fetchBACnetObjectList(ip, port, targetDeviceId);

        int count = 0;
        for (Integer rawId : objectIds) {
            String typeName = BACnetUtil.getObjectTypeName(rawId);
            int instance = BACnetUtil.getInstance(rawId);

            // Skip Device Object
            if (typeName.equals("Device")) continue;

            String pointName = typeName + "_" + instance;
            // Note: Could read Object Name (Prop 77) here for better naming

            // Create Point using manual method to ensure properties are set correctly
            if (addDynamicPoint(pointName, "bacnet", instance)) {
                // Ensure registerType is set for BACnet points
                setPointRegisterType(pointName, typeName);
                count++;
            }
        }

        return count;
    }

    private int scanCommonObjects(String ip, int port) {
        System.out.println("Scan fallback: Checking common objects...");
        int count = 0;

        int[] types = {0, 1, 2, 3, 4, 5}; // AI, AO, AV, BI, BO, BV

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(500);
            InetAddress addr = InetAddress.getByName(ip);

            for (int type : types) {
                for (int i = 0; i <= 10; i++) { // Scan ID 0-10
                    if (checkObjectExists(socket, addr, port, type, i)) {
                        String typeName = BACnetUtil.getObjectTypeName((type << 22));
                        String name = typeName + "_" + i;
                        if (addDynamicPoint(name, "bacnet", i)) {
                            setPointRegisterType(name, typeName);
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

    private List<Integer> fetchBACnetObjectList(String ip, int port, int deviceId) {
        List<Integer> list = new ArrayList<>();

        return AccessController.doPrivileged((PrivilegedAction<List<Integer>>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(2000);
                InetAddress addr = InetAddress.getByName(ip);

                // Hybrid Strategy for PoC: Scan common ranges
                int[] typesToScan = {0, 1, 2, 3, 4, 5}; // AI, AO, AV, BI, BO, BV

                for (int type : typesToScan) {
                    for (int i = 0; i <= 10; i++) { // Scan ID 0-10
                        if (checkObjectExists(socket, addr, port, type, i)) {
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

    private boolean checkObjectExists(DatagramSocket socket, InetAddress ip, int port, int type, int instance) {
        try {
            byte[] tx = BACnetUtil.buildReadPropertyPacket(type, instance, BACnetUtil.PROP_PRESENT_VALUE, (instance % 255));
            DatagramPacket p = new DatagramPacket(tx, tx.length, ip, port);
            socket.send(p);

            byte[] rxBuf = new byte[512];
            DatagramPacket rx = new DatagramPacket(rxBuf, rxBuf.length);
            socket.receive(rx);

            byte apduType = rxBuf[6];
            return (apduType & 0xF0) == 0x30; // Complex ACK

        } catch (Exception e) {
            return false;
        }
    }

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
        System.out.println("Discovering Modbus registers...");

        int count = 0;

        for (int i = 0; i < 10; i++) {
            String propName = "HR" + i;
            if (addDynamicPoint(propName, "modbus", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Discover HTTP Points
     */
    private int discoverHTTPPoints() throws Exception {
        System.out.println("Discovering HTTP endpoints...");

        int count = 0;
        String[] endpoints = {"temperature", "humidity", "pressure", "status"};

        for (int i = 0; i < endpoints.length; i++) {
            if (addDynamicPoint(endpoints[i], "http", i)) {
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
            if (addDynamicPoint(propName, "test", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Add point to Points folder
     */
    private boolean addDynamicPoint(String propName, String proto, int address) {
        try {
            BComponent pointsFolder = getPointsFolder();

            if (pointsFolder.get(propName) != null) {
                System.out.println("   Point already exists: " + propName);
                return false;
            }

            BMyProxyPoint point = new BMyProxyPoint();
            point.setProtocol(proto);
            point.setRegisterAddress(address);

            pointsFolder.add(propName, point);

            System.out.println(" Created point: " + propName + " (" + proto + ", addr:" + address + ")");
            return true;

        } catch (Exception e) {
            System.err.println("  Failed to create point " + propName + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Helper to set register type after creation (for BACnet)
     */
    private void setPointRegisterType(String propName, String typeName) {
        try {
            BComponent pointsFolder = getPointsFolder();
            BMyProxyPoint point = (BMyProxyPoint) pointsFolder.get(propName);
            if (point != null) {
                point.setRegisterType(typeName);
            }
        } catch (Exception e) {}
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
                System.out.println("   Created Points folder");
            }
            return folder;
        } catch (Exception e) {
            System.err.println("Failed to get Points folder: " + e.getMessage());
            return null;
        }
    }

    /**
     * Refresh all point values
     * (Deprecated: ProxyPoint handles polling independently now)
     */
    public void doRefreshValues() throws Exception {
        System.out.println("Refreshing values triggered (Skipped - ProxyPoints handle their own polling)");
    }

    /**
     * Clear all points
     */
    public void doClearPoints() throws Exception {
        System.out.println("Clearing all points from " + getDeviceName());

        BComponent pointsFolder = getPointsFolder();
        if (pointsFolder == null) {
            System.out.println("No Points folder found");
            return;
        }

        int count = 0;
        SlotCursor cursor = pointsFolder.getSlots();
        java.util.List<String> slotsToRemove = new java.util.ArrayList<>();

        while (cursor.next()) {
            Slot slot = (Slot) cursor.get();
            slotsToRemove.add(slot.getName());
        }

        for (String slotName : slotsToRemove) {
            try {
                pointsFolder.remove(slotName);
                count++;
                System.out.println("  Removed: " + slotName);
            } catch (Exception e) {
                System.err.println("  Failed to remove " + slotName + ": " + e.getMessage());
            }
        }

        System.out.println("Cleared " + count + " point(s)");
    }
}