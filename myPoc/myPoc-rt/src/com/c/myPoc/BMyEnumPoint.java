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
 * Enum Point for reading Mode/Status values
 * Supports: Modbus (Int16 mapped to Enum), BACnet
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
// DataType/ByteOrder อาจไม่จำเป็นมากสำหรับ Enum (เพราะมักเป็น Int16) แต่เก็บไว้ก็ได้
@NiagaraProperty(name = "dataType", type = "String", defaultValue = "int16")
@NiagaraProperty(name = "byteOrder", type = "String", defaultValue = "ABCD")
public class BMyEnumPoint extends BEnumWritable {


    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyEnumPoint(3944209486)1.0$ @*/
    /* Generated Tue Dec 23 15:19:13 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "address"
////////////////////////////////////////////////////////////////

    public static final Property address = newProperty(0, "", null);
    public String getAddress() { return getString(address); }
    public void setAddress(String v) { setString(address, v, null); }

////////////////////////////////////////////////////////////////
// Property "registerType"
////////////////////////////////////////////////////////////////

    public static final Property registerType = newProperty(0, "", null);
    public String getRegisterType() { return getString(registerType); }
    public void setRegisterType(String v) { setString(registerType, v, null); }

////////////////////////////////////////////////////////////////
// Property "registerAddress"
////////////////////////////////////////////////////////////////

    public static final Property registerAddress = newProperty(0, 0, null);
    public int getRegisterAddress() { return getInt(registerAddress); }
    public void setRegisterAddress(int v) { setInt(registerAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "protocol"
////////////////////////////////////////////////////////////////

    public static final Property protocol = newProperty(0, "modbus", null);
    public String getProtocol() { return getString(protocol); }
    public void setProtocol(String v) { setString(protocol, v, null); }

////////////////////////////////////////////////////////////////
// Property "pollInterval"
////////////////////////////////////////////////////////////////

    public static final Property pollInterval = newProperty(0, 5000, null);
    public int getPollInterval() { return getInt(pollInterval); }
    public void setPollInterval(int v) { setInt(pollInterval, v, null); }

////////////////////////////////////////////////////////////////
// Property "dataType"
////////////////////////////////////////////////////////////////

    public static final Property dataType = newProperty(0, "int16", null);
    public String getDataType() { return getString(dataType); }
    public void setDataType(String v) { setString(dataType, v, null); }

////////////////////////////////////////////////////////////////
// Property "byteOrder"
////////////////////////////////////////////////////////////////

    public static final Property byteOrder = newProperty(0, "ABCD", null);
    public String getByteOrder() { return getString(byteOrder); }
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

        // 2. Logic การเขียนค่า (Write) สำหรับ Enum
        if (p == out) {
            // ✅ แก้ไข: ใช้ BStatusEnum แทน BStatusNumeric
            BStatusEnum outVal = getOut();
            BStatusEnum fbVal = getFallback();

            if (outVal.getStatus().isNull()) return;

            // เทียบค่าด้วย Ordinal (ลำดับตัวเลข 0, 1, 2)
            int outOrd = outVal.getValue().getOrdinal();
            int fbOrd = -1;

            if (!fbVal.getStatus().isNull()) {
                fbOrd = fbVal.getValue().getOrdinal();
            }

            // ถ้าค่าไม่ตรงกัน ให้เขียน
            if (outOrd != fbOrd) {
                try {
                    // ส่งค่า Ordinal (int) ไปเขียน
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
        pollingThread = new Thread(() -> {
            while (isPolling) {
                try {
                    // อ่านค่ามาเป็นตัวเลข
                    double value = readFromDevice();
                    int intValue = (int) value;

                    // หา Enum จาก Facets
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
                        // ✅ กรณีปกติ: ใส่ค่าจริง + Status OK
                        setFallback(new BStatusEnum(enumVal, BStatus.ok));
                    } else {
                        // ❌ กรณีหาไม่เจอ: ใส่ค่า Default + Status Fault
                        setFallback(new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.fault));
                    }

                    Thread.sleep(getPollInterval());
                } catch (Exception e) {
                    try {
                        // ❌ กรณี Error: ใส่ค่า Default + Status Fault
                        setFallback(new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.fault));
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

        // Enum มักใช้ Int16 เสมอ

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                // Read Holding (03) or Input (04)
                byte fc = 0x03;
                if (getRegisterType().toLowerCase().contains("input")) fc = 0x04;

                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, fc,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        0x00, 0x01 // Quantity 1
                };

                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len < 9) throw new RuntimeException("Response too short");

                // Parse Int16 (Big Endian Default)
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
        // ... โค้ดเดิม (อาจจะต้องแก้ buildWritePropertyReal เป็น buildWritePropertyEnumerated ถ้าทำได้ แต่ใช้ Real ไปก่อนได้ในบางเคส)
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        // ... (Logic BACnet เดิม)
        // AccessController...
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

                // Write Single Register (06)
                byte[] request = new byte[] {
                        0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x06,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(val >> 8), (byte)(val & 0xFF)
                };

                out.write(request);
                out.flush();
                // ... read response ...
            } catch (Exception e) {
                System.err.println("Modbus Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    // ... readBACnet / readHTTP เดิม ...
    private double readBACnet() throws Exception {
        // ใช้ Logic เดิมได้เลย เพราะมัน return double แล้วเรามา cast เป็น int ทีหลัง
        return 0.0; // ใส่ Code เดิมของคุณตรงนี้
    }

    private double readHTTP() throws Exception {
        return 0.0; // ใส่ Code เดิมของคุณตรงนี้
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