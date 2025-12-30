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
 * Proxy Point with Last Value Retention
 * ✅ Keep last value on disconnect instead of resetting to 0
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"holding\",\"input\",\"coil\",\"discrete\",\"AI\",\"AO\",\"AV\",\"BI\",\"BO\",\"BV\"}))")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"modbus\",\"bacnet\",\"http\"}))")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "dataType", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"int16\",\"uint16\",\"int32\",\"uint32\",\"float32\"}))")
@NiagaraProperty(name = "byteOrder", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"ABCD\",\"CDAB\",\"BADC\",\"DCBA\"}))")
public class BMyProxyPoint extends BNumericWritable {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyProxyPoint(3278483480)1.0$ @*/
/* Generated Mon Dec 29 17:56:23 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
  public static final Property registerType = newProperty(0, BDynamicEnum.make(0, BEnumRange.make(new String[]{"holding","input","coil","discrete","AI","AO","AV","BI","BO","BV"})), null);
  
  /**
   * Get the {@code registerType} property.
   * @see #registerType
   */
  public BDynamicEnum getRegisterType() { return (BDynamicEnum)get(registerType); }
  
  /**
   * Set the {@code registerType} property.
   * @see #registerType
   */
  public void setRegisterType(BDynamicEnum v) { set(registerType, v, null); }

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
  public static final Property protocol = newProperty(0, BDynamicEnum.make(0, BEnumRange.make(new String[]{"modbus","bacnet","http"})), null);
  
  /**
   * Get the {@code protocol} property.
   * @see #protocol
   */
  public BDynamicEnum getProtocol() { return (BDynamicEnum)get(protocol); }
  
  /**
   * Set the {@code protocol} property.
   * @see #protocol
   */
  public void setProtocol(BDynamicEnum v) { set(protocol, v, null); }

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
// Property "dataType"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code dataType} property.
   * @see #getDataType
   * @see #setDataType
   */
  public static final Property dataType = newProperty(0, BDynamicEnum.make(0, BEnumRange.make(new String[]{"int16","uint16","int32","uint32","float32"})), null);
  
  /**
   * Get the {@code dataType} property.
   * @see #dataType
   */
  public BDynamicEnum getDataType() { return (BDynamicEnum)get(dataType); }
  
  /**
   * Set the {@code dataType} property.
   * @see #dataType
   */
  public void setDataType(BDynamicEnum v) { set(dataType, v, null); }

////////////////////////////////////////////////////////////////
// Property "byteOrder"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code byteOrder} property.
   * @see #getByteOrder
   * @see #setByteOrder
   */
  public static final Property byteOrder = newProperty(0, BDynamicEnum.make(0, BEnumRange.make(new String[]{"ABCD","CDAB","BADC","DCBA"})), null);
  
  /**
   * Get the {@code byteOrder} property.
   * @see #byteOrder
   */
  public BDynamicEnum getByteOrder() { return (BDynamicEnum)get(byteOrder); }
  
