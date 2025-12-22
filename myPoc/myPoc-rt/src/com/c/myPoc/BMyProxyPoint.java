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
import javax.baja.status.*;
import javax.baja.control.*;

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
public class BMyProxyPoint extends BNumericWritable {


    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyProxyPoint(3642654885)1.0$ @*/
/* Generated Fri Dec 19 14:23:13 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
    private volatile boolean isPingPong = false;
    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("ProxyPoint started: " + getName());
        startPolling();
    }

    @Override
    public void stopped() throws Exception {
        stopPolling();
        super.stopped();
    }

    @Override
    public void changed(Property p, Context cx) {
        super.changed(p, cx);

        if (p == out) {
            BStatusNumeric outVal = getOut();
            BStatusNumeric fbVal = getFallback();

            // 1. ถ้าสถานะเป็น Null (เพิ่งเริ่ม) ไม่ต้องทำอะไร
            if (outVal.getStatus().isNull()) return;

            // 2. Logic ตัด Loop: เปรียบเทียบค่า Out vs Fallback
            // ถ้าค่า Out เท่ากับค่าที่เพิ่งอ่านมา (Fallback) เป๊ะๆ
            // แปลว่าไม่มีการ Command (หรือค่า Command ตรงกับค่าจริง) -> ไม่ต้องเขียน
            double outV = outVal.getValue();
            double fbV = fbVal.getValue();

            // ใช้ Math.abs สำหรับเปรียบเทียบ double เพื่อป้องกันเรื่องทศนิยม
            if (Math.abs(outV - fbV) < 0.0001) {
                return; // 🛑 จบงานตรงนี้ ไม่ Write
            }

            // 3. ถ้าไม่เท่ากัน (เช่น User สั่ง Set 50 แต่ค่าที่อ่านได้คือ 40) -> ให้ Write
            try {
                System.out.println("Commanding device to: " + outV);
                writeToDevice(outV);
            } catch (Exception e) {
                System.err.println("Write failed: " + e.getMessage());
            }
        }
    }


    private void startPolling() {
        if (isPolling) return;

        isPolling = true;
        pollingThread = new Thread(() -> {
            while (isPolling) {
                try {
                    double value = readFromDevice();
                    BStatusNumeric statusValue = new BStatusNumeric(value, BStatus.ok);

                    isPingPong = true;
                    try {
                        setFallback(statusValue);
                    } finally {
                        isPingPong = false;
                    }

                    Thread.sleep(getPollInterval());

                } catch (Exception e) {
                    isPingPong = true; // อย่าลืมดักตรง Error ด้วยเผื่อไว้
                    try {
                        setFallback(new BStatusNumeric(0.0, BStatus.fault));
                    } finally {
                        isPingPong = false;
                    }
                    // ...
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
     * Read Modbus (Supports multiple types)
     */
    private double readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;

        int regAddr = getRegisterAddress();
        String type = getRegisterType().toLowerCase();

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                // เลือก Function Code ตามประเภท
                byte fc;
                if (type.contains("coil")) fc = 0x01;            // Read Coils
                else if (type.contains("discrete")) fc = 0x02;   // Read Discrete Inputs
                else if (type.contains("input")) fc = 0x04;      // Read Input Registers
                else fc = 0x03;                                  // Default: Read Holding Registers

