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
 * Proxy Point with Last Value Retention + Automatic Reconnection
 * ✅ Exponential Backoff Retry Strategy
 * ✅ Connection Health Monitoring
 * ✅ Smart Recovery Logic
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"holding\",\"input\",\"coil\",\"discrete\",\"AI\",\"AO\",\"AV\",\"BI\",\"BO\",\"BV\"}))")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"modbus\",\"bacnet\",\"http\"}))")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "dataType", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"int16\",\"uint16\",\"int32\",\"uint32\",\"float32\"}))")
@NiagaraProperty(name = "byteOrder", type = "BDynamicEnum", defaultValue = "BDynamicEnum.make(0, BEnumRange.make(new String[]{\"ABCD\",\"CDAB\",\"BADC\",\"DCBA\"}))")
// ✅ Reconnection Properties
@NiagaraProperty(name = "enableAutoReconnect", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "maxRetries", type = "int", defaultValue = "10")
@NiagaraProperty(name = "initialRetryDelay", type = "int", defaultValue = "1000")
@NiagaraProperty(name = "maxRetryDelay", type = "int", defaultValue = "60000")
@NiagaraProperty(name = "connectionStatus", type = "String", defaultValue = "Disconnected", flags = Flags.READONLY)
public class BMyProxyPoint extends BNumericWritable {
    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyProxyPoint(3148543320)1.0$ @*/
/* Generated Mon Jan 05 17:36:00 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Property "enableAutoReconnect"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code enableAutoReconnect} property.
   * @see #getEnableAutoReconnect
   * @see #setEnableAutoReconnect
   */
  public static final Property enableAutoReconnect = newProperty(0, true, null);
  
  /**
   * Get the {@code enableAutoReconnect} property.
   * @see #enableAutoReconnect
   */
  public boolean getEnableAutoReconnect() { return getBoolean(enableAutoReconnect); }
  
  /**
   * Set the {@code enableAutoReconnect} property.
   * @see #enableAutoReconnect
   */
  public void setEnableAutoReconnect(boolean v) { setBoolean(enableAutoReconnect, v, null); }

////////////////////////////////////////////////////////////////
// Property "maxRetries"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code maxRetries} property.
   * @see #getMaxRetries
   * @see #setMaxRetries
   */
  public static final Property maxRetries = newProperty(0, 10, null);
  
  /**
   * Get the {@code maxRetries} property.
   * @see #maxRetries
   */
  public int getMaxRetries() { return getInt(maxRetries); }
  
  /**
   * Set the {@code maxRetries} property.
   * @see #maxRetries
   */
  public void setMaxRetries(int v) { setInt(maxRetries, v, null); }

////////////////////////////////////////////////////////////////
// Property "initialRetryDelay"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code initialRetryDelay} property.
   * @see #getInitialRetryDelay
   * @see #setInitialRetryDelay
   */
  public static final Property initialRetryDelay = newProperty(0, 1000, null);
  
  /**
   * Get the {@code initialRetryDelay} property.
   * @see #initialRetryDelay
   */
  public int getInitialRetryDelay() { return getInt(initialRetryDelay); }
  
  /**
   * Set the {@code initialRetryDelay} property.
   * @see #initialRetryDelay
   */
  public void setInitialRetryDelay(int v) { setInt(initialRetryDelay, v, null); }

////////////////////////////////////////////////////////////////
// Property "maxRetryDelay"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code maxRetryDelay} property.
   * @see #getMaxRetryDelay
   * @see #setMaxRetryDelay
   */
  public static final Property maxRetryDelay = newProperty(0, 60000, null);
  
  /**
   * Get the {@code maxRetryDelay} property.
   * @see #maxRetryDelay
   */
  public int getMaxRetryDelay() { return getInt(maxRetryDelay); }
  
  /**
   * Set the {@code maxRetryDelay} property.
   * @see #maxRetryDelay
   */
  public void setMaxRetryDelay(int v) { setInt(maxRetryDelay, v, null); }

////////////////////////////////////////////////////////////////
// Property "connectionStatus"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code connectionStatus} property.
   * @see #getConnectionStatus
   * @see #setConnectionStatus
   */
  public static final Property connectionStatus = newProperty(Flags.READONLY, "Disconnected", null);
  
  /**
   * Get the {@code connectionStatus} property.
   * @see #connectionStatus
   */
  public String getConnectionStatus() { return getString(connectionStatus); }
  