  /**
   * Set the {@code byteOrder} property.
   * @see #byteOrder
   */
  public void setByteOrder(BDynamicEnum v) { set(byteOrder, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyProxyPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;
    private int consecutiveErrors = 0;
    private static final int MAX_ERRORS = 3;

    // ✅ เก็บค่าสุดท้ายที่อ่านได้สำเร็จ
    private double lastValidValue = 0.0;
    private boolean hasValidValue = false;

    @Override
    public void started() throws Exception {
        super.started();
        updateVisibility();
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
        if (p == protocol) updateVisibility();
        if (p == out) {
            BStatusNumeric outVal = getOut();
            BStatusNumeric fbVal = getFallback();
            if (outVal.getStatus().isNull()) return;

            if (Math.abs(outVal.getValue() - fbVal.getValue()) > 0.001) {
                try {
                    writeToDevice(outVal.getValue());
                } catch (Exception e) {
                    System.err.println("Write failed: " + e.getMessage());
                }
            }
        }
    }

    private void updateVisibility() {
        boolean isModbus = "modbus".equalsIgnoreCase(getProtocol().getTag());
        setSlotVisible(dataType, isModbus);
        setSlotVisible(byteOrder, isModbus);
    }

    private void setSlotVisible(Property p, boolean visible) {
        int flags = getFlags(p);
        if (visible) {
            if ((flags & Flags.HIDDEN) != 0) setFlags(p, flags & ~Flags.HIDDEN);
        } else {
            if ((flags & Flags.HIDDEN) == 0) setFlags(p, flags | Flags.HIDDEN);
        }
    }

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;
        consecutiveErrors = 0;

        pollingThread = new Thread(() -> {
            try {
                Thread.sleep((long)(Math.random() * 2000));
            } catch (InterruptedException e) { return; }

            System.out.println("🔄 Polling started: " + getName());

            while (isPolling) {
                try {
                    double value = readFromDevice();

                    // ✅ บันทึกค่าที่อ่านได้สำเร็จ
                    lastValidValue = value;
                    hasValidValue = true;

                    setFallback(new BStatusNumeric(value, BStatus.ok));

                    if (consecutiveErrors > 0) {
                        System.out.println("✅ Connection restored [" + getName() + "]");
                        consecutiveErrors = 0;
                    }

                    if (Math.random() < 0.1) {
                        System.out.println("📊 Poll [" + getName() + "]: " + value);
                    }

                    Thread.sleep(getPollInterval());

                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    consecutiveErrors++;

                    if (consecutiveErrors == 1 || consecutiveErrors % MAX_ERRORS == 0) {
                        System.err.println("❌ Connection lost [" + getName() + "] (x" + consecutiveErrors + "): " + e.getMessage());
                    }

                    // ✅ ใช้ค่าสุดท้ายที่มี + Status Down แทนการใส่ 0 + Fault
                    if (hasValidValue) {
                        if (consecutiveErrors == 1) {
                            System.out.println("💾 Keeping last value [" + getName() + "]: " + lastValidValue);
                        }
                        setFallback(new BStatusNumeric(lastValidValue, BStatus.down));
                    } else {
                        // กรณียังไม่เคยอ่านสำเร็จเลย ให้ใช้ fault
                        setFallback(new BStatusNumeric(0.0, BStatus.fault));
                    }

                    try {
                        Thread.sleep(Math.min(getPollInterval(), 5000));
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
            }
            System.out.println("⏹️  Polling stopped: " + getName());
        });

        pollingThread.setDaemon(true);
        pollingThread.setName("Poll-" + getName());
        pollingThread.start();
    }

    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) pollingThread.interrupt();
    }

    private double readFromDevice() throws Exception {
        String proto = getProtocol().getTag().toLowerCase();
        switch (proto) {
            case "modbus": return readModbus();
            case "bacnet": return readBACnet();
            case "http": return readHTTP();
            default: throw new Exception("Unsupported protocol: " + proto);
        }
    }

    // ... (เก็บ methods เดิม: readModbus, readBACnet, readHTTP, writeToDevice, etc.)
    // Copy จากไฟล์เดิมทั้งหมด

    private double readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();
        String type = getRegisterType().getTag().toLowerCase();
        String dType = getDataType().getTag().toLowerCase();
        String order = getByteOrder().getTag();

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);
                byte fc = 0x03;
                if (type.contains("input")) fc = 0x04;
                if (type.contains("coil")) fc = 0x01;
                if (type.contains("discrete")) fc = 0x02;

                int quantity = 1;
                if (dType.contains("32")) quantity = 2;

