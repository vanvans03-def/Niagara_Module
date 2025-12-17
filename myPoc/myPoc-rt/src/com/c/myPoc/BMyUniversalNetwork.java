package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.security.*;

/**
 * Universal Multi-Protocol Network Discovery
 *
 * รองรับ 3 Protocols:
 * 1. BACnet/IP - Who-Is broadcast (Port 47808)
 * 2. Modbus TCP - Port scan (Port 502)
 * 3. HTTP/REST - Web server detection (Port 80/443/8080)
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "3.0.0", flags = Flags.READONLY)
@NiagaraProperty(name = "subnet", type = "String", defaultValue = "192.168.1.0/24",
        flags = Flags.SUMMARY)
@NiagaraProperty(name = "enableBACnet", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "enableModbus", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "enableHTTP", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "scanTimeout", type = "int", defaultValue = "2000")
@NiagaraProperty(name = "maxThreads", type = "int", defaultValue = "50")
@NiagaraProperty(name = "lastDiscoveryCount", type = "int", defaultValue = "0", flags = Flags.READONLY)
@NiagaraProperty(name = "lastDiscoveryTime", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraAction(name = "discoverAll", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(name = "discoverBACnet", flags = Flags.ASYNC)
@NiagaraAction(name = "discoverModbus", flags = Flags.ASYNC)
@NiagaraAction(name = "discoverHTTP", flags = Flags.ASYNC)
@NiagaraAction(name = "clearDevices", flags = Flags.SUMMARY)
@NiagaraAction(name = "ping")
public class BMyUniversalNetwork extends BDeviceNetwork {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyUniversalNetwork(2979906276)1.0$ @*/
    /* Generated Wed Dec 17 16:00:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

    ////////////////////////////////////////////////////////////////
    // Property "version"
    ////////////////////////////////////////////////////////////////

    public static final Property version = newProperty(Flags.READONLY, "3.0.0", null);
    public String getVersion() { return getString(version); }
    public void setVersion(String v) { setString(version, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "subnet"
    ////////////////////////////////////////////////////////////////

    public static final Property subnet = newProperty(Flags.SUMMARY, "192.168.1.0/24", null);
    public String getSubnet() { return getString(subnet); }
    public void setSubnet(String v) { setString(subnet, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "enableBACnet"
    ////////////////////////////////////////////////////////////////

    public static final Property enableBACnet = newProperty(0, true, null);
    public boolean getEnableBACnet() { return getBoolean(enableBACnet); }
    public void setEnableBACnet(boolean v) { setBoolean(enableBACnet, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "enableModbus"
    ////////////////////////////////////////////////////////////////

    public static final Property enableModbus = newProperty(0, true, null);
    public boolean getEnableModbus() { return getBoolean(enableModbus); }
    public void setEnableModbus(boolean v) { setBoolean(enableModbus, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "enableHTTP"
    ////////////////////////////////////////////////////////////////

    public static final Property enableHTTP = newProperty(0, true, null);
    public boolean getEnableHTTP() { return getBoolean(enableHTTP); }
    public void setEnableHTTP(boolean v) { setBoolean(enableHTTP, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "scanTimeout"
    ////////////////////////////////////////////////////////////////

    public static final Property scanTimeout = newProperty(0, 2000, null);
    public int getScanTimeout() { return getInt(scanTimeout); }
    public void setScanTimeout(int v) { setInt(scanTimeout, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "maxThreads"
    ////////////////////////////////////////////////////////////////

    public static final Property maxThreads = newProperty(0, 50, null);
    public int getMaxThreads() { return getInt(maxThreads); }
    public void setMaxThreads(int v) { setInt(maxThreads, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "lastDiscoveryCount"
    ////////////////////////////////////////////////////////////////

    public static final Property lastDiscoveryCount = newProperty(Flags.READONLY, 0, null);
    public int getLastDiscoveryCount() { return getInt(lastDiscoveryCount); }
    public void setLastDiscoveryCount(int v) { setInt(lastDiscoveryCount, v, null); }

    ////////////////////////////////////////////////////////////////
    // Property "lastDiscoveryTime"
    ////////////////////////////////////////////////////////////////

    public static final Property lastDiscoveryTime = newProperty(Flags.READONLY, "", null);
    public String getLastDiscoveryTime() { return getString(lastDiscoveryTime); }
    public void setLastDiscoveryTime(String v) { setString(lastDiscoveryTime, v, null); }

    ////////////////////////////////////////////////////////////////
    // Action "discoverAll"
    ////////////////////////////////////////////////////////////////

    public static final Action discoverAll = newAction(Flags.ASYNC | Flags.SUMMARY, null);
    public void discoverAll() { invoke(discoverAll, null, null); }

    ////////////////////////////////////////////////////////////////
    // Action "discoverBACnet"
    ////////////////////////////////////////////////////////////////

    public static final Action discoverBACnet = newAction(Flags.ASYNC, null);
    public void discoverBACnet() { invoke(discoverBACnet, null, null); }

    ////////////////////////////////////////////////////////////////
    // Action "discoverModbus"
    ////////////////////////////////////////////////////////////////

    public static final Action discoverModbus = newAction(Flags.ASYNC, null);
    public void discoverModbus() { invoke(discoverModbus, null, null); }

    ////////////////////////////////////////////////////////////////
    // Action "discoverHTTP"
    ////////////////////////////////////////////////////////////////

    public static final Action discoverHTTP = newAction(Flags.ASYNC, null);
    public void discoverHTTP() { invoke(discoverHTTP, null, null); }

    ////////////////////////////////////////////////////////////////
    // Action "clearDevices"
    ////////////////////////////////////////////////////////////////

    public static final Action clearDevices = newAction(Flags.SUMMARY, null);
    public void clearDevices() { invoke(clearDevices, null, null); }

    ////////////////////////////////////////////////////////////////
    // Action "ping"
    ////////////////////////////////////////////////////////////////

    public static final Action ping = newAction(0, null);
    public void ping() { invoke(ping, null, null); }

    ////////////////////////////////////////////////////////////////
    // Type
    ////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyUniversalNetwork.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== Constants ====================

    private static final int BACNET_PORT = 47808;
    private static final int MODBUS_PORT = 502;
    private static final int[] HTTP_PORTS = {80, 443, 8080, 8443};

    // ==================== BDeviceNetwork Required Methods ====================

    @Override
    public Type getDeviceType() {
        return BMyPointDevice.TYPE;
    }

    @Override
    public Type getDeviceFolderType() {
        return BDeviceFolder.TYPE;
    }

    // ==================== Device Storage ====================

    private List<BDevice> devicesList = new ArrayList<>();
    private ExecutorService executor;

    // ==================== Discovery Actions ====================

    /**
     * Discover All Protocols
     */
    public void doDiscoverAll() throws Exception {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Universal Discovery: Starting All Protocols");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Subnet: " + getSubnet());
        System.out.println("Timeout: " + getScanTimeout() + "ms");
        System.out.println("");

        long startTime = System.currentTimeMillis();
        List<DiscoveredDevice> allDevices = new ArrayList<>();

        try {
            // 1. BACnet Discovery
            if (getEnableBACnet()) {
                System.out.println("🔍 [1/3] BACnet Discovery...");
                allDevices.addAll(scanBACnet());
            }

            // 2. Modbus Discovery
            if (getEnableModbus()) {
                System.out.println("🔍 [2/3] Modbus Discovery...");
                allDevices.addAll(scanModbus());
            }

            // 3. HTTP Discovery
            if (getEnableHTTP()) {
                System.out.println("🔍 [3/3] HTTP Discovery...");
                allDevices.addAll(scanHTTP());
            }

            // Create devices
            int createdCount = createDevicesFromList(allDevices);

            long duration = System.currentTimeMillis() - startTime;
            setLastDiscoveryCount(createdCount);
            setLastDiscoveryTime(new java.util.Date().toString());

            System.out.println("");
            System.out.println("═══════════════════════════════════════════");
            System.out.println("Discovery Completed!");
            System.out.println("  ⏱️  Duration: " + duration + "ms");
            System.out.println("  📊 Found: " + allDevices.size() + " devices");
            System.out.println("  ✅ Created: " + createdCount + " new devices");
            System.out.println("  ⏭️  Skipped: " + (allDevices.size() - createdCount) + " existing");
            System.out.println("═══════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("❌ Discovery failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Discover BACnet Only
     */
    public void doDiscoverBACnet() throws Exception {
        System.out.println("🔍 BACnet Discovery Starting...");
        List<DiscoveredDevice> devices = scanBACnet();
        int created = createDevicesFromList(devices);
        System.out.println("✅ BACnet Discovery: Found " + devices.size() + ", Created " + created);
    }

    /**
     * Discover Modbus Only
     */
    public void doDiscoverModbus() throws Exception {
        System.out.println("🔍 Modbus Discovery Starting...");
        List<DiscoveredDevice> devices = scanModbus();
        int created = createDevicesFromList(devices);
        System.out.println("✅ Modbus Discovery: Found " + devices.size() + ", Created " + created);
    }

    /**
     * Discover HTTP Only
     */
    public void doDiscoverHTTP() throws Exception {
        System.out.println("🔍 HTTP Discovery Starting...");
        List<DiscoveredDevice> devices = scanHTTP();
        int created = createDevicesFromList(devices);
        System.out.println("✅ HTTP Discovery: Found " + devices.size() + ", Created " + created);
    }

    // ==================== Protocol Scanners ====================

    /**
     * Scan BACnet/IP Devices (Who-Is Broadcast)
     */
    private List<DiscoveredDevice> scanBACnet() {
        List<DiscoveredDevice> devices = new ArrayList<>();

        return AccessController.doPrivileged((PrivilegedAction<List<DiscoveredDevice>>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(BACNET_PORT);
                socket.setBroadcast(true);
                socket.setSoTimeout(getScanTimeout());

                // Build Who-Is message
                byte[] whoIs = buildBACnetWhoIs();

                // Send broadcast
                String broadcastAddr = calculateBroadcast(getSubnet());
                InetAddress broadcast = InetAddress.getByName(broadcastAddr);
                DatagramPacket sendPacket = new DatagramPacket(
                        whoIs, whoIs.length, broadcast, BACNET_PORT
                );
                socket.send(sendPacket);

                System.out.println("   → Who-Is broadcast sent to " + broadcastAddr);

                // Listen for I-Am responses
                byte[] buffer = new byte[1024];
                long startTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - startTime < getScanTimeout()) {
                    try {
                        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                        socket.receive(receivePacket);

                        int deviceId = parseBACnetIAm(receivePacket.getData(), receivePacket.getLength());
                        if (deviceId > 0) {
                            String ip = receivePacket.getAddress().getHostAddress();
                            devices.add(new DiscoveredDevice(
                                    "BACnet_" + deviceId,
                                    "BACnet/IP",
                                    ip,
                                    BACNET_PORT,
                                    "BACnet Device ID: " + deviceId
                            ));
                            System.out.println("   ✓ Found: BACnet Device " + deviceId + " at " + ip);
                        }
                    } catch (SocketTimeoutException e) {
                        break;
                    }
                }

            } catch (Exception e) {
                System.err.println("   ✗ BACnet scan error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return devices;
        });
    }

    /**
     * Scan Modbus TCP Devices (Port Scan)
     */
    private List<DiscoveredDevice> scanModbus() {
        return AccessController.doPrivileged((PrivilegedAction<List<DiscoveredDevice>>) () -> {
            List<DiscoveredDevice> devices = new ArrayList<>();
            List<String> ipList = generateIPList(getSubnet());

            System.out.println("   → Scanning " + ipList.size() + " IP addresses...");

            try {
                executor = Executors.newFixedThreadPool(getMaxThreads());
                List<Future<DiscoveredDevice>> futures = new ArrayList<>();

                for (String ip : ipList) {
                    futures.add(executor.submit(() -> scanModbusDevice(ip)));
                }

                // Collect results
                for (Future<DiscoveredDevice> future : futures) {
                    try {
                        DiscoveredDevice device = future.get();
                        if (device != null) {
                            devices.add(device);
                            System.out.println("   ✓ Found: Modbus device at " + device.getAddress());
                        }
                    } catch (Exception e) {
                        // Ignore timeout/connection errors
                    }
                }

                executor.shutdown();

            } catch (Exception e) {
                System.err.println("   ✗ Modbus scan error: " + e.getMessage());
            }

            return devices;
        });
    }

    /**
     * Scan single Modbus device
     */
    private DiscoveredDevice scanModbusDevice(String ip) {
        return AccessController.doPrivileged((PrivilegedAction<DiscoveredDevice>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, MODBUS_PORT), getScanTimeout());

                // Send Modbus Read Holding Registers (Function 03)
                byte[] modbusRequest = {
                        0x00, 0x01,  // Transaction ID
                        0x00, 0x00,  // Protocol ID
                        0x00, 0x06,  // Length
                        0x01,        // Unit ID
                        0x03,        // Function Code (Read Holding Registers)
                        0x00, 0x00,  // Start Address
                        0x00, 0x01   // Quantity
                };

                OutputStream out = socket.getOutputStream();
                out.write(modbusRequest);
                out.flush();

                // Read response
                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len > 0 && response[7] == 0x03) {
                    // Valid Modbus response
                    return new DiscoveredDevice(
                            "Modbus_" + ip.replace(".", "_"),
                            "Modbus TCP",
                            ip,
                            MODBUS_PORT,
                            "Modbus TCP Device"
                    );
                }

            } catch (Exception e) {
                // Device not responding
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    /**
     * Scan HTTP/REST Devices (Web Server Detection)
     */
    private List<DiscoveredDevice> scanHTTP() {
        return AccessController.doPrivileged((PrivilegedAction<List<DiscoveredDevice>>) () -> {
            List<DiscoveredDevice> devices = new ArrayList<>();
            List<String> ipList = generateIPList(getSubnet());

            System.out.println("   → Scanning " + ipList.size() + " IP addresses...");

            try {
                executor = Executors.newFixedThreadPool(getMaxThreads());
                List<Future<DiscoveredDevice>> futures = new ArrayList<>();

                for (String ip : ipList) {
                    for (int port : HTTP_PORTS) {
                        futures.add(executor.submit(() -> scanHTTPDevice(ip, port)));
                    }
                }

                // Collect results
                for (Future<DiscoveredDevice> future : futures) {
                    try {
                        DiscoveredDevice device = future.get();
                        if (device != null) {
                            devices.add(device);
                            System.out.println("   ✓ Found: HTTP server at " + device.getAddress() + ":" + device.getPort());
                        }
                    } catch (Exception e) {
                        // Ignore
                    }
                }

                executor.shutdown();

            } catch (Exception e) {
                System.err.println("   ✗ HTTP scan error: " + e.getMessage());
            }

            return devices;
        });
    }

    /**
     * Scan single HTTP device
     */
    private DiscoveredDevice scanHTTPDevice(String ip, int port) {
        return AccessController.doPrivileged((PrivilegedAction<DiscoveredDevice>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), getScanTimeout());

                // Send HTTP HEAD request
                OutputStream out = socket.getOutputStream();
                String request = "HEAD / HTTP/1.1\r\nHost: " + ip + "\r\nConnection: close\r\n\r\n";
                out.write(request.getBytes());
                out.flush();

                // Read response
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = in.readLine();

                if (line != null && line.startsWith("HTTP")) {
                    // Extract server info
                    String serverInfo = "";
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        if (line.toLowerCase().startsWith("server:")) {
                            serverInfo = line.substring(7).trim();
                            break;
                        }
                    }

                    return new DiscoveredDevice(
                            "HTTP_" + ip.replace(".", "_") + "_" + port,
                            "HTTP/REST",
                            ip,
                            port,
                            "Web Server" + (serverInfo.isEmpty() ? "" : " (" + serverInfo + ")")
                    );
                }

            } catch (Exception e) {
                // Not a web server
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    // ==================== Helper Methods ====================

    /**
     * Build BACnet Who-Is message
     */
    private byte[] buildBACnetWhoIs() {
        return new byte[] {
                (byte) 0x81, (byte) 0x0A, (byte) 0x00, (byte) 0x0C,  // BVLC
                (byte) 0x01, (byte) 0x20, (byte) 0xFF, (byte) 0xFF,  // NPDU
                (byte) 0x00, (byte) 0xFF,
                (byte) 0x10, (byte) 0x08   // APDU (Who-Is)
        };
    }

    /**
     * Parse BACnet I-Am response
     */
    private int parseBACnetIAm(byte[] data, int length) {
        try {
            // Simple parsing - look for device instance
            int offset = 4; // Skip BVLC
            if (data[offset] == 0x01) { // NPDU version
                offset++;
                byte control = data[offset++];

                if ((control & 0x20) != 0) {
                    offset += 2;
                    int dlen = data[offset++] & 0xFF;
                    offset += dlen;
                    offset++;
                }

                if (data[offset] == 0x10 && data[offset + 1] == 0x00) {
                    offset += 2;
                    if (data[offset] == (byte) 0xC4) {
                        offset++;
                        return ((data[offset] & 0xFF) << 16) |
                                ((data[offset + 1] & 0xFF) << 8) |
                                (data[offset + 2] & 0xFF);
                    }
                }
            }
        } catch (Exception e) {}
        return -1;
    }

    /**
     * Generate IP list from subnet (e.g., "192.168.1.0/24")
     */
    private List<String> generateIPList(String subnet) {
        List<String> ips = new ArrayList<>();
        try {
            String[] parts = subnet.split("/");
            String baseIP = parts[0];
            int cidr = Integer.parseInt(parts[1]);

            String[] octets = baseIP.split("\\.");
            String network = octets[0] + "." + octets[1] + "." + octets[2] + ".";

            int hosts = (int) Math.pow(2, 32 - cidr) - 2;
            hosts = Math.min(hosts, 254); // Limit to 254

            for (int i = 1; i <= hosts; i++) {
                ips.add(network + i);
            }

        } catch (Exception e) {
            System.err.println("Invalid subnet format: " + subnet);
        }
        return ips;
    }

    /**
     * Calculate broadcast address
     */
    private String calculateBroadcast(String subnet) {
        try {
            String[] parts = subnet.split("/");
            String[] octets = parts[0].split("\\.");
            return octets[0] + "." + octets[1] + "." + octets[2] + ".255";
        } catch (Exception e) {
            return "255.255.255.255";
        }
    }

    /**
     * Create devices from discovered list
     */
    private int createDevicesFromList(List<DiscoveredDevice> discovered) throws Exception {
        int created = 0;
        for (DiscoveredDevice disc : discovered) {
            if (!deviceExists(disc.getName())) {
                BMyPointDevice device = new BMyPointDevice();
                device.setDeviceName(disc.getName());
                device.setDeviceAddress(disc.getAddress() + ":" + disc.getPort());
                device.setDeviceDescription(disc.getProtocol() + " - " + disc.getDescription());

                addDeviceToNetwork(disc.getName(), device);
                created++;
            }
        }
        return created;
    }

    // ==================== Device Management ====================

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

    /**
     * Clear all devices
     */
    public void doClearDevices() throws Exception {
        System.out.println("Clearing all devices...");
        int count = devicesList.size();

        for (BDevice device : devicesList) {
            remove(device.getName());
        }

        devicesList.clear();
        setLastDiscoveryCount(0);

        System.out.println("✅ Cleared " + count + " device(s)");
    }

    // ==================== Action Handlers ====================

    public void doPing() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Universal Network: Status Report");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Version: " + getVersion());
        System.out.println("  Subnet: " + getSubnet());
        System.out.println("  Scan Timeout: " + getScanTimeout() + "ms");
        System.out.println("  Max Threads: " + getMaxThreads());
        System.out.println("");
        System.out.println("  Enabled Protocols:");
        System.out.println("    - BACnet/IP: " + (getEnableBACnet() ? "✅" : "❌"));
        System.out.println("    - Modbus TCP: " + (getEnableModbus() ? "✅" : "❌"));
        System.out.println("    - HTTP/REST: " + (getEnableHTTP() ? "✅" : "❌"));
        System.out.println("");
        System.out.println("  Last Discovery: " + getLastDiscoveryTime());
        System.out.println("  Devices Created: " + getLastDiscoveryCount());
        System.out.println("  Total Devices: " + devicesList.size());
        System.out.println("═══════════════════════════════════════════");
    }

    // ==================== Lifecycle ====================

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("✅ Universal Network: Started");
    }

    @Override
    public void stopped() throws Exception {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        System.out.println("🛑 Universal Network: Stopped");
        super.stopped();
    }

    // ==================== Helper Classes ====================

    /**
     * Container for discovered device info
     */
    private static class DiscoveredDevice {
        private String name;
        private String protocol;
        private String address;
        private int port;
        private String description;

        public DiscoveredDevice(String name, String protocol, String address, int port, String description) {
            this.name = name;
            this.protocol = protocol;
            this.address = address;
            this.port = port;
            this.description = description;
        }

        public String getName() { return name; }
        public String getProtocol() { return protocol; }
        public String getAddress() { return address; }
        public int getPort() { return port; }
        public String getDescription() { return description; }
    }
}