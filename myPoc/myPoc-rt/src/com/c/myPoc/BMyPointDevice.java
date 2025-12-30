package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.util.IFuture;
import javax.baja.control.*;
import java.net.*;
import java.util.*;
import java.security.*;

@NiagaraType
@NiagaraProperty(name = "deviceName", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceAddress", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceDescription", type = "String", defaultValue = "")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraProperty(name = "deviceId", type = "int", defaultValue = "-1", flags = Flags.READONLY)
@NiagaraProperty(name = "useCOV", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "covLifetime", type = "int", defaultValue = "3600")
@NiagaraAction(name = "discoverPoints", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(name = "subscribeCOV", flags = Flags.SUMMARY)
@NiagaraAction(name = "unsubscribeCOV", flags = Flags.SUMMARY)
public class BMyPointDevice extends BDevice {


    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyPointDevice(3720370310)1.0$ @*/
    /* Generated Fri Dec 26 13:38:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Property "useCOV"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code useCOV} property.
     * @see #getUseCOV
     * @see #setUseCOV
     */
    public static final Property useCOV = newProperty(0, true, null);

    /**
     * Get the {@code useCOV} property.
     * @see #useCOV
     */
    public boolean getUseCOV() { return getBoolean(useCOV); }

    /**
     * Set the {@code useCOV} property.
     * @see #useCOV
     */
    public void setUseCOV(boolean v) { setBoolean(useCOV, v, null); }

////////////////////////////////////////////////////////////////
// Property "covLifetime"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code covLifetime} property.
     * @see #getCovLifetime
     * @see #setCovLifetime
     */
    public static final Property covLifetime = newProperty(0, 3600, null);

    /**
     * Get the {@code covLifetime} property.
     * @see #covLifetime
     */
    public int getCovLifetime() { return getInt(covLifetime); }

    /**
     * Set the {@code covLifetime} property.
     * @see #covLifetime
     */
    public void setCovLifetime(int v) { setInt(covLifetime, v, null); }

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
// Action "subscribeCOV"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code subscribeCOV} action.
     * @see #subscribeCOV()
     */
    public static final Action subscribeCOV = newAction(Flags.SUMMARY, null);

    /**
     * Invoke the {@code subscribeCOV} action.
     * @see #subscribeCOV
     */
    public void subscribeCOV() { invoke(subscribeCOV, null, null); }

////////////////////////////////////////////////////////////////
// Action "unsubscribeCOV"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code unsubscribeCOV} action.
     * @see #unsubscribeCOV()
     */
    public static final Action unsubscribeCOV = newAction(Flags.SUMMARY, null);

    /**
     * Invoke the {@code unsubscribeCOV} action.
     * @see #unsubscribeCOV
     */
    public void unsubscribeCOV() { invoke(unsubscribeCOV, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearPoints" (MANUAL DEFINITION)
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code clearPoints} action with Custom Facets.
     */
    public static final Action clearPoints = newAction(
            Flags.SUMMARY,
            BBoolean.FALSE,
            BFacets.make(
                    BFacets.TRUE_TEXT, BString.make("Yes, Clear All Points"),
                    BFacets.FALSE_TEXT, BString.make("Cancel")
            )
    );

    /**
     * Invoke the {@code clearPoints} action.
     * @see #clearPoints
     */
    public void clearPoints(BBoolean confirm) { invoke(clearPoints, confirm, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== COV Management ====================

    private Thread covListenerThread;
    private DatagramSocket covSocket;
    private volatile boolean isCOVRunning = false;
    private Map<String, Integer> covSubscriptions = new HashMap<>();
    private int processId = (int) (Math.random() * 65535);
    private int covPort = 0;

    // ==================== Lifecycle ====================

    @Override
    public Type getNetworkType() {
        return BMyUniversalNetwork.TYPE;
    }

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("MyPointDevice: Device started - " + getDeviceName());
        setCovStatus("Disabled (Polling only)");
    }

    @Override
    public void stopped() throws Exception {
        stopCOVListener();
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

    // ==================== Point Discovery ====================

    public void doDiscoverPoints() throws Exception {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("🔍 Discovering points for: " + getDeviceName());
        System.out.println("   Address: " + getDeviceAddress());
        System.out.println("   Description: " + getDeviceDescription());
        System.out.println("═══════════════════════════════════════════");

        try {
            String proto = detectProtocol();
            System.out.println("✓ Detected protocol: " + proto);
            setProtocol(proto);

            int pointsCreated = 0;

            switch (proto) {
                case "bacnet":
                    System.out.println("→ Starting BACnet discovery...");
                    pointsCreated = discoverBACnetPointsEnhanced();
                    break;
                case "modbus":
                    System.out.println("→ Starting Modbus discovery...");
                    pointsCreated = discoverModbusPoints();
                    break;
                case "http":
                    System.out.println("→ Starting HTTP discovery...");
                    pointsCreated = discoverHTTPPoints();
                    break;
                default:
                    System.out.println("⚠️  Unknown protocol, creating test points...");
                    pointsCreated = createTestPoints();
            }

            System.out.println("");
            System.out.println("═══════════════════════════════════════════");
            System.out.println("✅ Discovery completed!");
            System.out.println("   Points created: " + pointsCreated);
            System.out.println("═══════════════════════════════════════════");

            if ("bacnet".equals(proto) && getUseCOV() && pointsCreated > 0) {
                System.out.println("→ Auto-subscribing COV...");
                doSubscribeCOV();
            }

        } catch (Exception e) {
            System.err.println("❌ Discovery failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private int discoverBACnetPointsEnhanced() throws Exception {
        System.out.println("");
        System.out.println("📡 BACnet Discovery Process:");
        System.out.println("-------------------------------------------");

        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        System.out.println("   Target IP: " + ip);
        System.out.println("   Target Port: " + port);

        int targetDeviceId = parseDeviceIdFromName(getDeviceName());

        if (targetDeviceId < 0) {
            System.err.println("   ⚠️  Cannot parse Device ID from name: " + getDeviceName());
            System.out.println("   → Switching to fallback scan method...");
            return scanCommonObjects(ip, port);
        }

        System.out.println("   Device ID: " + targetDeviceId);
        setDeviceId(targetDeviceId);

        System.out.println("");
        System.out.println("Step 1: Reading Object List Count...");
        int objectCount = readObjectListCount(ip, port, targetDeviceId);

        if (objectCount <= 0) {
            System.out.println("   ⚠️  Object List not available (count = " + objectCount + ")");
            System.out.println("   → Switching to fallback scan method...");
            return scanCommonObjects(ip, port);
        }

        System.out.println("   ✓ Found " + objectCount + " objects in list");

        System.out.println("");
        System.out.println("Step 2: Reading individual objects...");

        int created = 0;
        int failed = 0;

        for (int i = 1; i <= objectCount; i++) {
            System.out.print("   [" + i + "/" + objectCount + "] ");
            int objectId = readObjectListItem(ip, port, targetDeviceId, i);

            if (objectId > 0) {
                String typeName = BACnetUtil.getObjectTypeName(objectId);
                int instance = BACnetUtil.getInstance(objectId);

                if (typeName.equals("Device")) {
                    System.out.println("Skipping Device object");
                    continue;
                }

                String pointName = typeName + "_" + instance;
                Type targetType = selectPointType(typeName);

                if (addDynamicPoint(pointName, "bacnet", instance, targetType)) {
                    if (targetType == BMyProxyPoint.TYPE) {
                        setPointRegisterType(pointName, typeName);
                    }
                    created++;
                    System.out.println("✓ Created: " + pointName + " (Type: " + typeName + ")");
                } else {
                    System.out.println("⚠️  Already exists: " + pointName);
                }
            } else {
                failed++;
                System.out.println("✗ Failed to read object");
            }

            if (i % 10 == 0) Thread.sleep(100);
            else Thread.sleep(50);
        }

        System.out.println("");
        System.out.println("Summary:");
        System.out.println("   ✓ Created: " + created);
        System.out.println("   ✗ Failed: " + failed);
        System.out.println("   Total: " + objectCount);

        return created;
    }

    private Type selectPointType(String typeName) {
        if (typeName.contains("Binary") || typeName.equals("BI") || typeName.equals("BO") || typeName.equals("BV")) {
            return BMyBooleanPoint.TYPE;
        } else if (typeName.contains("Multi")) {
            return BMyEnumPoint.TYPE;
        } else {
            return BMyProxyPoint.TYPE;
        }
    }

    private int readObjectListCount(String ip, int port, int deviceId) {
        return AccessController.doPrivileged((PrivilegedAction<Integer>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(5000);
                InetAddress addr = InetAddress.getByName(ip);
                int invokeId = (int) (Math.random() * 255);

                System.out.println("   → Sending request (Index 0 = Count)...");
                byte[] request = BACnetUtil.buildReadObjectListPacket(deviceId, 0, invokeId);
                socket.send(new DatagramPacket(request, request.length, addr, port));

                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                System.out.println("   → Response received (" + response.getLength() + " bytes)");

                return BACnetUtil.parseObjectCount(response.getData());
            } catch (SocketTimeoutException e) {
                System.err.println("   ✗ Timeout waiting for response");
                return 0;
            } catch (Exception e) {
                System.err.println("   ✗ Error: " + e.getMessage());
                e.printStackTrace();
                return 0;
            } finally {
                if (socket != null) socket.close();
            }
        });
    }

    private int readObjectListItem(String ip, int port, int deviceId, int index) {
        return AccessController.doPrivileged((PrivilegedAction<Integer>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);
                InetAddress addr = InetAddress.getByName(ip);
                int invokeId = (int) (Math.random() * 255);

                byte[] request = BACnetUtil.buildReadObjectListPacket(deviceId, index, invokeId);
                socket.send(new DatagramPacket(request, request.length, addr, port));

                byte[] buffer = new byte[512];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                byte[] data = response.getData();
                if (BACnetUtil.isSegmentedResponse(data)) {
                    return handleSegmentedResponse(socket, addr, port, data, invokeId);
                } else {
                    return BACnetUtil.parseObjectId(data);
                }
            } catch (Exception e) {
                return -1;
            } finally {
                if (socket != null) socket.close();
            }
        });
    }

    private int handleSegmentedResponse(DatagramSocket socket, InetAddress addr, int port, byte[] firstSegment, int invokeId) throws Exception {
        System.out.println("      [Segmented response detected]");
        BACnetUtil.SegmentAssembler assembler = new BACnetUtil.SegmentAssembler();

        if (!assembler.addSegment(firstSegment)) {
            System.err.println("      ✗ Failed to add first segment");
            return -1;
        }

        while (!assembler.isComplete()) {
            int seq = BACnetUtil.getSegmentSequence(firstSegment);
            byte[] ack = BACnetUtil.buildSegmentACK(seq, invokeId);
            socket.send(new DatagramPacket(ack, ack.length, addr, port));

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            if (!assembler.addSegment(response.getData())) {
                System.err.println("      ✗ Failed to add segment");
                break;
            }
            firstSegment = response.getData();
        }
        return BACnetUtil.parseObjectId(assembler.getData());
    }

    private int scanCommonObjects(String ip, int port) {
        System.out.println("");
        System.out.println("📡 Fallback Scan Method:");
        System.out.println("-------------------------------------------");
        System.out.println("   Scanning common BACnet objects (0-10 of each type)...");

        int[] types = {0, 1, 2, 3, 4, 5};
        String[] typeNames = {"AI", "AO", "AV", "BI", "BO", "BV"};

        return AccessController.doPrivileged((PrivilegedAction<Integer>) () -> {
            DatagramSocket socket = null;
            int totalCreated = 0;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(500);
                InetAddress addr = InetAddress.getByName(ip);

                for (int t = 0; t < types.length; t++) {
                    int objectType = types[t];
                    String typeName = typeNames[t];
                    System.out.println("");
                    System.out.println("   Scanning " + typeName + " objects...");

                    for (int instance = 0; instance <= 10; instance++) {
                        if (checkObjectExists(socket, addr, port, objectType, instance)) {
                            String pointName = typeName + "_" + instance;
                            Type targetType = (objectType >= 3) ? BMyBooleanPoint.TYPE : BMyProxyPoint.TYPE;

                            if (addDynamicPoint(pointName, "bacnet", instance, targetType)) {
                                if (targetType == BMyProxyPoint.TYPE) {
                                    setPointRegisterType(pointName, typeName);
                                }
                                totalCreated++;
                                System.out.println("      ✓ Found & Created: " + pointName);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("   ✗ Scan error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            System.out.println("");
            System.out.println("   Total objects found: " + totalCreated);
            return totalCreated;
        });
    }

    private boolean checkObjectExists(DatagramSocket socket, InetAddress addr, int port, int objectType, int instance) {
        try {
            int invokeId = (int) (Math.random() * 255);
            byte[] request = BACnetUtil.buildReadPropertyPacket(objectType, instance, BACnetUtil.PROP_PRESENT_VALUE, invokeId);
            socket.send(new DatagramPacket(request, request.length, addr, port));

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);

            byte[] data = response.getData();
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }
            return (data[offset] & 0xF0) == 0x30;
        } catch (Exception e) {
            return false;
        }
    }

    private int discoverModbusPoints() throws Exception {
        System.out.println("   Scanning Modbus registers...");
        int count = 0;
        for (int i = 0; i < 10; i++) {
            String propName = "HR" + i;
            if (addDynamicPoint(propName, "modbus", i, BMyProxyPoint.TYPE)) {
                count++;
                System.out.println("   ✓ Created: " + propName);
            }
        }
        for (int i = 0; i < 10; i++) {
            String propName = "Coil" + i;
            if (addDynamicPoint(propName, "modbus", i, BMyBooleanPoint.TYPE)) {
                setPointRegisterType(propName, "coil");
                count++;
                System.out.println("   ✓ Created: " + propName);
            }
        }
        return count;
    }

    private int discoverHTTPPoints() throws Exception {
        System.out.println("   Scanning HTTP endpoints...");
        int count = 0;
        String[] endpoints = {"temp", "humid", "pressure"};
        for (int i = 0; i < endpoints.length; i++) {
            if (addDynamicPoint(endpoints[i], "http", i, BMyProxyPoint.TYPE)) {
                count++;
                System.out.println("   ✓ Created: " + endpoints[i]);
            }
        }
        return count;
    }

    private int createTestPoints() throws Exception {
        System.out.println("   Creating test points...");
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (addDynamicPoint("TestPoint" + i, "test", i, BMyProxyPoint.TYPE)) {
                count++;
                System.out.println("   ✓ Created: TestPoint" + i);
            }
        }
        return count;
    }

    // ==================== COV Support ====================
    private void startCOVListener() {
        if (isCOVRunning) {
            System.out.println("⚠️  COV Listener already running");
            return;
        }
        System.out.println("Starting COV Listener...");
        isCOVRunning = true;

        covListenerThread = new Thread(() -> {
            AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
                try {
                    covPort = 47809;
                    try {
                        covSocket = new DatagramSocket(covPort);
                    } catch (BindException e) {
                        covPort = 47810;
                        covSocket = new DatagramSocket(covPort);
                    }
                    covSocket.setSoTimeout(1000);
                    System.out.println("✓ COV Listener started on port " + covPort);
                    setCovStatus("Active on port " + covPort);

                    while (isCOVRunning) {
                        try {
                            byte[] buffer = new byte[1024];
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                            covSocket.receive(packet);
                            processCOVNotification(packet.getData());
                        } catch (SocketTimeoutException e) {
                        } catch (Exception e) {
                            if (isCOVRunning) System.err.println("COV Listener error: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("❌ Failed to start COV Listener: " + e.getMessage());
                    setCovStatus("Failed: " + e.getMessage());
                    isCOVRunning = false;
                } finally {
                    if (covSocket != null && !covSocket.isClosed()) covSocket.close();
                }
                return null;
            });
        });
        covListenerThread.setDaemon(true);
        covListenerThread.start();
    }

    private void stopCOVListener() {
        if (!isCOVRunning) return;
        System.out.println("Stopping COV Listener...");
        isCOVRunning = false;
        if (covSocket != null && !covSocket.isClosed()) covSocket.close();
        if (covListenerThread != null) covListenerThread.interrupt();
        setCovStatus("Disabled");
    }

    private void processCOVNotification(byte[] data) {
        try {
            Map<String, Object> notification = BACnetUtil.parseCOVNotification(data);
            if (notification.isEmpty()) return;

            int objectType = (Integer) notification.get("objectType");
            int instance = (Integer) notification.get("instance");
            Object value = notification.get("value");
            if (value == null) return;

            String typeName = BACnetUtil.getObjectTypeName((objectType << 22) | instance);
            String pointName = typeName + "_" + instance;

            updatePointValue(pointName, value);
            System.out.println("📬 COV Update: " + pointName + " = " + value);
        } catch (Exception e) {
            System.err.println("Failed to process COV: " + e.getMessage());
        }
    }

    private void updatePointValue(String pointName, Object value) {
        try {
            BComponent pointsFolder = getPointsFolder();
            if (pointsFolder == null) return;

            BComplex point = (BComplex) pointsFolder.get(pointName);
            if (point == null) return;

            if (point instanceof BNumericWritable) {
                double numValue = ((Number) value).doubleValue();
                BNumericWritable np = (BNumericWritable) point;
                np.setFallback(new BStatusNumeric(numValue, BStatus.ok));
            } else if (point instanceof BBooleanWritable) {
                boolean boolValue = ((Number) value).intValue() != 0;
                BBooleanWritable bp = (BBooleanWritable) point;
                bp.setFallback(new BStatusBoolean(boolValue, BStatus.ok));
            }
        } catch (Exception e) {
            System.err.println("Failed to update point value: " + e.getMessage());
        }
    }

    public void doSubscribeCOV() throws Exception {
        if (!"bacnet".equalsIgnoreCase(getProtocol())) {
            System.err.println("❌ COV is only supported for BACnet");
            setCovStatus("Error: Not a BACnet device");
            return;
        }

        if (!isCOVRunning) {
            startCOVListener();
            Thread.sleep(500);
        }

        if (covPort == 0) {
            System.err.println("❌ COV Listener not ready");
            setCovStatus("Error: Listener not started");
            return;
        }

        System.out.println("═══════════════════════════════════════════");
        System.out.println("📡 Subscribing COV for all points...");
        System.out.println("   COV Port: " + covPort);
        System.out.println("   Lifetime: " + getCovLifetime() + "s");
        System.out.println("═══════════════════════════════════════════");

        BComponent pointsFolder = getPointsFolder();
        if (pointsFolder == null) return;

        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        SlotCursor cursor = pointsFolder.getSlots();
        int subscribed = 0;
        int failed = 0;
        int total = 0;

        while (cursor.next()) {
            String pointName = cursor.slot().getName();
            total++;

            try {
                String[] nameParts = pointName.split("_");
                if (nameParts.length < 2) continue;

                int instance = Integer.parseInt(nameParts[1]);
                int objectType = getObjectTypeFromName(nameParts[0]);
                if (objectType < 0) continue;

                System.out.print("   [" + total + "] " + pointName + " ... ");

                boolean success = subscribeCOVForPoint(ip, port, objectType, instance, getCovLifetime());

                if (success) {
                    covSubscriptions.put(pointName, objectType);
                    subscribed++;
                    System.out.println("✅");
                } else {
                    failed++;
                    System.out.println("❌");
                }
                Thread.sleep(150);
            } catch (Exception e) {
                failed++;
                System.err.println("❌ Error: " + e.getMessage());
            }
        }

        System.out.println("");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("COV Subscription Summary:");
        System.out.println("   ✅ Success: " + subscribed);
        System.out.println("   ❌ Failed: " + failed);
        System.out.println("   📊 Total: " + total);
        System.out.println("═══════════════════════════════════════════");

        if (subscribed > 0) {
            setCovStatus("Active (" + subscribed + "/" + total + " points)");
            System.out.println("💡 Points will now update via COV notifications");
        } else {
            setCovStatus("Failed - Using polling only");
            System.out.println("⚠️  No points subscribed - using polling only");
        }
    }

    private boolean subscribeCOVForPoint(String ip, int port, int objectType, int instance, int lifetime) {
        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
            DatagramSocket socketToSend = (covSocket != null && !covSocket.isClosed()) ? covSocket : null;
            boolean tempSocket = false;

            try {
                if (socketToSend == null) {
                    socketToSend = new DatagramSocket();
                    tempSocket = true;
                }

                InetAddress addr = InetAddress.getByName(ip);
                int invokeId = (int) (Math.random() * 255);

                byte[] request = BACnetUtil.buildSubscribeCOVPacket(objectType, instance, processId, lifetime, invokeId);
                socketToSend.send(new DatagramPacket(request, request.length, addr, port));
                return true;
            } catch (Exception e) {
                System.err.println("COV Subscribe Error: " + e.getMessage());
                return false;
            } finally {
                if (tempSocket && socketToSend != null) socketToSend.close();
            }
        });
    }

    public void doUnsubscribeCOV() throws Exception {
        if (!"bacnet".equalsIgnoreCase(getProtocol())) {
            System.err.println("COV is only supported for BACnet");
            return;
        }

        System.out.println("Unsubscribing COV...");
        String[] parts = getDeviceAddress().split(":");
        String ip = parts[0];
        int port = parts.length > 1 ? Integer.parseInt(parts[1]) : 47808;

        for (Map.Entry<String, Integer> entry : covSubscriptions.entrySet()) {
            String pointName = entry.getKey();
            try {
                String[] nameParts = pointName.split("_");
                int instance = Integer.parseInt(nameParts[1]);
                int objectType = entry.getValue();
                subscribeCOVForPoint(ip, port, objectType, instance, 0);
            } catch (Exception e) {
                System.err.println("Failed to unsubscribe " + pointName);
            }
        }
        covSubscriptions.clear();
        stopCOVListener();
        setCovStatus("Disabled - Polling only");
        System.out.println("✅ Unsubscribed all COV");
    }

    private int getObjectTypeFromName(String typeName) {
        switch (typeName.toUpperCase()) {
            case "AI": return 0;
            case "AO": return 1;
            case "AV": return 2;
            case "BI": return 3;
            case "BO": return 4;
            case "BV": return 5;
            default: return -1;
        }
    }

    private String detectProtocol() {
        String desc = getDeviceDescription().toLowerCase();
        if (desc.contains("bacnet")) return "bacnet";
        if (desc.contains("modbus")) return "modbus";
        if (desc.contains("http")) return "http";
        return "unknown";
    }

    private BComponent getPointsFolder() {
        try {
            BComponent folder = (BComponent) get("Points");
            if (folder == null) {
                folder = new BComponent();
                add("Points", folder);
            }
            return folder;
        } catch (Exception e) {
            System.err.println("Failed to get/create Points folder: " + e.getMessage());
            return null;
        }
    }

    private void setCovStatus(String status) {
        try {
            set("covStatus", BString.make(status));
        } catch (Exception e) { }
    }

    private boolean addDynamicPoint(String propName, String proto, int address, Type pointType) {
        try {
            BComponent pointsFolder = getPointsFolder();
            if (pointsFolder.get(propName) != null) return false;

            BControlPoint point = (BControlPoint) ((BValue) pointType.getInstance()).newCopy();

            try {
                Property protoProp = point.getProperty("protocol");
                if (protoProp != null) {
                    BValue val = point.get(protoProp);
                    if (val instanceof BDynamicEnum) {
                        BDynamicEnum dyn = (BDynamicEnum) val;
                        BEnumRange range = dyn.getRange();
                        BEnum enm = range.get(proto);

                        if (enm == null) {
                            int[] ordinals = range.getOrdinals();
                            for (int i = 0; i < ordinals.length; i++) {
                                BEnum temp = range.get(ordinals[i]);
                                if (temp.getTag().equalsIgnoreCase(proto)) {
                                    enm = temp;
                                    break;
                                }
                            }
                        }
                        if (enm != null) {
                            point.set(protoProp, BDynamicEnum.make(enm.getOrdinal(), range));
                        }
                    } else {
                        point.set("protocol", BString.make(proto));
                    }
                }
                point.set("registerAddress", BInteger.make(address));
            } catch (Exception e) { }

            pointsFolder.add(propName, point);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to create point " + propName + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private void setPointRegisterType(String propName, String typeName) {
        try {
            BComponent pointsFolder = getPointsFolder();
            BComplex point = (BComplex) pointsFolder.get(propName);
            if (point != null) {
                try {
                    point.set("registerType", BString.make(typeName));
                } catch (Exception e) { }
            }
        } catch (Exception e) { }
    }

    private int parseDeviceIdFromName(String name) {
        try {
            if (name.contains("_")) {
                String[] parts = name.split("_");
                return Integer.parseInt(parts[parts.length - 1]);
            }
        } catch (Exception e) { }
        return -1;
    }

    /**
     * Implementation of Clear Points
     */
    public void doClearPoints(BBoolean confirm) throws Exception {
        // ✅ เช็คก่อนว่า User กด Yes หรือไม่
        if (!confirm.getBoolean()) {
            return; // ถ้า Cancel ก็จบการทำงาน
        }

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

        System.out.println("✅ Cleared " + toRemove.size() + " points.");
    }
}