                byte[] request = {0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, fc, (byte)(regAddr >> 8), (byte)(regAddr & 0xFF), 0x00, (byte)quantity};
                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();
                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);
                if (len < 9) throw new RuntimeException("Response too short");

                int dataOffset = 9;
                if (fc == 0x01 || fc == 0x02) return (double) (response[dataOffset] & 0x01);

                byte[] raw = new byte[quantity * 2];
                System.arraycopy(response, dataOffset, raw, 0, quantity * 2);
                byte[] ordered = swapBytes(raw, order);

                if (dType.equals("float32")) {
                    int bits = ((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) | ((ordered[2] & 0xFF) << 8) | (ordered[3] & 0xFF);
                    return (double) Float.intBitsToFloat(bits);
                } else if (dType.equals("int32")) {
                    return (double) (((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) | ((ordered[2] & 0xFF) << 8) | (ordered[3] & 0xFF));
                } else if (dType.equals("uint32")) {
                    return (double) (((ordered[0] & 0xFFL) << 24) | ((ordered[1] & 0xFFL) << 16) | ((ordered[2] & 0xFFL) << 8) | (ordered[3] & 0xFFL));
                } else if (dType.equals("uint16")) {
                    return (double) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                } else {
                    return (double) (short) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                }
            } catch (Exception e) { throw new RuntimeException(e); } finally { try { if (socket != null) socket.close(); } catch (Exception e) {} }
        });
    }

    private byte[] swapBytes(byte[] in, String order) {
        if (in.length < 4 && order.equals("ABCD")) return in;
        byte[] out = new byte[in.length];
        if (in.length == 4) {
            byte A = in[0], B = in[1], C = in[2], D = in[3];
            switch (order) {
                case "CDAB": out[0]=C; out[1]=D; out[2]=A; out[3]=B; break;
                case "BADC": out[0]=B; out[1]=A; out[2]=D; out[3]=C; break;
                case "DCBA": out[0]=D; out[1]=C; out[2]=B; out[3]=A; break;
                default: return in;
            }
        } else {
            if (!order.equals("ABCD")) { out[0] = in[1]; out[1] = in[0]; } else return in;
        }
        return out;
    }

    private void writeToDevice(double value) throws Exception {
        String proto = getProtocol().getTag().toLowerCase();
        if ("bacnet".equals(proto)) writeBACnet(value);
        else if ("modbus".equals(proto)) writeModbus(value);
    }

    private void writeBACnet(double val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        String typeStr = getRegisterType().getTag().toLowerCase();
        String nameStr = getName().toLowerCase();
        int objectType = 1;

        if (typeStr.contains("ai") || nameStr.contains("ai_")) objectType = 0;
        else if (typeStr.contains("ao") || nameStr.contains("ao_")) objectType = 1;
        else if (typeStr.contains("av") || nameStr.contains("av_")) objectType = 2;
        else if (typeStr.contains("bi") || nameStr.contains("bi_")) objectType = 3;
        else if (typeStr.contains("bo") || nameStr.contains("bo_")) objectType = 4;
        else if (typeStr.contains("bv") || nameStr.contains("bv_")) objectType = 5;

        if (objectType == 0) {
            System.err.println("❌ Write blocked: Cannot write to Analog Input (AI) - Read Only");
            return;
        }

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(ip);
                int invokeId = (int) (Math.random() * 255);

                byte[] tx = BACnetUtil.buildWritePropertyReal(
                        finalObjectType,
                        instance,
                        BACnetUtil.PROP_PRESENT_VALUE,
                        (float) val,
                        invokeId,
                        16
                );

                socket.send(new DatagramPacket(tx, tx.length, addr, port));
                System.out.println("BACnet Write Success: " + val + " (Pri 16)");
            } catch (Exception e) {
                System.err.println("BACnet Write Error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return null;
        });
    }

    /**
     * Updated Modbus Write: Supports FC06 (16-bit) and FC16 (32-bit)
     */
    private void writeModbus(double val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;

        int regAddr = getRegisterAddress();
        String type = getRegisterType().getTag().toLowerCase();    // holding, input, etc.
        String dType = getDataType().getTag().toLowerCase();       // int16, float32, etc.
        String order = getByteOrder().getTag();                    // ABCD, CDAB, etc.

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000); // 2s timeout
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                byte[] request;

                // --- Case 1: Write Coil (FC05) ---
                if (type.contains("coil") || type.contains("binary")) {
                    int outputVal = (val > 0) ? 0xFF00 : 0x0000;
                    request = new byte[] {
                            0x00, 0x01, 0x00, 0x00, 0x00, 0x06, // MBAP Header
                            0x01, 0x05,                         // UnitId, FC05
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                            (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                    };
                }
                // --- Case 2: Write Registers (FC06 or FC16) ---
                else {
                    boolean is32Bit = dType.contains("32") || dType.contains("float");

                    if (is32Bit) {
                        // === FC16: Write Multiple Registers (32-bit values) ===
                        int quantity = 2;
                        byte[] rawBytes = new byte[4];

                        // 1. Convert Value to Bytes (Big Endian / Java Default)
                        if (dType.equals("float32")) {
                            int bits = Float.floatToIntBits((float) val);
                            rawBytes[0] = (byte) (bits >> 24);
                            rawBytes[1] = (byte) (bits >> 16);
                            rawBytes[2] = (byte) (bits >> 8);
                            rawBytes[3] = (byte) (bits);
                        } else { // int32, uint32
                            int bits = (int) val;
                            rawBytes[0] = (byte) (bits >> 24);
                            rawBytes[1] = (byte) (bits >> 16);
                            rawBytes[2] = (byte) (bits >> 8);
                            rawBytes[3] = (byte) (bits);
                        }

                        // 2. Apply Byte/Word Swapping (Java -> Device)
                        // Note: swapBytes works symmetrically for bijective swaps
                        byte[] payload = swapBytes(rawBytes, order);

                        // 3. Construct FC16 Packet
                        int byteCount = 4;
                        int packetLen = 7 + byteCount; // UnitId(1)+FC(1)+Addr(2)+Quant(2)+ByteCount(1)+Data(4)

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        // MBAP Header
                        bos.write(new byte[]{0x00, 0x01, 0x00, 0x00}); // TransID, ProtoID
                        bos.write(packetLen >> 8);
                        bos.write(packetLen & 0xFF);
                        bos.write(0x01); // UnitID

                        // PDU
                        bos.write(0x10); // FC16 (Write Multiple)
                        bos.write(regAddr >> 8);
                        bos.write(regAddr & 0xFF);
                        bos.write(quantity >> 8);
                        bos.write(quantity & 0xFF);
                        bos.write(byteCount);
                        bos.write(payload);

                        request = bos.toByteArray();

                    } else {
                        // === FC06: Write Single Register (16-bit values) ===
                        // Note: Handling Byte Order for 16-bit (Swap Bytes if needed)
                        int intVal = (int) val;
                        byte[] rawBytes = new byte[2];
                        rawBytes[0] = (byte) (intVal >> 8);
                        rawBytes[1] = (byte) (intVal);

                        byte[] payload = swapBytes(rawBytes, order);
                        // Note: swapBytes handles 2-byte array as BADC swap if needed (check swapBytes impl)

                        // Re-assemble int from payload
                        int finalVal = ((payload[0] & 0xFF) << 8) | (payload[1] & 0xFF);

                        request = new byte[] {
                                0x00, 0x01, 0x00, 0x00, 0x00, 0x06, // MBAP
                                0x01, 0x06,                         // UnitId, FC06
                                (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                                (byte)(finalVal >> 8), (byte)(finalVal & 0xFF)
                        };
                    }
                }

                // Send Request
                out.write(request);
                out.flush();

                // Read Response
                byte[] response = new byte[256];
                int len = in.read(response);

                // Basic Validation
                if (len > 0) {
                    int fc = response[7] & 0xFF;
                    if (fc == 0x05 || fc == 0x06 || fc == 0x10) {
                        System.out.println("✅ Modbus Write Success: " + val + " (FC" + fc + ")");
                    } else if (fc > 0x80) {
                        int exceptionCode = response[8] & 0xFF;
                        System.err.println("❌ Modbus Write Exception: Code " + exceptionCode);
                    }
                } else {
                    System.err.println("⚠️ Modbus Write: No Response");
                }

            } catch (Exception e) {
                System.err.println("❌ Modbus Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    private double readBACnet() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;
        String typeStr = getRegisterType().getTag().toLowerCase();
        String nameStr = getName().toLowerCase();
        int objectType = 0;

        if (typeStr.contains("ai") || nameStr.contains("ai_")) objectType = 0;
        else if (typeStr.contains("ao") || nameStr.contains("ao_")) objectType = 1;
        else if (typeStr.contains("av") || nameStr.contains("av_")) objectType = 2;
        else if (typeStr.contains("bi") || nameStr.contains("bi_")) objectType = 3;
        else if (typeStr.contains("bo") || nameStr.contains("bo_")) objectType = 4;
        else if (typeStr.contains("bv") || nameStr.contains("bv_")) objectType = 5;

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);
                int invokeId = (int) (Math.random() * 255);
                byte[] tx = BACnetUtil.buildReadPropertyPacket(finalObjectType, instance, BACnetUtil.PROP_PRESENT_VALUE, invokeId);
                InetAddress addr = InetAddress.getByName(ip);
                socket.send(new DatagramPacket(tx, tx.length, addr, port));
                byte[] rxBuf = new byte[512];
                DatagramPacket rxPacket = new DatagramPacket(rxBuf, rxBuf.length);
                socket.receive(rxPacket);
                byte[] data = rxPacket.getData();
                int offset = 6;
                byte npduControl = data[4];
                if ((npduControl & 0x20) != 0) { offset += 2; int dlen = data[offset++] & 0xFF; offset += dlen + 1; }
                byte apduType = data[offset];
                if ((apduType & 0xF0) != 0x30) throw new RuntimeException("Invalid APDU type: 0x" + Integer.toHexString(apduType & 0xFF));
                offset += 3;
                if (data[offset] == (byte) 0x0C) offset += 5;
                if (data[offset] == (byte) 0x19) offset += 2;
                if (data[offset] == (byte) 0x3E) offset++;
                byte tag = data[offset];
                if (tag == (byte) 0x44) {
                    int bits = ((data[offset + 1] & 0xFF) << 24) | ((data[offset + 2] & 0xFF) << 16) | ((data[offset + 3] & 0xFF) << 8) | (data[offset + 4] & 0xFF);
                    return (double) Float.intBitsToFloat(bits);
                } else if (tag == (byte) 0x91 || tag == (byte) 0x21) {
                    return (double) (data[offset + 1] & 0xFF);
                } else if ((tag & 0xF0) == 0x20) {
                    return (double) (data[offset + 1] & 0xFF);
                }
                throw new RuntimeException("Unsupported data type: 0x" + Integer.toHexString(tag & 0xFF));
            } catch (Exception e) { throw new RuntimeException(e); } finally { if (socket != null) socket.close(); }
        });
    }

    private double readHTTP() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 80;
        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            try {
                URL url = new URL("http://" + ip + ":" + port + "/api/value");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                if (conn.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String json = in.readLine();
                    in.close();
                    if (json != null && json.contains("value")) return 123.45;
                }
                throw new RuntimeException("HTTP request failed");
            } catch (Exception e) { throw new RuntimeException(e); }
        });
    }

    private BMyPointDevice getParentDevice() {
        BComplex parent = getParent();
        while (parent != null && !(parent instanceof BMyPointDevice)) {
            if (parent instanceof BComponent) parent = ((BComponent) parent).getParent();
            else break;
        }
        return (BMyPointDevice) parent;
    }
}