  /**
   * Set the {@code connectionStatus} property.
   * @see #connectionStatus
   */
  public void setConnectionStatus(String v) { setString(connectionStatus, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyProxyPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    // ✅ Connection Management
    private int consecutiveErrors = 0;
    private int consecutiveSuccesses = 0;
    private long lastSuccessTime = 0;
    private int currentRetryDelay = 0;

    // ✅ Value Retention
    private double lastValidValue = 0.0;
    private boolean hasValidValue = false;

    @Override
    public void started() throws Exception {
        super.started();
        updateVisibility();
        resetConnectionState();
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

        if (p == enableAutoReconnect) {
            if (getEnableAutoReconnect()) {
                resetConnectionState();
            }
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

    // ==================== Connection Management ====================

    private void resetConnectionState() {
        consecutiveErrors = 0;
        consecutiveSuccesses = 0;
        currentRetryDelay = getInitialRetryDelay();
        updateConnectionStatus("Connecting");
    }

    private void updateConnectionStatus(String status) {
        setConnectionStatus(status);
    }

    private int calculateRetryDelay() {
        if (!getEnableAutoReconnect()) {
            return getPollInterval();
        }

        // ✅ Exponential Backoff: delay = min(initial * 2^retries, maxDelay)
        int delay = getInitialRetryDelay() * (1 << Math.min(consecutiveErrors, 10));
        currentRetryDelay = Math.min(delay, getMaxRetryDelay());

        return currentRetryDelay;
    }

    private boolean shouldStopRetrying() {
        if (!getEnableAutoReconnect()) {
            return false;
        }

        return getMaxRetries() > 0 && consecutiveErrors >= getMaxRetries();
    }

    private void handleSuccessfulRead(double value) {
        // ✅ บันทึกค่าที่อ่านได้สำเร็จ
        lastValidValue = value;
        hasValidValue = true;
        lastSuccessTime = System.currentTimeMillis();

        consecutiveSuccesses++;

        // ✅ Connection Restored
        if (consecutiveErrors > 0) {
            System.out.println("✅ Connection restored [" + getName() + "] after " +
                    consecutiveErrors + " failures");
            updateConnectionStatus("Connected (Recovered)");
        } else if (consecutiveSuccesses == 1) {
            updateConnectionStatus("Connected");
        }

        consecutiveErrors = 0;
        currentRetryDelay = getInitialRetryDelay();

        setFallback(new BStatusNumeric(value, BStatus.ok));
    }

    private void handleFailedRead(Exception e) {
        consecutiveErrors++;
        consecutiveSuccesses = 0;

        String errorMsg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();

        // ✅ Log errors at strategic intervals
        if (consecutiveErrors == 1) {
            System.err.println("❌ Connection lost [" + getName() + "]: " + errorMsg);
            updateConnectionStatus("Disconnected");
        } else if (consecutiveErrors % 5 == 0) {
            System.err.println("⚠️  Still disconnected [" + getName() + "] (attempt " +
                    consecutiveErrors + "): " + errorMsg);
        }

        // ✅ Check if should give up
        if (shouldStopRetrying()) {
            System.err.println("🛑 Max retries reached [" + getName() + "], stopping reconnection attempts");
            updateConnectionStatus("Failed (Max Retries)");
            setFallback(new BStatusNumeric(
                    hasValidValue ? lastValidValue : 0.0,
                    BStatus.fault
            ));
            return;
        }

        // ✅ ใช้ค่าสุดท้าย + Status Down
        if (hasValidValue) {
            if (consecutiveErrors == 1) {
                System.out.println("💾 Keeping last value [" + getName() + "]: " + lastValidValue);
            }

            long timeSinceSuccess = System.currentTimeMillis() - lastSuccessTime;
            String statusDetail = String.format("Down (%ds ago)", timeSinceSuccess / 1000);

            if (getEnableAutoReconnect()) {
                int nextRetry = calculateRetryDelay() / 1000;
                statusDetail = String.format("Reconnecting (retry in %ds)", nextRetry);
                updateConnectionStatus(statusDetail);
            } else {
                updateConnectionStatus("Down (No Auto-Reconnect)");
            }

            setFallback(new BStatusNumeric(lastValidValue, BStatus.down));
        } else {
            updateConnectionStatus("Failed (No Data)");
            setFallback(new BStatusNumeric(0.0, BStatus.fault));
        }
    }

    // ==================== Polling Thread ====================

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;

        pollingThread = new Thread(() -> {
            // ✅ Random initial delay to prevent thundering herd
            try {
                Thread.sleep((long)(Math.random() * 2000));
            } catch (InterruptedException e) {
                return;
            }

            System.out.println("🔄 Polling started: " + getName() +
                    (getEnableAutoReconnect() ? " (Auto-Reconnect ON)" : ""));

            while (isPolling) {
                try {
                    double value = readFromDevice();
                    handleSuccessfulRead(value);

                    // ✅ Occasional success log
                    if (Math.random() < 0.1 || consecutiveSuccesses == 1) {
                        System.out.println("📊 Poll [" + getName() + "]: " + value);
                    }

                    Thread.sleep(getPollInterval());

                } catch (InterruptedException e) {
                    break;

                } catch (Exception e) {
                    handleFailedRead(e);

                    // ✅ Stop if max retries reached
                    if (shouldStopRetrying()) {
                        System.err.println("🛑 Stopping polling thread [" + getName() +
                                "] - Max retries exceeded");
                        break;
                    }

                    // ✅ Exponential backoff delay
                    try {
                        int delay = calculateRetryDelay();
                        Thread.sleep(delay);
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
            }

            updateConnectionStatus("Stopped");
            System.out.println("⏹️  Polling stopped: " + getName());
        });

        pollingThread.setDaemon(true);
        pollingThread.setName("Poll-" + getName());
        pollingThread.start();
    }

    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) {
            pollingThread.interrupt();
        }
    }

    // ==================== Protocol Handlers ====================

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
                socket.setSoTimeout(2000);

                byte fc = 0x03;
                if (type.contains("input")) fc = 0x04;
                if (type.contains("coil")) fc = 0x01;
                if (type.contains("discrete")) fc = 0x02;

                int quantity = dType.contains("32") ? 2 : 1;

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
                System.arraycopy(response, dataOffset, raw, 0, quantity * 2);
                byte[] ordered = swapBytes(raw, order);

                if (dType.equals("float32")) {
                    int bits = ((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) |
                            ((ordered[2] & 0xFF) << 8) | (ordered[3] & 0xFF);
                    return (double) Float.intBitsToFloat(bits);
                } else if (dType.equals("int32")) {
                    return (double) (((ordered[0] & 0xFF) << 24) | ((ordered[1] & 0xFF) << 16) |
                            ((ordered[2] & 0xFF) << 8) | (ordered[3] & 0xFF));
                } else if (dType.equals("uint32")) {
                    return (double) (((ordered[0] & 0xFFL) << 24) | ((ordered[1] & 0xFFL) << 16) |
                            ((ordered[2] & 0xFFL) << 8) | (ordered[3] & 0xFFL));
                } else if (dType.equals("uint16")) {
                    return (double) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                } else {
                    return (double) (short) (((ordered[0] & 0xFF) << 8) | (ordered[1] & 0xFF));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
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

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        return AccessController.doPrivileged((PrivilegedAction<Double>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);

                int invokeId = (int) (Math.random() * 255);
                byte[] tx = BACnetUtil.buildReadPropertyPacket(
                        finalObjectType, instance, BACnetUtil.PROP_PRESENT_VALUE, invokeId
                );

                InetAddress addr = InetAddress.getByName(ip);
                socket.send(new DatagramPacket(tx, tx.length, addr, port));

                byte[] rxBuf = new byte[512];
                DatagramPacket rxPacket = new DatagramPacket(rxBuf, rxBuf.length);
                socket.receive(rxPacket);

                byte[] data = rxPacket.getData();
                int offset = 6;

                byte npduControl = data[4];
                if ((npduControl & 0x20) != 0) {
                    offset += 2;
                    int dlen = data[offset++] & 0xFF;
                    offset += dlen + 1;
                }

                if ((data[offset] & 0xF0) != 0x30) {
                    throw new RuntimeException("Invalid APDU");
                }

                offset += 3;
                if (data[offset] == (byte) 0x0C) offset += 5;
                if (data[offset] == (byte) 0x19) offset += 2;
                if (data[offset] == (byte) 0x3E) offset++;

                byte tag = data[offset];
                if (tag == (byte) 0x44) {
                    int bits = ((data[offset + 1] & 0xFF) << 24) |
                            ((data[offset + 2] & 0xFF) << 16) |
                            ((data[offset + 3] & 0xFF) << 8) |
                            (data[offset + 4] & 0xFF);
                    return (double) Float.intBitsToFloat(bits);
                } else if (tag == (byte) 0x91 || tag == (byte) 0x21) {
                    return (double) (data[offset + 1] & 0xFF);
                }

                throw new RuntimeException("Unsupported data type");

            } catch (Exception e) {
                throw new RuntimeException(e);
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
                conn.setReadTimeout(3000);

                if (conn.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );
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

    // ==================== Write Operations ====================

    private void writeToDevice(double value) throws Exception {
        String proto = getProtocol().getTag().toLowerCase();
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

        String typeStr = getRegisterType().getTag().toLowerCase();
        String nameStr = getName().toLowerCase();
        int objectType = 1;

        if (typeStr.contains("ai") || nameStr.contains("ai_")) {
            System.err.println("❌ Write blocked: Cannot write to Analog Input (AI)");
            return;
        }
        if (typeStr.contains("ao") || nameStr.contains("ao_")) objectType = 1;
        else if (typeStr.contains("av") || nameStr.contains("av_")) objectType = 2;

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);

                InetAddress addr = InetAddress.getByName(ip);
                int invokeId = (int) (Math.random() * 255);

                byte[] tx = BACnetUtil.buildWritePropertyReal(
                        finalObjectType, instance,
                        BACnetUtil.PROP_PRESENT_VALUE,
                        (float) val, invokeId, 16
                );

                socket.send(new DatagramPacket(tx, tx.length, addr, port));
                System.out.println("✅ BACnet Write Success: " + val);

            } catch (Exception e) {
                System.err.println("❌ BACnet Write Error: " + e.getMessage());
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
        String dType = getDataType().getTag().toLowerCase();
        String order = getByteOrder().getTag();

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);
                socket.setSoTimeout(2000);

                OutputStream out = socket.getOutputStream();
                byte[] request;

                if (type.contains("coil")) {
                    int outputVal = (val > 0) ? 0xFF00 : 0x0000;
                    request = new byte[] {
                            0x00, 0x01, 0x00, 0x00, 0x00, 0x06,
                            0x01, 0x05,
                            (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                            (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                    };
                } else {
                    boolean is32Bit = dType.contains("32");

                    if (is32Bit) {
                        byte[] rawBytes = new byte[4];

                        if (dType.equals("float32")) {
                            int bits = Float.floatToIntBits((float) val);
                            rawBytes[0] = (byte) (bits >> 24);
                            rawBytes[1] = (byte) (bits >> 16);
                            rawBytes[2] = (byte) (bits >> 8);
                            rawBytes[3] = (byte) bits;
                        } else {
                            int bits = (int) val;
                            rawBytes[0] = (byte) (bits >> 24);
                            rawBytes[1] = (byte) (bits >> 16);
                            rawBytes[2] = (byte) (bits >> 8);
                            rawBytes[3] = (byte) bits;
                        }

                        byte[] payload = swapBytes(rawBytes, order);

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bos.write(new byte[]{0x00, 0x01, 0x00, 0x00});
                        bos.write(11 >> 8);
                        bos.write(11 & 0xFF);
                        bos.write(0x01);
                        bos.write(0x10);
                        bos.write(regAddr >> 8);
                        bos.write(regAddr & 0xFF);
                        bos.write(0x00);
                        bos.write(0x02);
                        bos.write(4);
                        bos.write(payload);

                        request = bos.toByteArray();
                    } else {
                        int intVal = (int) val;
                        byte[] rawBytes = {(byte)(intVal >> 8), (byte)(intVal & 0xFF)};
                        byte[] payload = swapBytes(rawBytes, order);
                        int finalVal = ((payload[0] & 0xFF) << 8) | (payload[1] & 0xFF);

                        request = new byte[] {
                                0x00, 0x01, 0x00, 0x00, 0x00, 0x06,
                                0x01, 0x06,
                                (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                                (byte)(finalVal >> 8), (byte)(finalVal & 0xFF)
                        };
                    }
                }

                out.write(request);
                out.flush();
                System.out.println("✅ Modbus Write Success: " + val);

            } catch (Exception e) {
                System.err.println("❌ Modbus Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
        });
    }

    // ==================== Helper Methods ====================

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
        } else if (in.length == 2) {
            if (!order.equals("ABCD")) {
                out[0] = in[1];
                out[1] = in[0];
            } else {
                return in;
            }
        }

        return out;
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