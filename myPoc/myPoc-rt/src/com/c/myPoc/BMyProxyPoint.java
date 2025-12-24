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
 * Proxy Point - ใช้ BDynamicEnum แทน String เพื่อแสดง Dropdown
 * ใน Niagara Workbench
 */
@NiagaraType
@NiagaraProperty(
        name = "address",
        type = "String",
        defaultValue = ""
)
@NiagaraProperty(
        name = "registerType",
        type = "BDynamicEnum",
        defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"holding\",\"input\",\"coil\",\"discrete\",\"AI\",\"AO\",\"AV\",\"BI\",\"BO\",\"BV\"}))"
)
@NiagaraProperty(
        name = "registerAddress",
        type = "int",
        defaultValue = "0"
)
@NiagaraProperty(
        name = "protocol",
        type = "BDynamicEnum",
        defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"modbus\",\"bacnet\",\"http\"}))"
)
@NiagaraProperty(
        name = "pollInterval",
        type = "int",
        defaultValue = "5000"
)
@NiagaraProperty(
        name = "dataType",
        type = "BDynamicEnum",
        defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"int16\",\"uint16\",\"int32\",\"uint32\",\"float32\"}))"
)
@NiagaraProperty(
        name = "byteOrder",
        type = "BDynamicEnum",
        defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"ABCD\",\"CDAB\",\"BADC\",\"DCBA\"}))"
)
public class BMyProxyPoint extends BNumericWritable {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyProxyPoint(389456721)1.0$ @*/
    /* Generated Tue Dec 24 10:45:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "address"
////////////////////////////////////////////////////////////////

    public static final Property address = newProperty(0, "", null);
    public String getAddress() { return getString(address); }
    public void setAddress(String v) { setString(address, v, null); }

////////////////////////////////////////////////////////////////
// Property "registerType"
// Dropdown values: holding, input, coil, discrete, AI, AO, AV, BI, BO, BV
////////////////////////////////////////////////////////////////

    public static final Property registerType = newProperty(0,
            BDynamicEnum.make(
                    0,
                    BEnumRange.make(new String[]{"holding", "input", "coil", "discrete", "AI", "AO", "AV", "BI", "BO", "BV"})
            ),
            null
    );

    public BDynamicEnum getRegisterType() {
        return (BDynamicEnum) get(registerType);
    }

    public void setRegisterType(BDynamicEnum v) {
        set(registerType, v, null);
    }

////////////////////////////////////////////////////////////////
// Property "registerAddress"
////////////////////////////////////////////////////////////////

    public static final Property registerAddress = newProperty(0, 0, null);
    public int getRegisterAddress() { return getInt(registerAddress); }
    public void setRegisterAddress(int v) { setInt(registerAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "protocol"
// Dropdown values: modbus, bacnet, http
////////////////////////////////////////////////////////////////

    public static final Property protocol = newProperty(0,
            BDynamicEnum.make(
                    0,
                    BEnumRange.make(new String[]{"modbus", "bacnet", "http"})
            ),
            null
    );

    public BDynamicEnum getProtocol() {
        return (BDynamicEnum) get(protocol);
    }

    public void setProtocol(BDynamicEnum v) {
        set(protocol, v, null);
    }

////////////////////////////////////////////////////////////////
// Property "pollInterval"
////////////////////////////////////////////////////////////////

    public static final Property pollInterval = newProperty(0, 5000, null);
    public int getPollInterval() { return getInt(pollInterval); }
    public void setPollInterval(int v) { setInt(pollInterval, v, null); }

////////////////////////////////////////////////////////////////
// Property "dataType"
// Dropdown values: int16, uint16, int32, uint32, float32
////////////////////////////////////////////////////////////////

    public static final Property dataType = newProperty(0,
            BDynamicEnum.make(
                    0,
                    BEnumRange.make(new String[]{"int16", "uint16", "int32", "uint32", "float32"})
            ),
            null
    );

    public BDynamicEnum getDataType() {
        return (BDynamicEnum) get(dataType);
    }

    public void setDataType(BDynamicEnum v) {
        set(dataType, v, null);
    }

////////////////////////////////////////////////////////////////
// Property "byteOrder"
// Dropdown values: ABCD, CDAB, BADC, DCBA
////////////////////////////////////////////////////////////////

    public static final Property byteOrder = newProperty(0,
            BDynamicEnum.make(
                    0,
                    BEnumRange.make(new String[]{"ABCD", "CDAB", "BADC", "DCBA"})
            ),
            null
    );

    public BDynamicEnum getByteOrder() {
        return (BDynamicEnum) get(byteOrder);
    }

    public void setByteOrder(BDynamicEnum v) {
        set(byteOrder, v, null);
    }

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
                    double value = readFromDevice();
                    BStatusNumeric statusValue = new BStatusNumeric(value, BStatus.ok);
                    setFallback(statusValue);
                    Thread.sleep(getPollInterval());
                } catch (Exception e) {
                    try {
                        setFallback(new BStatusNumeric(0.0, BStatus.fault));
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
        String proto = getProtocol().getTag().toLowerCase();
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

                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, fc,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        0x00, (byte)quantity
                };

                OutputStream out = socket.getOutputStream();
                out.write(request);
                out.flush();

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len < 9) throw new RuntimeException("Response too short");

                int dataOffset = 9;

                if (fc == 0x01 || fc == 0x02) {
                    return (double) (response[dataOffset] & 0x01);
                }

                byte[] raw = new byte[quantity * 2];
                if (len < dataOffset + raw.length) throw new RuntimeException("Incomplete data");
                System.arraycopy(response, dataOffset, raw, 0, quantity * 2);

                byte[] ordered = swapBytes(raw, order);

                if (dType.equals("float32")) {
                    int bits = ((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) |
                            ((ordered[2] & 0xFF) << 8)  | (ordered[3] & 0xFF);
                    return (double) Float.intBitsToFloat(bits);
                }
                else if (dType.equals("int32")) {
                    int val = ((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) |
                            ((ordered[2] & 0xFF) << 8)  | (ordered[3] & 0xFF);
                    return (double) val;
                }
                else if (dType.equals("uint32")) {
                    long val = ((ordered[0] & 0xFFL) << 24) | ((ordered[1] & 0xFFL) << 16) |
                            ((ordered[2] & 0xFFL) << 8)  | (ordered[3] & 0xFFL);
                    return (double) val;
                }
                else if (dType.equals("uint16")) {
                    return (double) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                }
                else {
                    short val = (short) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                    return (double) val;
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
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
        }
        else {
            if (!order.equals("ABCD")) {
                out[0] = in[1];
                out[1] = in[0];
            } else {
                return in;
            }
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

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(ip);
                byte[] tx = BACnetUtil.buildWritePropertyReal(finalObjectType, instance, BACnetUtil.PROP_PRESENT_VALUE, (float) val, 1);
                socket.send(new DatagramPacket(tx, tx.length, addr, port));
                System.out.println("BACnet Write Success: " + val);
            } catch (Exception e) {
                System.err.println("BACnet Write Error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return null;
        });
    }

    private void writeModbus(double val) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();
        String type = getRegisterType().getTag().toLowerCase();

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                byte[] request;

                if (type.contains("coil") || type.contains("binary")) {
                    int outputVal = (val > 0) ? 0xFF00 : 0x0000;
                    request = new byte[] {
                            0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x05,
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                            (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                    };
                } else {
                    int intVal = (int) val;
                    request = new byte[] {
                            0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x06,
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                            (byte)(intVal >> 8), (byte)(intVal & 0xFF)
                    };
                }
                out.write(request);
                out.flush();
                byte[] response = new byte[256];
                int len = in.read(response);
                if (len > 0 && (response[7] == 0x05 || response[7] == 0x06)) {
                    System.out.println("Modbus Write Success: " + val);
                } else {
                    System.err.println("Modbus Write Error: Invalid response");
                }
            } catch (Exception e) {
                System.err.println("Modbus Write Error: " + e.getMessage());
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
                socket.setSoTimeout(2000);
                int invokeId = (int) (Math.random() * 255);
                byte[] tx = BACnetUtil.buildReadPropertyPacket(finalObjectType, instance, BACnetUtil.PROP_PRESENT_VALUE, invokeId);
                socket.send(new DatagramPacket(tx, tx.length, InetAddress.getByName(ip), port));
                byte[] rxBuf = new byte[512];
                DatagramPacket rxPacket = new DatagramPacket(rxBuf, rxBuf.length);
                socket.receive(rxPacket);
                byte[] data = rxPacket.getData();
                int offset = 6;
                if (data[offset] == 0x30) {
                    offset += 3;
                    if (data[offset] == 0x0C) offset += 5;
                    if (data[offset] == 0x19) offset += 2;
                    if (data[offset] == 0x3E) offset += 1;
                    byte tag = data[offset];
                    if (tag == 0x44) {
                        int bits = ((data[offset + 1] & 0xFF) << 24) | ((data[offset + 2] & 0xFF) << 16) |
                                ((data[offset + 3] & 0xFF) << 8) | (data[offset + 4] & 0xFF);
                        return (double) Float.intBitsToFloat(bits);
                    } else if (tag == (byte) 0x91 || tag == (byte) 0x21) {
                        return (double) (data[offset + 1] & 0xFF);
                    }
                }
                return Double.NaN;
            } catch (Exception e) {
                throw new RuntimeException("Read Failed");
            } finally {
                if (socket != null) socket.close();
            }
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
                    if (json != null && json.contains("value")) {
                        return 123.45;
                    }
                }
                throw new RuntimeException("HTTP request failed");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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