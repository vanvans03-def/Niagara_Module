package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.naming.BOrd;
import javax.baja.collection.*;

import com.c.myPoc.services.DefaultPointRepository;
import com.c.myPoc.services.DefaultPointService;
import com.c.myPoc.services.PointRepository;
import com.c.myPoc.services.PointService;
import com.cocoad.extension.service.ServiceCollection;

import java.util.*;

/**
 * Point API Network with INTERNAL Discovery Support
 *
 * Discovery โดยตรงจาก Station ผ่าน BQL Query
 * ไม่ต้องพึ่ง External API
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "2.2.0", flags = Flags.READONLY)
@NiagaraProperty(name = "endpoint", type = "String", defaultValue = "/pointApi", flags = Flags.READONLY)
@NiagaraProperty(name = "discoveryQuery", type = "String",
        defaultValue = "station:|slot:/|bql:select name, displayName, slotPath from control:ControlPoint")
@NiagaraProperty(name = "groupBy", type = "String", defaultValue = "deviceName",
        flags = Flags.SUMMARY)
@NiagaraProperty(name = "lastDiscoveryCount", type = "int", defaultValue = "0", flags = Flags.READONLY)
@NiagaraAction(name = "restart", flags = Flags.SUMMARY)
@NiagaraAction(name = "ping")
@NiagaraAction(name = "discoverDevices", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(name = "clearDevices", flags = Flags.SUMMARY)
public class BMyPointNetwork extends BDeviceNetwork implements BIService {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyPointNetwork(4135182234)1.0$ @*/
/* Generated Wed Dec 17 13:36:40 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "version"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code version} property.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "2.2.0", null);
  
  /**
   * Get the {@code version} property.
   * @see #version
   */
  public String getVersion() { return getString(version); }
  
  /**
   * Set the {@code version} property.
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

////////////////////////////////////////////////////////////////
// Property "endpoint"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code endpoint} property.
   * @see #getEndpoint
   * @see #setEndpoint
   */
  public static final Property endpoint = newProperty(Flags.READONLY, "/pointApi", null);
  
  /**
   * Get the {@code endpoint} property.
   * @see #endpoint
   */
  public String getEndpoint() { return getString(endpoint); }
  
  /**
   * Set the {@code endpoint} property.
   * @see #endpoint
   */
  public void setEndpoint(String v) { setString(endpoint, v, null); }

////////////////////////////////////////////////////////////////
// Property "discoveryQuery"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoveryQuery} property.
   * @see #getDiscoveryQuery
   * @see #setDiscoveryQuery
   */
  public static final Property discoveryQuery = newProperty(0, "station:|slot:/|bql:select name, displayName, slotPath from control:ControlPoint", null);
  
  /**
   * Get the {@code discoveryQuery} property.
   * @see #discoveryQuery
   */
  public String getDiscoveryQuery() { return getString(discoveryQuery); }
  
  /**
   * Set the {@code discoveryQuery} property.
   * @see #discoveryQuery
   */
  public void setDiscoveryQuery(String v) { setString(discoveryQuery, v, null); }

////////////////////////////////////////////////////////////////
// Property "groupBy"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code groupBy} property.
   * @see #getGroupBy
   * @see #setGroupBy
   */
  public static final Property groupBy = newProperty(Flags.SUMMARY, "deviceName", null);
  
  /**
   * Get the {@code groupBy} property.
   * @see #groupBy
   */
  public String getGroupBy() { return getString(groupBy); }
  
  /**
   * Set the {@code groupBy} property.
   * @see #groupBy
   */
  public void setGroupBy(String v) { setString(groupBy, v, null); }

////////////////////////////////////////////////////////////////
// Property "lastDiscoveryCount"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code lastDiscoveryCount} property.
   * @see #getLastDiscoveryCount
   * @see #setLastDiscoveryCount
   */
  public static final Property lastDiscoveryCount = newProperty(Flags.READONLY, 0, null);
  
  /**
   * Get the {@code lastDiscoveryCount} property.
   * @see #lastDiscoveryCount
   */
  public int getLastDiscoveryCount() { return getInt(lastDiscoveryCount); }
  
  /**
   * Set the {@code lastDiscoveryCount} property.
   * @see #lastDiscoveryCount
   */
  public void setLastDiscoveryCount(int v) { setInt(lastDiscoveryCount, v, null); }

////////////////////////////////////////////////////////////////
// Action "restart"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code restart} action.
   * @see #restart()
   */
  public static final Action restart = newAction(Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code restart} action.
   * @see #restart
   */
  public void restart() { invoke(restart, null, null); }

////////////////////////////////////////////////////////////////
// Action "ping"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code ping} action.
   * @see #ping()
   */
  public static final Action ping = newAction(0, null);
  
  /**
   * Invoke the {@code ping} action.
   * @see #ping
   */
  public void ping() { invoke(ping, null, null); }

