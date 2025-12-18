package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.control.*;
import javax.baja.status.*;
import java.io.*;
import java.net.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Proxy Point for reading values from external devices
 * Supports: Modbus, BACnet, HTTP
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "String", defaultValue = "holdingRegister")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
public class BMyProxyPoint extends BNumericPoint {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyProxyPoint(3642654885)1.0$ @*/
/* Generated Wed Dec 17 16:22:45 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "address"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, "", null);
  
  /**
   * Get the {@code address} property.
   * @see #address
   */
  public String getAddress() { return getString(address); }
  
  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(String v) { setString(address, v, null); }

////////////////////////////////////////////////////////////////
// Property "registerType"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code registerType} property.
   * @see #getRegisterType
   * @see #setRegisterType
   */
  public static final Property registerType = newProperty(0, "holdingRegister", null);
  
  /**
   * Get the {@code registerType} property.
   * @see #registerType
   */
  public String getRegisterType() { return getString(registerType); }
  
  /**
   * Set the {@code registerType} property.
   * @see #registerType
   */
  public void setRegisterType(String v) { setString(registerType, v, null); }

////////////////////////////////////////////////////////////////
// Property "registerAddress"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code registerAddress} property.
   * @see #getRegisterAddress
   * @see #setRegisterAddress
   */
  public static final Property registerAddress = newProperty(0, 0, null);
  
  /**
   * Get the {@code registerAddress} property.
   * @see #registerAddress
   */
  public int getRegisterAddress() { return getInt(registerAddress); }
  
  /**
   * Set the {@code registerAddress} property.
   * @see #registerAddress
   */
  public void setRegisterAddress(int v) { setInt(registerAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "protocol"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code protocol} property.
   * @see #getProtocol
   * @see #setProtocol
   */
  public static final Property protocol = newProperty(0, "modbus", null);
  
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
// Property "pollInterval"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code pollInterval} property.
   * @see #getPollInterval
   * @see #setPollInterval
   */
  public static final Property pollInterval = newProperty(0, 5000, null);
  
  /**
   * Get the {@code pollInterval} property.
   * @see #pollInterval
   */
  public int getPollInterval() { return getInt(pollInterval); }
  
  /**
   * Set the {@code pollInterval} property.
   * @see #pollInterval
   */
  public void setPollInterval(int v) { setInt(pollInterval, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyProxyPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("ProxyPoint started: " + getName());

        // Start polling thread
        startPolling();
    }

    @Override
    public void stopped() throws Exception {
        stopPolling();
        super.stopped();
    }

    private void startPolling() {
        if (isPolling) return;

        isPolling = true;
        pollingThread = new Thread(() -> {
            while (isPolling) {
                try {
                    // Read value from device
                    double value = readFromDevice();

                    // ✅ แก้: ใช้ BStatusNumeric แทน double
                    BStatusNumeric statusValue = new BStatusNumeric(value, BStatus.ok);
                    setOut(statusValue);

                    // Sleep based on poll interval
                    Thread.sleep(getPollInterval());

                } catch (Exception e) {
                    System.err.println("Poll error: " + e.getMessage());

                    // ✅ แก้: Set fault status
                    BStatusNumeric faultValue = new BStatusNumeric(0.0, BStatus.fault);
                    setOut(faultValue);

                    try {
                        Thread.sleep(getPollInterval());
                    } catch (InterruptedException ie) {}
                }
            }
        });

        pollingThread.setDaemon(true);
        pollingThread.start();
    }

    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) {
            pollingThread.interrupt();
        }
    }

    /**
     * Read value from device based on protocol
     */
    private double readFromDevice() throws Exception {
        String proto = getProtocol().toLowerCase();

        switch (proto) {
            case "modbus":
                return readModbus();
            case "bacnet":
                return readBACnet();
            case "http":
                return readHTTP();
            default:
                throw new Exception("Unsupported protocol: " + proto);
        }
    }

