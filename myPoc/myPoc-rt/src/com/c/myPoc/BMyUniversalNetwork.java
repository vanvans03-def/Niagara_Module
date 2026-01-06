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
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "3.7.1", flags = Flags.READONLY)
@NiagaraProperty(name = "subnet", type = "String", defaultValue = "192.168.1.0/24", flags = Flags.SUMMARY)
@NiagaraProperty(name = "apiUrl", type = "String", defaultValue = "http://localhost:3000/integration/niagara/export?protocol=MODBUS", flags = Flags.SUMMARY)
@NiagaraProperty(name = "apiKey", type = "String", defaultValue = "", flags = Flags.SUMMARY)
@NiagaraProperty(name = "enableBACnet", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "enableModbus", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "enableHTTP", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "scanTimeout", type = "int", defaultValue = "2000")
@NiagaraProperty(name = "maxThreads", type = "int", defaultValue = "50")
@NiagaraProperty(name = "lastDiscoveryCount", type = "int", defaultValue = "0", flags = Flags.READONLY)
@NiagaraProperty(name = "lastDiscoveryTime", type = "String", defaultValue = "", flags = Flags.READONLY)
@NiagaraProperty(name = "localDeviceId", type = "int", defaultValue = "12345")

@NiagaraAction(name = "discoverAll", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(name = "discoverBACnet", flags = Flags.ASYNC)
@NiagaraAction(name = "discoverModbus", flags = Flags.ASYNC)
@NiagaraAction(name = "discoverHTTP", flags = Flags.ASYNC)
//@NiagaraAction(name = "importFromApi", flags = Flags.ASYNC | Flags.SUMMARY)
@NiagaraAction(
        name = "importFromApi",
        parameterType = "BImportDialog",
        defaultValue = "new BImportDialog()",
        flags = Flags.ASYNC | Flags.SUMMARY
)
@NiagaraAction(name = "clearDevices", parameterType = "BBoolean", defaultValue = "BBoolean.FALSE", flags = Flags.ASYNC)
@NiagaraAction(name = "ping")

public class BMyUniversalNetwork extends BDeviceNetwork {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyUniversalNetwork(1163805678)1.0$ @*/
/* Generated Tue Jan 06 17:20:41 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "version"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code version} property.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "3.7.1", null);
  
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
// Property "subnet"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code subnet} property.
   * @see #getSubnet
   * @see #setSubnet
   */
  public static final Property subnet = newProperty(Flags.SUMMARY, "192.168.1.0/24", null);
  
  /**
   * Get the {@code subnet} property.
   * @see #subnet
   */
  public String getSubnet() { return getString(subnet); }
  
  /**
   * Set the {@code subnet} property.
   * @see #subnet
   */
  public void setSubnet(String v) { setString(subnet, v, null); }

////////////////////////////////////////////////////////////////
// Property "apiUrl"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code apiUrl} property.
   * @see #getApiUrl
   * @see #setApiUrl
   */
  public static final Property apiUrl = newProperty(Flags.SUMMARY, "http://localhost:3000/integration/niagara/export?protocol=MODBUS", null);
  
  /**
   * Get the {@code apiUrl} property.
   * @see #apiUrl
   */
  public String getApiUrl() { return getString(apiUrl); }
  
  /**
   * Set the {@code apiUrl} property.
   * @see #apiUrl
   */
  public void setApiUrl(String v) { setString(apiUrl, v, null); }

////////////////////////////////////////////////////////////////
// Property "apiKey"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code apiKey} property.
   * @see #getApiKey
   * @see #setApiKey
   */
  public static final Property apiKey = newProperty(Flags.SUMMARY, "", null);
  
  /**
   * Get the {@code apiKey} property.
   * @see #apiKey
   */
  public String getApiKey() { return getString(apiKey); }
  
  /**
   * Set the {@code apiKey} property.
   * @see #apiKey
   */
  public void setApiKey(String v) { setString(apiKey, v, null); }

////////////////////////////////////////////////////////////////
// Property "enableBACnet"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code enableBACnet} property.
   * @see #getEnableBACnet
   * @see #setEnableBACnet
   */
  public static final Property enableBACnet = newProperty(0, true, null);
  
  /**
   * Get the {@code enableBACnet} property.
   * @see #enableBACnet
   */
  public boolean getEnableBACnet() { return getBoolean(enableBACnet); }
  
  /**
   * Set the {@code enableBACnet} property.
   * @see #enableBACnet
   */
  public void setEnableBACnet(boolean v) { setBoolean(enableBACnet, v, null); }

////////////////////////////////////////////////////////////////
// Property "enableModbus"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code enableModbus} property.
   * @see #getEnableModbus
   * @see #setEnableModbus
   */
  public static final Property enableModbus = newProperty(0, true, null);
  
  /**
   * Get the {@code enableModbus} property.
   * @see #enableModbus
   */
  public boolean getEnableModbus() { return getBoolean(enableModbus); }
  
  /**
   * Set the {@code enableModbus} property.
   * @see #enableModbus
   */
  public void setEnableModbus(boolean v) { setBoolean(enableModbus, v, null); }

////////////////////////////////////////////////////////////////
// Property "enableHTTP"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code enableHTTP} property.
   * @see #getEnableHTTP
   * @see #setEnableHTTP
   */
  public static final Property enableHTTP = newProperty(0, true, null);
  
  /**
   * Get the {@code enableHTTP} property.
   * @see #enableHTTP
   */
  public boolean getEnableHTTP() { return getBoolean(enableHTTP); }
  
  /**
   * Set the {@code enableHTTP} property.
   * @see #enableHTTP
   */
  public void setEnableHTTP(boolean v) { setBoolean(enableHTTP, v, null); }

////////////////////////////////////////////////////////////////
// Property "scanTimeout"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code scanTimeout} property.
   * @see #getScanTimeout
   * @see #setScanTimeout
   */
  public static final Property scanTimeout = newProperty(0, 2000, null);
  
  /**
   * Get the {@code scanTimeout} property.
   * @see #scanTimeout
   */
  public int getScanTimeout() { return getInt(scanTimeout); }
  
  /**
   * Set the {@code scanTimeout} property.
   * @see #scanTimeout
   */
  public void setScanTimeout(int v) { setInt(scanTimeout, v, null); }

////////////////////////////////////////////////////////////////
// Property "maxThreads"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code maxThreads} property.
   * @see #getMaxThreads
   * @see #setMaxThreads
   */
  public static final Property maxThreads = newProperty(0, 50, null);
  
  /**
   * Get the {@code maxThreads} property.
   * @see #maxThreads
   */
  public int getMaxThreads() { return getInt(maxThreads); }
  
  /**
   * Set the {@code maxThreads} property.
   * @see #maxThreads
   */
  public void setMaxThreads(int v) { setInt(maxThreads, v, null); }

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
// Property "lastDiscoveryTime"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code lastDiscoveryTime} property.
   * @see #getLastDiscoveryTime
   * @see #setLastDiscoveryTime
   */
  public static final Property lastDiscoveryTime = newProperty(Flags.READONLY, "", null);
  
  /**
   * Get the {@code lastDiscoveryTime} property.
   * @see #lastDiscoveryTime
   */
  public String getLastDiscoveryTime() { return getString(lastDiscoveryTime); }
  
  /**
   * Set the {@code lastDiscoveryTime} property.
   * @see #lastDiscoveryTime
   */
  public void setLastDiscoveryTime(String v) { setString(lastDiscoveryTime, v, null); }

////////////////////////////////////////////////////////////////
// Property "localDeviceId"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code localDeviceId} property.
   * @see #getLocalDeviceId
   * @see #setLocalDeviceId
   */
  public static final Property localDeviceId = newProperty(0, 12345, null);
  
  /**
   * Get the {@code localDeviceId} property.
   * @see #localDeviceId
   */
  public int getLocalDeviceId() { return getInt(localDeviceId); }
  
  /**
   * Set the {@code localDeviceId} property.
   * @see #localDeviceId
   */
  public void setLocalDeviceId(int v) { setInt(localDeviceId, v, null); }

////////////////////////////////////////////////////////////////
// Action "discoverAll"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverAll} action.
   * @see #discoverAll()
   */
  public static final Action discoverAll = newAction(Flags.ASYNC | Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code discoverAll} action.
   * @see #discoverAll
   */
  public void discoverAll() { invoke(discoverAll, null, null); }

////////////////////////////////////////////////////////////////
// Action "discoverBACnet"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverBACnet} action.
   * @see #discoverBACnet()
   */
  public static final Action discoverBACnet = newAction(Flags.ASYNC, null);
  
  /**
   * Invoke the {@code discoverBACnet} action.
   * @see #discoverBACnet
   */
  public void discoverBACnet() { invoke(discoverBACnet, null, null); }

////////////////////////////////////////////////////////////////
// Action "discoverModbus"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverModbus} action.
   * @see #discoverModbus()
   */
  public static final Action discoverModbus = newAction(Flags.ASYNC, null);
  
  /**
   * Invoke the {@code discoverModbus} action.
   * @see #discoverModbus
   */
  public void discoverModbus() { invoke(discoverModbus, null, null); }

////////////////////////////////////////////////////////////////
// Action "discoverHTTP"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverHTTP} action.
   * @see #discoverHTTP()
   */
  public static final Action discoverHTTP = newAction(Flags.ASYNC, null);
  
  /**
   * Invoke the {@code discoverHTTP} action.
   * @see #discoverHTTP
   */
  public void discoverHTTP() { invoke(discoverHTTP, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearDevices"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code clearDevices} action.
   * @see #clearDevices(BBoolean parameter)
   */
  public static final Action clearDevices = newAction(Flags.ASYNC, BBoolean.FALSE, null);
  
  /**
   * Invoke the {@code clearDevices} action.
   * @see #clearDevices
   */
  public void clearDevices(BBoolean parameter) { invoke(clearDevices, parameter, null); }

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
// Action "importFromApi"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code importFromApi} action.
   * @see #importFromApi(BImportDialog parameter)
   */
  public static final Action importFromApi = newAction(Flags.ASYNC | Flags.SUMMARY, new BImportDialog(), null);
  
  /**
   * Invoke the {@code importFromApi} action.
   * @see #importFromApi
   */
  public void importFromApi(BImportDialog parameter) { invoke(importFromApi, parameter, null); }

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
    private List<BDevice> devicesList = new ArrayList<>();
    private ExecutorService executor;

    // ==================== BDeviceNetwork Required Methods ====================
    @Override
    public Type getDeviceType() { return BMyPointDevice.TYPE; }
    @Override
    public Type getDeviceFolderType() { return BDeviceFolder.TYPE; }

    // ==================== API Import Logic ====================

    public void doImportFromApi(BImportDialog dialog) {
        // 1. ดึงค่าจาก Dialog
        String apiKey = dialog.getApiKey();
        BProtocolSelect protocolEnum = dialog.getProtocol();

        // 2. เลือก URL อัตโนมัติตาม Protocol
        String targetUrl;
        if (protocolEnum.getOrdinal() == BProtocolSelect.BACNET) {
            targetUrl = "http://localhost:3000/integration/niagara/export?protocol=BACNET";
        } else {
            targetUrl = "http://localhost:3000/integration/niagara/export?protocol=MODBUS";
        }

        System.out.println("═══════════════════════════════════════════");
        System.out.println("📥 Importing from API (Dialog Input)");
        System.out.println("   ⚙️ Protocol: " + protocolEnum.getTag());
        System.out.println("   🌐 URL: " + targetUrl);
        System.out.println("   🔑 Key: " + (apiKey.isEmpty() ? "None" : "******"));
        System.out.println("═══════════════════════════════════════════");

        try {
            // 3. ใช้ฟังก์ชัน fetchUrl เดิม (ส่ง URL ใหม่เข้าไป)
            String json = fetchUrl(targetUrl, apiKey);

            if (json == null || json.isEmpty()) {
                System.out.println("❌ No data received from API");
                return;
            }

            // 4. เรียกใช้ Logic การ Parse เดิมที่มีอยู่แล้ว
            Object result = new SimpleJsonParser(json).parse();
            if (result == null) {
                System.err.println("❌ Failed to parse JSON");
                return;
            }

            Map<String, Object> root = (Map<String, Object>) result;

            Object devicesObj = root.get("devices");
            int importedCount = 0;

            Collection<Object> deviceCollection = null;
            if (devicesObj instanceof List) {
                deviceCollection = (List<Object>) devicesObj;
            } else if (devicesObj instanceof Map) {
                deviceCollection = ((Map<String, Object>) devicesObj).values();
            }

            if (deviceCollection != null) {
                for (Object obj : deviceCollection) {
                    if (obj instanceof Map) {
                        Map<String, Object> devData = (Map<String, Object>) obj;
                        try {
                            importedCount += processDeviceImport(devData);
                        } catch (Exception e) {
                            System.err.println("   ❌ Error importing device: " + devData.get("name") + " - " + e.getMessage());
                        }
                    }
                }
            }

            setLastDiscoveryCount(importedCount);
            setLastDiscoveryTime(new java.util.Date().toString());
            System.out.println("✅ Import Complete. Created/Updated " + importedCount + " devices.");

        } catch (Exception e) {
            System.err.println("❌ Import Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private int processDeviceImport(Map<String, Object> devData) throws Exception {
        String rawName = (String) devData.get("name");
        String name = sanitizeName(rawName); // ✅ Sanitize Name

        String ip = (String) devData.get("ip");
        Number portNum = (Number) devData.get("port");
        String protocol = (String) devData.get("protocol");
        Number devId = (Number) devData.get("instanceId");
        if (devId == null) devId = (Number) devData.get("unitId");

        String deviceAddress = ip + ":" + portNum;

        System.out.println("   → Processing: " + rawName + " (" + protocol + ")");

        BMyPointDevice device;
        boolean created = false;

        if (get(name) instanceof BMyPointDevice) {
            device = (BMyPointDevice) get(name);
        } else {
            device = new BMyPointDevice();
            device.setDeviceName(rawName);
            add(name, device);
            created = true;
            System.out.println("      Created new device slot: " + name);
        }

        device.setDeviceAddress(deviceAddress);
        device.setProtocol(protocol != null ? protocol.toLowerCase() : "unknown");
        if (devId != null) device.setDeviceId(devId.intValue());
        device.setDeviceDescription("Imported from BMS API");

        // Process Points
        Object pointsObj = devData.get("points");
        Collection<Object> pointsList = null;

        if (pointsObj instanceof List) {
            pointsList = (List<Object>) pointsObj;
        } else if (pointsObj instanceof Map) {
            pointsList = ((Map<String, Object>) pointsObj).values();
        }

        if (pointsList != null && !pointsList.isEmpty()) {
            for (Object pObj : pointsList) {
                if (pObj instanceof Map) {
                    Map<String, Object> pData = (Map<String, Object>) pObj;
                    String rawPName = (String) pData.get("name");
                    String pName = sanitizeName(rawPName);

                    Number pAddr = (Number) pData.get("address");
                    String pType = (String) pData.get("objectType");
                    String pDataType = (String) pData.get("dataType");

                    if (pName != null && pAddr != null) {
                        device.createPointFromImport(
                                pName,
                                pAddr.intValue(),
                                pType,
                                pDataType,
                                device.getProtocol()
                        );
                    }
                }
            }
        }
        return created ? 1 : 0;
    }

    private String sanitizeName(String input) {
        if (input == null || input.isEmpty()) return "Device_" + System.currentTimeMillis();
        String valid = input.trim().replaceAll("[^a-zA-Z0-9_]", "_"); // Hyphens are removed
        if (Character.isDigit(valid.charAt(0))) {
            valid = "_" + valid;
        }
        return valid;
    }

    private String fetchUrl(String urlString, String key) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            if (key != null && !key.isEmpty()) {
                conn.setRequestProperty("x-api-key", key);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }
        } catch (Exception e) {
            System.err.println("HTTP Get Error: " + e.getMessage());
            return null;
        }
        return result.toString();
    }

    // ==================== Clear Devices Action (FIXED) ====================

    public void doClearDevices(BBoolean confirm) throws Exception {
        if (!confirm.getBoolean()) {
            System.out.println("❌ Clear devices cancelled (Must check confirm).");
            return;
        }

        System.out.println("🗑️ Clearing all devices...");
        List<String> toRemove = new ArrayList<>();
        SlotCursor cursor = getSlots();

        while (cursor.next()) {
            // FIX: ต้องเช็ค isProperty() ก่อนเรียก get()
            if (cursor.slot().isProperty()) {
                try {
                    if (cursor.get() instanceof BDevice) {
                        toRemove.add(cursor.slot().getName());
                    }
                } catch (Exception ignored) {}
            }
        }

        for (String name : toRemove) {
            remove(name);
        }

        devicesList.clear();
        setLastDiscoveryCount(0);
        System.out.println("✅ Cleared " + toRemove.size() + " device(s).");
    }

    // ==================== Discovery Actions ====================

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
            if (getEnableBACnet()) {
                System.out.println("🔍 [1/3] BACnet Discovery...");
                allDevices.addAll(scanBACnet());
            }

            if (getEnableModbus()) {
                System.out.println("🔍 [2/3] Modbus Discovery...");
                allDevices.addAll(scanModbus());
            }

            if (getEnableHTTP()) {
                System.out.println("🔍 [3/3] HTTP Discovery...");
                allDevices.addAll(scanHTTP());
            }

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
            System.out.println("═══════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("❌ Discovery failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void doDiscoverBACnet() throws Exception {
        System.out.println("🔍 BACnet Discovery Starting...");
        List<DiscoveredDevice> devices = scanBACnet();
        int created = createDevicesFromList(devices);
        System.out.println("✅ BACnet Discovery: Found " + devices.size() + ", Created " + created);
    }

    public void doDiscoverModbus() throws Exception {
        System.out.println("🔍 Modbus Discovery Starting...");
        List<DiscoveredDevice> devices = scanModbus();
        int created = createDevicesFromList(devices);
        System.out.println("✅ Modbus Discovery: Found " + devices.size() + ", Created " + created);
    }

    public void doDiscoverHTTP() throws Exception {
        System.out.println("🔍 HTTP Discovery Starting...");
        List<DiscoveredDevice> devices = scanHTTP();
        int created = createDevicesFromList(devices);
        System.out.println("✅ HTTP Discovery: Found " + devices.size() + ", Created " + created);
    }

    // ==================== Protocol Scanners ====================

    private List<DiscoveredDevice> scanBACnet() {
        List<DiscoveredDevice> devices = new ArrayList<>();
        return AccessController.doPrivileged((PrivilegedAction<List<DiscoveredDevice>>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(BACNET_PORT);
                socket.setBroadcast(true);
                socket.setSoTimeout(getScanTimeout());

                byte[] whoIs = buildBACnetWhoIs();
                String broadcastAddr = calculateBroadcast(getSubnet());
                InetAddress broadcast = InetAddress.getByName(broadcastAddr);
                DatagramPacket sendPacket = new DatagramPacket(whoIs, whoIs.length, broadcast, BACNET_PORT);
                socket.send(sendPacket);
                System.out.println("   → Who-Is broadcast sent to " + broadcastAddr);

                byte[] buffer = new byte[1024];
                long startTime = System.currentTimeMillis();

                while (System.currentTimeMillis() - startTime < getScanTimeout()) {
                    try {
                        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                        socket.receive(receivePacket);

                        int deviceId = parseBACnetIAm(receivePacket.getData(), receivePacket.getLength());
                        if (deviceId > 0) {
                            String ip = receivePacket.getAddress().getHostAddress();
                            devices.add(new DiscoveredDevice("BACnet_" + deviceId, "BACnet/IP", ip, BACNET_PORT, "BACnet Device ID: " + deviceId));
                            System.out.println("   ✓ Found: BACnet Device " + deviceId + " at " + ip);
                        }
                    } catch (SocketTimeoutException e) { break; }
                }
            } catch (Exception e) {
                System.err.println("   ✗ BACnet scan error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return devices;
        });
    }

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
                for (Future<DiscoveredDevice> future : futures) {
                    try {
                        DiscoveredDevice device = future.get();
                        if (device != null) {
                            devices.add(device);
                            System.out.println("   ✓ Found: Modbus device at " + device.getAddress());
                        }
                    } catch (Exception e) {}
                }
                executor.shutdown();
            } catch (Exception e) {
                System.err.println("   ✗ Modbus scan error: " + e.getMessage());
            }
            return devices;
        });
    }

    private DiscoveredDevice scanModbusDevice(String ip) {
        return AccessController.doPrivileged((PrivilegedAction<DiscoveredDevice>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, MODBUS_PORT), getScanTimeout());
                byte[] modbusRequest = {0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03, 0x00, 0x00, 0x00, 0x01};
                OutputStream out = socket.getOutputStream();
                out.write(modbusRequest);
                out.flush();
                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);
                if (len > 0 && response[7] == 0x03) {
                    return new DiscoveredDevice("Modbus_" + ip.replace(".", "_"), "Modbus TCP", ip, MODBUS_PORT, "Modbus TCP Device");
                }
            } catch (Exception e) {
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

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
                for (Future<DiscoveredDevice> future : futures) {
                    try {
                        DiscoveredDevice device = future.get();
                        if (device != null) {
                            devices.add(device);
                            System.out.println("   ✓ Found: HTTP server at " + device.getAddress() + ":" + device.getPort());
                        }
                    } catch (Exception e) {}
                }
                executor.shutdown();
            } catch (Exception e) {
                System.err.println("   ✗ HTTP scan error: " + e.getMessage());
            }
            return devices;
        });
    }

    private DiscoveredDevice scanHTTPDevice(String ip, int port) {
        return AccessController.doPrivileged((PrivilegedAction<DiscoveredDevice>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), getScanTimeout());
                OutputStream out = socket.getOutputStream();
                String request = "HEAD / HTTP/1.1\r\nHost: " + ip + "\r\nConnection: close\r\n\r\n";
                out.write(request.getBytes());
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = in.readLine();
                if (line != null && line.startsWith("HTTP")) {
                    String serverInfo = "";
                    while ((line = in.readLine()) != null && !line.isEmpty()) {
                        if (line.toLowerCase().startsWith("server:")) {
                            serverInfo = line.substring(7).trim();
                            break;
                        }
                    }
                    return new DiscoveredDevice("HTTP_" + ip.replace(".", "_") + "_" + port, "HTTP/REST", ip, port, "Web Server" + (serverInfo.isEmpty() ? "" : " (" + serverInfo + ")"));
                }
            } catch (Exception e) {
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    // ==================== Helper Methods for Discovery ====================

    private byte[] buildBACnetWhoIs() {
        return new byte[]{(byte) 0x81, (byte) 0x0A, (byte) 0x00, (byte) 0x0C, (byte) 0x01, (byte) 0x20, (byte) 0xFF, (byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0x10, (byte) 0x08};
    }

    private int parseBACnetIAm(byte[] data, int length) {
        try {
            int offset = 4;
            if (data[offset] == 0x01) {
                offset++;
                byte control = data[offset++];
                if ((control & 0x20) != 0) {
                    offset += 2;
                    int dlen = data[offset++] & 0xFF;
                    offset += dlen + 1;
                }
                if (data[offset] == 0x10 && data[offset + 1] == 0x00) {
                    offset += 2;
                    if (data[offset] == (byte) 0xC4) {
                        offset++;
                        return ((data[offset] & 0xFF) << 16) | ((data[offset + 1] & 0xFF) << 8) | (data[offset + 2] & 0xFF);
                    }
                }
            }
        } catch (Exception e) {}
        return -1;
    }

    private List<String> generateIPList(String subnet) {
        List<String> ips = new ArrayList<>();
        try {
            String[] parts = subnet.split("/");
            String baseIP = parts[0];
            int cidr = Integer.parseInt(parts[1]);
            String[] octets = baseIP.split("\\.");
            String network = octets[0] + "." + octets[1] + "." + octets[2] + ".";
            int hosts = (int) Math.pow(2, 32 - cidr) - 2;
            hosts = Math.min(hosts, 254);
            for (int i = 1; i <= hosts; i++) ips.add(network + i);
        } catch (Exception e) {
            System.err.println("Invalid subnet format: " + subnet);
        }
        return ips;
    }

    private String calculateBroadcast(String subnet) {
        try {
            String[] parts = subnet.split("/");
            String[] octets = parts[0].split("\\.");
            return octets[0] + "." + octets[1] + "." + octets[2] + ".255";
        } catch (Exception e) {
            return "255.255.255.255";
        }
    }

    private int createDevicesFromList(List<DiscoveredDevice> discovered) throws Exception {
        int created = 0;
        for (DiscoveredDevice disc : discovered) {
            String safeName = sanitizeName(disc.getName());
            if (!deviceExists(safeName)) {
                BMyPointDevice device = new BMyPointDevice();
                device.setDeviceName(disc.getName());
                device.setDeviceAddress(disc.getAddress() + ":" + disc.getPort());
                device.setDeviceDescription(disc.getProtocol() + " - " + disc.getDescription());

                addDeviceToNetwork(safeName, device);
                created++;

                try {
                    System.out.println("");
                    System.out.println("🔍 Auto-discovering points for " + disc.getName() + "...");
                    device.doDiscoverPoints();
                } catch (Exception e) {
                    System.err.println("⚠️ Point discovery failed: " + e.getMessage());
                }
            }
        }
        return created;
    }

    @Override
    public BDevice[] getDevices() { return devicesList.toArray(new BDevice[0]); }

    private void addDeviceToNetwork(String name, BMyPointDevice device) throws Exception {
        add(name, device);
        devicesList.add(device);
    }

    private boolean deviceExists(String name) {
        if (get(name) != null) return true;
        for (BDevice device : devicesList) {
            if (device instanceof BMyPointDevice) {
                BMyPointDevice d = (BMyPointDevice) device;
                if (d.getDeviceName().equals(name)) return true;
            }
        }
        return false;
    }

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("✅ Universal Network: Started");
        devicesList.clear();
        SlotCursor cursor = getSlots();
        while (cursor.next()) {
            if (cursor.get() instanceof BDevice) {
                devicesList.add((BDevice) cursor.get());
            }
        }
    }

    @Override
    public void stopped() throws Exception {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        System.out.println("🛑 Universal Network: Stopped");
        super.stopped();
    }

    public void doPing() {
        System.out.println("Universal Network is Alive!");
    }

    // ==================== Helper Classes ====================

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

    // ==================== Simple Embedded JSON Parser ====================

    private static class SimpleJsonParser {
        private final String json;
        private int pos = 0;
        private final int length;

        public SimpleJsonParser(String json) {
            this.json = json;
            this.length = json.length();
        }

        public Object parse() {
            consumeWhitespace();
            char c = peek();
            if (c == '{') return parseObject();
            if (c == '[') return parseArray();
            throw new RuntimeException("Invalid JSON root: must be Object or Array");
        }

        private Object parseValue() {
            consumeWhitespace();
            char c = peek();
            if (c == '{') return parseObject();
            if (c == '[') return parseArray();
            if (c == '"') return parseString();
            if (c == 't' || c == 'f') return parseBoolean();
            if (c == 'n') return parseNull();
            if (Character.isDigit(c) || c == '-') return parseNumber();
            throw new RuntimeException("Unexpected char at " + pos + ": " + c);
        }

        private Map<String, Object> parseObject() {
            Map<String, Object> map = new HashMap<>();
            consume('{');
            consumeWhitespace();
            if (peek() == '}') {
                consume('}');
                return map;
            }
            while (true) {
                String key = parseString();
                consumeWhitespace();
                consume(':');
                Object value = parseValue();
                map.put(key, value);
                consumeWhitespace();
                if (peek() == '}') break;
                consume(',');
                consumeWhitespace();
            }
            consume('}');
            return map;
        }

        private List<Object> parseArray() {
            List<Object> list = new ArrayList<>();
            consume('[');
            consumeWhitespace();
            if (peek() == ']') {
                consume(']');
                return list;
            }
            while (true) {
                list.add(parseValue());
                consumeWhitespace();
                if (peek() == ']') break;
                consume(',');
            }
            consume(']');
            return list;
        }

        private String parseString() {
            consume('"');
            StringBuilder sb = new StringBuilder();
            while (pos < length) {
                char c = json.charAt(pos++);
                if (c == '"') return sb.toString();
                if (c == '\\') {
                    char esc = json.charAt(pos++);
                    if (esc == 'n') sb.append('\n');
                    else if (esc == 't') sb.append('\t');
                    else if (esc == 'r') sb.append('\r');
                    else if (esc == '"') sb.append('"');
                    else if (esc == '\\') sb.append('\\');
                    else sb.append(esc);
                } else {
                    sb.append(c);
                }
            }
            throw new RuntimeException("Unterminated string");
        }

        private Number parseNumber() {
            int start = pos;
            while (pos < length && (Character.isDigit(json.charAt(pos)) || json.charAt(pos) == '.' || json.charAt(pos) == '-')) {
                pos++;
            }
            String numStr = json.substring(start, pos);
            if (numStr.contains(".")) return Double.parseDouble(numStr);
            try {
                return Integer.parseInt(numStr);
            } catch (NumberFormatException e) {
                return Long.parseLong(numStr);
            }
        }

        private Boolean parseBoolean() {
            if (json.startsWith("true", pos)) { pos += 4; return true; }
            if (json.startsWith("false", pos)) { pos += 5; return false; }
            throw new RuntimeException("Invalid boolean");
        }

        private Object parseNull() {
            if (json.startsWith("null", pos)) { pos += 4; return null; }
            throw new RuntimeException("Invalid null");
        }

        private void consumeWhitespace() {
            while (pos < length && Character.isWhitespace(json.charAt(pos))) pos++;
        }

        private void consume(char expected) {
            consumeWhitespace();
            if (pos >= length || json.charAt(pos) != expected)
                throw new RuntimeException("Expected " + expected + " at " + pos);
            pos++;
        }

        private char peek() {
            if (pos >= length) throw new RuntimeException("Unexpected EOF");
            return json.charAt(pos);
        }
    }
}