////////////////////////////////////////////////////////////////
// Action "discoverDevices"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverDevices} action.
   * @see #discoverDevices()
   */
  public static final Action discoverDevices = newAction(Flags.ASYNC | Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code discoverDevices} action.
   * @see #discoverDevices
   */
  public void discoverDevices() { invoke(discoverDevices, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearDevices"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code clearDevices} action.
   * @see #clearDevices()
   */
  public static final Action clearDevices = newAction(Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code clearDevices} action.
   * @see #clearDevices
   */
  public void clearDevices() { invoke(clearDevices, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyPointNetwork.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== BIService Implementation ====================

    @Override
    public Type[] getServiceTypes() {
        return new Type[] { TYPE };
    }

    @Override
    public void serviceStarted() throws Exception {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Service Starting...");

        try {
            ServiceCollection.getInstance()
                    .addSingleton(PointRepository.class, DefaultPointRepository.class)
                    .addSingleton(PointService.class, DefaultPointService.class);

            System.out.println("MyPointNetwork: Services registered successfully");
            System.out.println("");
            System.out.println("✅ API Ready at: " + getEndpoint() + "/*");
            System.out.println("🔍 Internal Discovery enabled");
            System.out.println("");
            System.out.println("📝 Available Endpoints:");
            System.out.println("  GET " + getEndpoint() + "/              - Get all points");
            System.out.println("  GET " + getEndpoint() + "/{name}        - Get specific point");
            System.out.println("");
            System.out.println("🔎 Discovery:");
            System.out.println("  - Right-click → Actions → discoverDevices");
            System.out.println("  - Devices will be auto-created from Station points");
            System.out.println("  - Group by: " + getGroupBy());
            System.out.println("");
            System.out.println("MyPointNetwork: Service Started successfully ✅");

        } catch (Exception e) {
            System.err.println("MyPointNetwork: Failed to start service - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        System.out.println("===========================================");
    }

    @Override
    public void serviceStopped() throws Exception {
        System.out.println("MyPointNetwork: Service Stopping...");
        System.out.println("MyPointNetwork: Service Stopped");
    }

    // ==================== BDeviceNetwork Required Methods ====================

    @Override
    public Type getDeviceType() {
        return BMyPointDevice.TYPE;
    }

    @Override
    public Type getDeviceFolderType() {
        return BDeviceFolder.TYPE;
    }

    // ==================== INTERNAL Discovery Implementation ====================

    /**
     * Discover devices จาก Station โดยตรง
     * ใช้ BQL Query แทนการยิง External API
     */
    public void doDiscoverDevices() throws Exception {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Starting INTERNAL device discovery...");
        System.out.println("Query: " + getDiscoveryQuery());
        System.out.println("Group by: " + getGroupBy());

        try {
            // 1. Query points from Station
            Map<String, List<DiscoveredPoint>> deviceGroups = discoverDevicesFromStation();

            int createdCount = 0;
            int pointsCount = 0;

            // 2. Create devices and add points
            for (Map.Entry<String, List<DiscoveredPoint>> entry : deviceGroups.entrySet()) {
                String deviceName = entry.getKey();
                List<DiscoveredPoint> points = entry.getValue();

                if (!deviceExists(deviceName)) {
                    // Create device
                    BMyPointDevice device = new BMyPointDevice();
                    device.setDeviceName(deviceName);
                    device.setDeviceAddress(generateDeviceAddress(deviceName));
                    device.setDeviceDescription(
                            "Auto-discovered device with " + points.size() + " point(s)"
                    );

                    // Add to network
                    addDeviceToNetwork(deviceName, device);
                    createdCount++;

                    System.out.println("✅ Created device: " + deviceName);

                    // TODO: Add points to device (implement later)
                    for (DiscoveredPoint point : points) {
                        System.out.println("   └─ Point: " + point.getName() +
                                " (Path: " + point.getPath() + ")");
                        pointsCount++;
                    }
                } else {
                    System.out.println("⏭️  Skipped (already exists): " + deviceName);
                }
            }

            setLastDiscoveryCount(createdCount);
            System.out.println("");
            System.out.println("MyPointNetwork: Discovery completed");
            System.out.println("  - Found: " + deviceGroups.size() + " device groups");
            System.out.println("  - Created: " + createdCount + " new devices");
            System.out.println("  - Total points: " + pointsCount);
            System.out.println("  - Skipped: " + (deviceGroups.size() - createdCount) + " existing devices");

        } catch (Exception e) {
            System.err.println("MyPointNetwork: Discovery failed - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        System.out.println("===========================================");
    }

    /**
     * Query points จาก Station แล้ว Group เป็น Device
     */
    private Map<String, List<DiscoveredPoint>> discoverDevicesFromStation() throws Exception {
        Map<String, List<DiscoveredPoint>> deviceGroups = new HashMap<>();

        // Execute BQL Query
        BOrd ord = BOrd.make(getDiscoveryQuery());
        BObject resolved = ord.resolve(Sys.getStation(), null).get();

        if (!(resolved instanceof BITable)) {
            throw new Exception("Query result is not a table");
        }

        BITable result = (BITable) resolved;
        Column[] cols = result.getColumns().list();
        TableCursor cursor = result.cursor();

        // Process each point
        while (cursor.next()) {
            String pointName = "";
            String displayName = "";
            String slotPath = "";

            // Extract values
            for (int i = 0; i < cols.length; i++) {
                String colName = cols[i].getName().toLowerCase();
                String value = cursor.cell(cols[i]).toString();

                if (colName.contains("name") && !colName.contains("display")) {
                    pointName = value;
                } else if (colName.contains("display")) {
                    displayName = value;
                } else if (colName.contains("path")) {
                    slotPath = value;
                }
            }

            // Determine device name based on groupBy strategy
            String deviceName = extractDeviceName(pointName, slotPath);

            // Add point to device group
            deviceGroups.computeIfAbsent(deviceName, k -> new ArrayList<>())
                    .add(new DiscoveredPoint(pointName, displayName, slotPath));
        }

        return deviceGroups;
    }

    /**
     * Extract device name จาก point name หรือ path
     * ตามกลยุทธ์ที่เลือกใน groupBy property
     */
    private String extractDeviceName(String pointName, String slotPath) {
        String strategy = getGroupBy().toLowerCase();

        if (strategy.equals("path")) {
            // Extract from path: /Drivers/NiagaraNetwork/Device1/Points/...
            // -> Device1
            String[] parts = slotPath.split("/");
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].toLowerCase().contains("device") && i < parts.length) {
                    return parts[i];
                }
            }
        }
        else if (strategy.equals("prefix")) {
            // Extract prefix: "HVAC_Temp" -> "HVAC"
            int idx = pointName.indexOf('_');
            if (idx > 0) {
                return pointName.substring(0, idx);
            }
        }

        // Default: use point name directly (or create generic device)
        return "Device_" + pointName.hashCode();
    }

    /**
     * Generate device address (เช่น internal reference path)
     */
    private String generateDeviceAddress(String deviceName) {
        return "station:/" + deviceName;
    }

    /**
     * Helper class สำหรับเก็บข้อมูล Point ที่ค้นพบ
     */
    private static class DiscoveredPoint {
        private String name;
        private String displayName;
        private String path;

        public DiscoveredPoint(String name, String displayName, String path) {
            this.name = name;
            this.displayName = displayName;
            this.path = path;
        }

        public String getName() { return name; }
        public String getDisplayName() { return displayName; }
        public String getPath() { return path; }
    }

    // ==================== Device Management ====================

    private List<BDevice> devicesList = new ArrayList<>();

    @Override
    public BDevice[] getDevices() {
        return devicesList.toArray(new BDevice[0]);
    }

    private void addDeviceToNetwork(String name, BMyPointDevice device) throws Exception {
        add(name, device);
        devicesList.add(device);
    }

    private boolean deviceExists(String name) {
        for (BDevice device : devicesList) {
            if (device instanceof BMyPointDevice) {
                BMyPointDevice d = (BMyPointDevice) device;
                if (d.getDeviceName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    // ==================== Action Handlers ====================

    public void doRestart() throws Exception {
        System.out.println("MyPointNetwork: Restarting service...");
        serviceStopped();
        serviceStarted();
        System.out.println("MyPointNetwork: Service restarted");
    }

    public void doPing() {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Ping Response");
        System.out.println("  - Status: " + getStatus());
        System.out.println("  - Running: " + isRunning());
        System.out.println("  - Endpoint: " + getEndpoint());
        System.out.println("  - Version: " + getVersion());
        System.out.println("  - Discovery: Internal (BQL-based)");
        System.out.println("  - Group Strategy: " + getGroupBy());
        System.out.println("  - Last Discovery: " + getLastDiscoveryCount() + " devices created");

        BDevice[] devices = getDevices();
        System.out.println("  - Total Devices: " + (devices != null ? devices.length : 0));

        try {
            PointService pointService = ServiceCollection.getInstance().getService(PointService.class);
            System.out.println("  - PointService: " + (pointService != null ? "✅ Registered" : "❌ Not Found"));
        } catch (Exception e) {
            System.out.println("  - Services: ❌ Error - " + e.getMessage());
        }

        System.out.println("===========================================");
    }

    /**
     * Clear all discovered devices
     */
    public void doClearDevices() throws Exception {
        System.out.println("MyPointNetwork: Clearing all devices...");

        int count = devicesList.size();

        // Remove all devices from component tree
        for (BDevice device : devicesList) {
            remove(device.getName());
        }

        devicesList.clear();
        setLastDiscoveryCount(0);

        System.out.println("MyPointNetwork: Cleared " + count + " device(s)");
    }

    // ==================== Network Lifecycle ====================

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("MyPointNetwork: Network started() called");
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("MyPointNetwork: Network stopping...");
        super.stopped();
    }
}