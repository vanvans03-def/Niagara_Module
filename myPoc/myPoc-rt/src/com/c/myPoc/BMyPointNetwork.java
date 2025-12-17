package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;

import com.c.myPoc.services.DefaultPointRepository;
import com.c.myPoc.services.DefaultPointService;
import com.c.myPoc.services.PointRepository;
import com.c.myPoc.services.PointService;
import com.cocoad.extension.service.ServiceCollection;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Point API Network with Discovery Support
 *
 * Features:
 * - Auto-register servlet via web.xml
 * - Device Discovery from external API via Action
 * - Auto-create devices and points
 *
 * Usage:
 * 1. Place at Services
 * 2. Right-click → Actions → discoverDevices
 * 3. Devices will be auto-created
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "2.1.0", flags = Flags.READONLY)
@NiagaraProperty(name = "endpoint", type = "String", defaultValue = "/pointApi", flags = Flags.READONLY)
@NiagaraProperty(name = "discoveryUrl", type = "String", defaultValue = "http://localhost:8080/api/devices")
@NiagaraProperty(name = "discoveryTimeout", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "lastDiscoveryCount", type = "int", defaultValue = "0", flags = Flags.READONLY)
@NiagaraAction(name = "restart", flags = Flags.SUMMARY)
@NiagaraAction(name = "ping")
@NiagaraAction(name = "discoverDevices", flags = Flags.ASYNC | Flags.SUMMARY)
public class BMyPointNetwork extends BDeviceNetwork implements BIService {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyPointNetwork(2564787471)1.0$ @*/
/* Generated Wed Dec 17 11:27:09 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "version"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code version} property.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "2.1.0", null);
  
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
// Property "discoveryUrl"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoveryUrl} property.
   * @see #getDiscoveryUrl
   * @see #setDiscoveryUrl
   */
  public static final Property discoveryUrl = newProperty(0, "http://localhost:8080/api/devices", null);
  
  /**
   * Get the {@code discoveryUrl} property.
   * @see #discoveryUrl
   */
  public String getDiscoveryUrl() { return getString(discoveryUrl); }
  
  /**
   * Set the {@code discoveryUrl} property.
   * @see #discoveryUrl
   */
  public void setDiscoveryUrl(String v) { setString(discoveryUrl, v, null); }

////////////////////////////////////////////////////////////////
// Property "discoveryTimeout"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoveryTimeout} property.
   * @see #getDiscoveryTimeout
   * @see #setDiscoveryTimeout
   */
  public static final Property discoveryTimeout = newProperty(0, 5000, null);
  
  /**
   * Get the {@code discoveryTimeout} property.
   * @see #discoveryTimeout
   */
  public int getDiscoveryTimeout() { return getInt(discoveryTimeout); }
  
  /**
   * Set the {@code discoveryTimeout} property.
   * @see #discoveryTimeout
   */
  public void setDiscoveryTimeout(int v) { setInt(discoveryTimeout, v, null); }

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
            System.out.println("🔍 Discovery URL: " + getDiscoveryUrl());
            System.out.println("");
            System.out.println("📝 Available Endpoints:");
            System.out.println("  GET " + getEndpoint() + "/              - Get all points");
            System.out.println("  GET " + getEndpoint() + "/{name}        - Get specific point");
            System.out.println("");
            System.out.println("🔎 Discovery:");
            System.out.println("  - Right-click → Actions → discoverDevices");
            System.out.println("  - Devices will be auto-created under this network");
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

    // ==================== Discovery Implementation (via Action) ====================

