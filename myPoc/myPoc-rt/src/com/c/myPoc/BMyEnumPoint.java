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
 * Enum Point with Last Value Retention
 * ✅ Keep last value on disconnect
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "dataType", type = "String", defaultValue = "int16")
@NiagaraProperty(name = "byteOrder", type = "String", defaultValue = "ABCD")
public class BMyEnumPoint extends BEnumWritable {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyEnumPoint(3944209486)1.0$ @*/
/* Generated Mon Dec 29 17:57:16 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
  public static final Property registerType = newProperty(0, "", null);
  
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
// Property "dataType"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code dataType} property.
   * @see #getDataType
   * @see #setDataType
   */
  public static final Property dataType = newProperty(0, "int16", null);
  
  /**
   * Get the {@code dataType} property.
   * @see #dataType
   */
  public String getDataType() { return getString(dataType); }
  
  /**
   * Set the {@code dataType} property.
   * @see #dataType
   */
  public void setDataType(String v) { setString(dataType, v, null); }

////////////////////////////////////////////////////////////////
// Property "byteOrder"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code byteOrder} property.
   * @see #getByteOrder
   * @see #setByteOrder
   */
  public static final Property byteOrder = newProperty(0, "ABCD", null);
  
  /**
   * Get the {@code byteOrder} property.
   * @see #byteOrder
   */
  public String getByteOrder() { return getString(byteOrder); }
  
  /**
   * Set the {@code byteOrder} property.
   * @see #byteOrder
   */
  public void setByteOrder(String v) { setString(byteOrder, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyEnumPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    // ✅ เก็บค่าสุดท้ายที่อ่านได้สำเร็จ
    private BEnum lastValidEnum = null;
    private boolean hasValidValue = false;
    private int consecutiveErrors = 0;
    private static final int MAX_ERRORS = 3;

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

        if (p == protocol) {
            updateVisibility();
        }

        if (p == out) {
            BStatusEnum outVal = getOut();
            BStatusEnum fbVal = getFallback();

            if (outVal.getStatus().isNull()) return;

            int outOrd = outVal.getValue().getOrdinal();
            int fbOrd = -1;

            if (!fbVal.getStatus().isNull()) {
                fbOrd = fbVal.getValue().getOrdinal();
            }

            if (outOrd != fbOrd) {
                try {
                    writeToDevice(outOrd);
                } catch (Exception e) {
                    System.err.println("Write failed: " + e.getMessage());
                }
            }
        }
    }

    private void updateVisibility() {
        boolean isModbus = "modbus".equalsIgnoreCase(getProtocol());
        setSlotVisible(dataType, isModbus);
        setSlotVisible(byteOrder, isModbus);
    }

    private void setSlotVisible(Property p, boolean visible) {
        int flags = getFlags(p);
        if (visible) {
            if ((flags & Flags.HIDDEN) != 0) {
                setFlags(p, flags & ~Flags.HIDDEN);
            }
        } else {
            if ((flags & Flags.HIDDEN) == 0) {
                setFlags(p, flags | Flags.HIDDEN);
            }
        }
    }

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;
        consecutiveErrors = 0;

        pollingThread = new Thread(() -> {
            while (isPolling) {
                try {
                    double value = readFromDevice();
                    int intValue = (int) value;

                    BFacets facets = getFacets();
                    BEnumRange range = (BEnumRange) facets.get(BFacets.RANGE);

                    BEnum enumVal;
                    if (range != null) {
                        try {
                            enumVal = range.get(intValue);
                        } catch (Exception e) {
                            enumVal = null;
                        }
                    } else {
                        enumVal = null;
                    }

                    if (enumVal != null) {
                        // ✅ บันทึกค่าที่อ่านได้สำเร็จ
                        lastValidEnum = enumVal;
                        hasValidValue = true;

                        setFallback(new BStatusEnum(enumVal, BStatus.ok));

                        if (consecutiveErrors > 0) {
                            System.out.println("✅ Connection restored [" + getName() + "]");
                            consecutiveErrors = 0;
                        }
                    } else {
                        // ค่าที่อ่านได้ไม่อยู่ใน Enum Range
                        if (hasValidValue) {
                            setFallback(new BStatusEnum(lastValidEnum, BStatus.down));
                        } else {
                            setFallback(new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.fault));
                        }
                    }

                    Thread.sleep(getPollInterval());

                } catch (Exception e) {
                    consecutiveErrors++;

                    if (consecutiveErrors == 1 || consecutiveErrors % MAX_ERRORS == 0) {
                        System.err.println("❌ Connection lost [" + getName() + "] (x" + consecutiveErrors + "): " + e.getMessage());
                    }

                    // ✅ ใช้ค่าสุดท้าย + Status Down
                    if (hasValidValue && lastValidEnum != null) {
                        if (consecutiveErrors == 1) {
                            System.out.println("💾 Keeping last value [" + getName() + "]: " + lastValidEnum.getTag());
                        }
                        setFallback(new BStatusEnum(lastValidEnum, BStatus.down));
                    } else {
                        setFallback(new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.fault));
                    }

                    try {
                        Thread.sleep(getPollInterval());
                    } catch (Exception ie) {}
                }
            }
        });
        pollingThread.setDaemon(true);
        pollingThread.start();
    }

    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) pollingThread.interrupt();
    }

    private double readFromDevice() throws Exception {
        String proto = getProtocol().toLowerCase();
        switch (proto) {
            case "modbus": return readModbus();
            case "bacnet": return readBACnet();
            case "http": return readHTTP();
            default: throw new Exception("Unsupported protocol: " + proto);
        }
    }

    private double readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;

        int regAddr = getRegisterAddress();

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                byte fc = 0x03;
                if (getRegisterType().toLowerCase().contains("input")) fc = 0x04;

                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, fc,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        0x00, 0x01
                };

                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len < 9) throw new RuntimeException("Response too short");

                int val = ((response[9] & 0xFF) << 8) | (response[10] & 0xFF);
                return (double) val;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
        });
    }

    private void writeToDevice(int value) throws Exception {
        String proto = getProtocol().toLowerCase();
        if ("bacnet".equals(proto)) writeBACnet(value);
        else if ("modbus".equals(proto)) writeModbus(value);
    }

    private void writeBACnet(double val) throws Exception {
        // BACnet write implementation (if needed)
    }

    private void writeModbus(int val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);
                OutputStream out = socket.getOutputStream();

                byte[] request = new byte[] {
                        0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x06,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(val >> 8), (byte)(val & 0xFF)
                };

                out.write(request);
                out.flush();
            } catch (Exception e) {
                System.err.println("Modbus Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    private double readBACnet() throws Exception {
        return 0.0;
    }

    private double readHTTP() throws Exception {
        return 0.0;
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