    /**
     * Read Modbus Holding Register
     */
    private double readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) {
            throw new Exception("Device not found");
        }

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 3000);

                // Build Modbus Read Holding Registers request
                int regAddr = getRegisterAddress();
                byte[] request = {
                        0x00, 0x01,  // Transaction ID
                        0x00, 0x00,  // Protocol ID
                        0x00, 0x06,  // Length
                        0x01,        // Unit ID
                        0x03,        // Function Code (Read Holding Registers)
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),  // Start Address
                        0x00, 0x01   // Quantity (1 register)
                };

                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();

                // Read response
                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len >= 11 && response[7] == 0x03) {
                    // Extract register value (2 bytes, big-endian)
                    int value = ((response[9] & 0xFF) << 8) | (response[10] & 0xFF);
                    return (double) value;
                }

                throw new RuntimeException("Invalid Modbus response");

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (socket != null) {
                    try { socket.close(); } catch (Exception e) {}
                }
            }
        });
    }

    /**
     * Read BACnet Present Value
     */
    /**
     * Read BACnet Present Value using Raw UDP (PoC Implementation)
     */
    private double readBACnet() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) {
            throw new Exception("Device not found");
        }

        // 1. Prepare Connection Info
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        // 2. Determine Object Type & Instance
        // Mapping: AnalogInput=0, AnalogOutput=1, AnalogValue=2, BinaryInput=3, etc.
        int objectType = 0; // Default to AnalogInput
        String typeStr = getRegisterType().toLowerCase(); // หรือใช้ชื่อ Point ถ้าไม่ได้ set registerType
        if (getName().toLowerCase().contains("analogoutput") || typeStr.contains("analogoutput")) objectType = 1;
        else if (getName().toLowerCase().contains("analogvalue") || typeStr.contains("analogvalue")) objectType = 2;
        else if (getName().toLowerCase().contains("binaryinput") || typeStr.contains("binaryinput")) objectType = 3;
        else if (getName().toLowerCase().contains("binaryoutput") || typeStr.contains("binaryoutput")) objectType = 4;

        int instance = getRegisterAddress();
        int propertyId = 85; // Present_Value

        int finalObjectType = objectType;
        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);
                InetAddress address = InetAddress.getByName(ip);

                // 3. Construct BACnet/IP Packet (ReadProperty Request)
                // BVLC (4 bytes) + NPDU (2 bytes) + APDU (variable)

                // --- APDU Construction ---
                // Service: Confirmed-Request (0x00), MaxSegs (0x05), InvokeID (0x01), ServiceChoice: ReadProperty (0x0C)
                // ObjectIdentifier (Tag 0): Type + Instance
                // PropertyIdentifier (Tag 1): 85 (Present_Value)

                // Calculate Object ID parts
                // ObjectType is bits 22-31, Instance is bits 0-21
                // We construct the 4 bytes for Object ID manually
                int objectIdPacked = (finalObjectType << 22) | (instance & 0x3FFFFF);

                byte[] apdu = new byte[] {
                        0x00,       // PDU Type: Confirmed Request
                        0x05,       // Max Segments / APDU Size
                        0x01,       // Invoke ID (Hardcoded 1 for PoC)
                        0x0c,       // Service Choice: ReadProperty (12)

                        // Tag 0: Object Identifier (Context Specific, Length 4) -> 0x0C
                        0x0c,
                        (byte)((objectIdPacked >> 24) & 0xFF),
                        (byte)((objectIdPacked >> 16) & 0xFF),
                        (byte)((objectIdPacked >> 8) & 0xFF),
                        (byte)(objectIdPacked & 0xFF),

                        // Tag 1: Property Identifier (Context Specific, Length 1) -> 0x19
                        0x19,
                        (byte)propertyId // 85 (0x55)
                };

                // --- BVLC & NPDU Construction ---
                int packetLength = 4 + 2 + apdu.length; // BVLC(4) + NPDU(2) + APDU
                byte[] buffer = new byte[packetLength];

                // BVLC
                buffer[0] = (byte) 0x81; // BACnet/IP
                buffer[1] = (byte) 0x0a; // Function: Original-Unicast-NPDU
                buffer[2] = (byte) ((packetLength >> 8) & 0xFF); // Length High
                buffer[3] = (byte) (packetLength & 0xFF);        // Length Low

                // NPDU
                buffer[4] = (byte) 0x01; // Version
                buffer[5] = (byte) 0x04; // Control (Expecting Reply)

                // Copy APDU
                System.arraycopy(apdu, 0, buffer, 6, apdu.length);

                // 4. Send Packet
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
                socket.send(packet);

                // 5. Receive Response
                byte[] rxBuffer = new byte[512];
                DatagramPacket rxPacket = new DatagramPacket(rxBuffer, rxBuffer.length);
                socket.receive(rxPacket);

                byte[] data = rxPacket.getData();
                int len = rxPacket.getLength();

                // 6. Basic Parsing (Heuristic / Simplified)
                // Check for Complex ACK (PDU Type 0x30)
                // We skip BVLC(4) + NPDU(2?) to find APDU.
                // Note: Response NPDU might vary, usually 2 bytes if no source info.

                // Scan for "Real" Application Tag (0x44) -> Float value
                // Or "Enumerated" Application Tag (0x91) -> Binary value (0/1)

                for (int i = 6; i < len - 4; i++) {
                    // Check for Real (Float) Tag: 0x44 (Application Tag 4, Length 4)
                    if (data[i] == 0x44) {
                        int bits = ((data[i+1] & 0xFF) << 24) |
                                ((data[i+2] & 0xFF) << 16) |
                                ((data[i+3] & 0xFF) << 8)  |
                                (data[i+4] & 0xFF);
                        return (double) Float.intBitsToFloat(bits);
                    }

                    // Check for Enumerated Tag: 0x91 (Application Tag 9, Length 1) - Common for Binary PV
                    if (data[i] == (byte)0x91) {
                        return (double) (data[i+1] & 0xFF);
                    }
                }

                // If we got a response but couldn't parse logic
                System.out.println("BACnet Response received but value not found/parsed.");
                return 0.0; // Fail-safe

            } catch (SocketTimeoutException se) {
                throw new RuntimeException("BACnet Timeout");
            } catch (Exception e) {
                throw new RuntimeException("BACnet Error: " + e.getMessage());
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
        });
    }

    /**
     * Read HTTP/REST API
     */
    private double readHTTP() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) {
            throw new Exception("Device not found");
        }

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 80;

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            try {
                URL url = new URL("http://" + ip + ":" + port + "/api/value");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    // Parse JSON response
                    String json = response.toString();
                    int idx = json.indexOf("\"value\"");
                    if (idx > 0) {
                        int start = json.indexOf(":", idx) + 1;
                        int end = json.indexOf(",", start);
                        if (end < 0) end = json.indexOf("}", start);

                        String valueStr = json.substring(start, end).trim();
                        return Double.parseDouble(valueStr);
                    }
                }

                throw new RuntimeException("HTTP request failed: " + responseCode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Get parent device
     */
    private BMyPointDevice getParentDevice() {
        BComplex parent = getParent();
        while (parent != null && !(parent instanceof BMyPointDevice)) {
            if (parent instanceof BComponent) {
                parent = ((BComponent) parent).getParent();
            } else {
                break;
            }
        }
        return (BMyPointDevice) parent;
    }
}