    /**
     * Discover devices from external API and create them automatically
     * This runs as an async action (background thread)
     */
    public void doDiscoverDevices() throws Exception {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Starting device discovery...");
        System.out.println("Discovery URL: " + getDiscoveryUrl());

        try {
            List<DiscoveredDevice> devices = discoverDevicesFromAPI();

            int createdCount = 0;
            for (DiscoveredDevice device : devices) {
                // Check if device already exists
                String deviceName = device.getName();
                if (!deviceExists(deviceName)) {
                    // Create new device
                    BMyPointDevice bDevice = new BMyPointDevice();
                    bDevice.setDeviceName(device.getName());
                    bDevice.setDeviceAddress(device.getAddress());
                    bDevice.setDeviceDescription(device.getDescription());

                    // Add to network
                    addDeviceToNetwork(deviceName, bDevice);
                    createdCount++;

                    System.out.println("✅ Created device: " + deviceName +
                            " (" + device.getAddress() + ")");
                } else {
                    System.out.println("⏭️  Skipped (already exists): " + deviceName);
                }
            }

            setLastDiscoveryCount(createdCount);
            System.out.println("");
            System.out.println("MyPointNetwork: Discovery completed");
            System.out.println("  - Found: " + devices.size() + " devices");
            System.out.println("  - Created: " + createdCount + " new devices");
            System.out.println("  - Skipped: " + (devices.size() - createdCount) + " existing devices");

        } catch (Exception e) {
            System.err.println("MyPointNetwork: Discovery failed - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        System.out.println("===========================================");
    }

    /**
     * Discover devices from external API
     */
    private List<DiscoveredDevice> discoverDevicesFromAPI() throws Exception {
        List<DiscoveredDevice> devices = new ArrayList<>();

        try {
            URL url = new URL(getDiscoveryUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(getDiscoveryTimeout());
            conn.setReadTimeout(getDiscoveryTimeout());

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // Parse JSON response (simple parsing)
                devices = parseDevicesJSON(content.toString());
            }

            conn.disconnect();

        } catch (Exception e) {
            System.err.println("API Discovery failed: " + e.getMessage());
            // Fallback: Use mock data for testing
            devices = getMockDevices();
        }

        return devices;
    }

    /**
     * Parse JSON response (simple implementation)
     * You should use a proper JSON library like Gson or Jackson
     */
    private List<DiscoveredDevice> parseDevicesJSON(String json) {
        List<DiscoveredDevice> devices = new ArrayList<>();

        // TODO: Implement proper JSON parsing
        // For now, return mock data
        return getMockDevices();
    }

    /**
     * Mock devices for testing
     */
    private List<DiscoveredDevice> getMockDevices() {
        List<DiscoveredDevice> devices = new ArrayList<>();
        devices.add(new DiscoveredDevice("Device001", "192.168.1.101", "Temperature Sensor"));
        devices.add(new DiscoveredDevice("Device002", "192.168.1.102", "Humidity Sensor"));
        devices.add(new DiscoveredDevice("Device003", "192.168.1.103", "Pressure Sensor"));
        devices.add(new DiscoveredDevice("Device004", "192.168.1.104", "Flow Sensor"));
        devices.add(new DiscoveredDevice("Device005", "192.168.1.105", "CO2 Sensor"));
        return devices;
    }

    /**
     * Helper class to hold discovered device info
     */
    private static class DiscoveredDevice {
        private String name;
        private String address;
        private String description;

        public DiscoveredDevice(String name, String address, String description) {
            this.name = name;
            this.address = address;
            this.description = description;
        }

        public String getName() { return name; }
        public String getAddress() { return address; }
        public String getDescription() { return description; }
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
        System.out.println("  - Discovery URL: " + getDiscoveryUrl());
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

    // ==================== Device Management ====================

    private List<BDevice> devicesList = new ArrayList<>();

    @Override
    public BDevice[] getDevices() {
        // Return devices from our internal list
        return devicesList.toArray(new BDevice[0]);
    }

    /**
     * Add a device to the network
     */
    private void addDeviceToNetwork(String name, BMyPointDevice device) throws Exception {
        add(name, device);
        devicesList.add(device);
    }

    /**
     * Check if device exists by name
     */
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
}