                byte[] request = {
                        0x00, 0x01,  // Transaction ID
                        0x00, 0x00,  // Protocol ID
                        0x00, 0x06,  // Length
                        0x01,        // Unit ID
                        fc,          // Function Code
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),  // Start Addr
                        0x00, 0x01   // Quantity = 1
                };

                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                // Parse Response
                if (len >= 9 && response[7] == fc) {
                    int byteCount = response[8];
                    if (fc == 0x01 || fc == 0x02) {
                        // --- Bit Data (Coil/Discrete) ---
                        // Data อยู่ที่ byte 9, bit แรก
                        int val = response[9] & 0x01;
                        return (double) val;
                    } else {
                        // --- Word Data (Register) ---
                        // Data อยู่ที่ byte 9-10 (Big Endian)
                        int val = ((response[9] & 0xFF) << 8) | (response[10] & 0xFF);
                        // ถ้าเป็น Signed Int16
                        if (val > 32767) val -= 65536;
                        return (double) val;
                    }
                }

                // ถ้ามาไม่ถึงตรงนี้ แสดงว่า Error
                throw new RuntimeException("Invalid Modbus response");

            } catch (Exception e) {
                // Return NaN หรือ throw เพื่อให้ setFault ทำงาน
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
        });
    }
    /**
     * เขียนค่าไปยัง Device
     */
    private void writeToDevice(double value) throws Exception {
        String proto = getProtocol().toLowerCase();
        if ("bacnet".equals(proto)) {
            writeBACnet(value);
        } else if ("modbus".equals(proto)) {
            writeModbus(value);
        }
    }

    private void writeBACnet(double val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        // Determine Object Type from Name/Property
        String typeStr = getRegisterType().toLowerCase();
        int objectType = 1; // Default AO
        if (typeStr.contains("ai") || typeStr.contains("analoginput")) objectType = 0;
        else if (typeStr.contains("ao") || typeStr.contains("analogoutput")) objectType = 1;
        else if (typeStr.contains("av") || typeStr.contains("analogvalue")) objectType = 2;
        else if (typeStr.contains("bi") || typeStr.contains("binaryinput")) objectType = 3;
        else if (typeStr.contains("bo") || typeStr.contains("binaryoutput")) objectType = 4;
        else if (typeStr.contains("bv") || typeStr.contains("binaryvalue")) objectType = 5;

        int instance = getRegisterAddress();
        int finalObjectType = objectType;
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(ip);

                // ใช้ Helper สร้าง Packet (ต้องแน่ใจว่าสร้าง BACnetUtil แล้ว)
                // ถ้ายังไม่มี Helper ให้ใช้โค้ดสร้าง Packet แบบ Inline ไปก่อนได้
                // แต่แนะนำให้สร้างไฟล์ BACnetUtil.java ตามที่ให้ไปรอบก่อน
                byte[] tx = BACnetUtil.buildWritePropertyReal(finalObjectType, instance, BACnetUtil.PROP_PRESENT_VALUE, (float) val, 1);

                DatagramPacket p = new DatagramPacket(tx, tx.length, addr, port);
                socket.send(p);

                System.out.println("BACnet Write Success: " + val);

            } catch (Exception e) {
                System.err.println("BACnet Write Error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return null;
        });
    }

    /**
     * Write Modbus (Support Single Register & Single Coil)
     */
    private void writeModbus(double val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;

        int regAddr = getRegisterAddress();
        String type = getRegisterType().toLowerCase();

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                byte[] request;

                // 1. ตัดสินใจว่าจะเขียน Coil หรือ Register
                if (type.contains("coil") || type.contains("binary")) {
                    // --- Write Single Coil (FC 05) ---
                    int outputVal = (val > 0) ? 0xFF00 : 0x0000; // ON=0xFF00, OFF=0x0000
                    request = new byte[] {
                            0x00, 0x02,             // Transaction ID
                            0x00, 0x00,             // Protocol ID
                            0x00, 0x06,             // Length
                            0x01,                   // Unit ID (Default 1)
                            0x05,                   // FC 05: Write Single Coil
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF), // Address
                            (byte)(outputVal >> 8), (byte)(outputVal & 0xFF) // Value
                    };
                } else {
                    // --- Write Single Register (FC 06) ---
                    // แปลง double เป็น int (รองรับแค่ 16-bit ใน PoC นี้)
                    int intVal = (int) val;
                    request = new byte[] {
                            0x00, 0x02,             // Transaction ID
                            0x00, 0x00,             // Protocol ID
                            0x00, 0x06,             // Length
                            0x01,                   // Unit ID (Default 1)
                            0x06,                   // FC 06: Write Single Register
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF), // Address
                            (byte)(intVal >> 8), (byte)(intVal & 0xFF)    // Value
                    };
                }

                // Send
                out.write(request);
                out.flush();

                // Read Response (Simple check)
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len > 0 && (response[7] == 0x05 || response[7] == 0x06)) {
                    System.out.println("Modbus Write Success: " + val);
                } else {
                    System.err.println("Modbus Write Error: Invalid response or Exception Code");
                }

            } catch (Exception e) {
                System.err.println("Modbus Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
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
        int finalObjectType1 = objectType;
        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(2000); // Timeout เร็วขึ้นหน่อย

                // ใช้ Helper สร้าง Packet
                int invokeId = (int) (Math.random() * 255); // Random Invoke ID เพื่อความปลอดภัย
                byte[] tx = BACnetUtil.buildReadPropertyPacket(finalObjectType1, instance, BACnetUtil.PROP_PRESENT_VALUE, invokeId);

                socket.send(new DatagramPacket(tx, tx.length, InetAddress.getByName(ip), port));

                // Receive
                byte[] rxBuf = new byte[512];
                DatagramPacket rxPacket = new DatagramPacket(rxBuf, rxBuf.length);
                socket.receive(rxPacket);

                // Parsing Logic ที่ดีขึ้น
                byte[] data = rxPacket.getData();
                // ข้าม Header: BVLC(4) + NPDU(2) + APDU-Header(3-4)
                // มองหา Application Tag

                int offset = 6; // Start of APDU
                if (data[offset] == 0x30) { // Complex ACK
                    offset += 3; // Skip Type, InvokeID, ServiceACK
                    // Skip Object ID (Tag 0, len 4+1)
                    if (data[offset] == 0x0C) offset += 5;
                    // Skip Prop ID (Tag 1, len 1+1)
                    if (data[offset] == 0x19) offset += 2;
                    // Skip Opening Tag (Tag 3, len 1)
                    if (data[offset] == 0x3E) offset += 1;

                    // Now at Value Tag
                    byte tag = data[offset];
                    if (tag == 0x44) { // Real (Float)
                        int bits = ((data[offset + 1] & 0xFF) << 24) | ((data[offset + 2] & 0xFF) << 16) |
                                ((data[offset + 3] & 0xFF) << 8) | (data[offset + 4] & 0xFF);
                        return (double) Float.intBitsToFloat(bits);
                    } else if (tag == (byte) 0x91) { // Enumerated
                        return (double) (data[offset + 1] & 0xFF);
                    } else if (tag == (byte) 0x21) { // Unsigned Int
                        return (double) (data[offset + 1] & 0xFF); // (simplified 1 byte)
                    }
                }

                return Double.NaN; // หาไม่เจอ

            } catch (Exception e) {
                // return 0.0 or throw
                throw new RuntimeException("Read Failed");
            } finally {
                if (socket != null) socket.close();
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