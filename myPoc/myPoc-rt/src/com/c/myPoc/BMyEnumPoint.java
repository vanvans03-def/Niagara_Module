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
 * Enum Point with Last Value Retention + Automatic Reconnection
 * ✅ Exponential Backoff Retry Strategy
 * ✅ Connection Health Monitoring
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerType", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "dataType", type = "String", defaultValue = "int16")
@NiagaraProperty(name = "byteOrder", type = "String", defaultValue = "ABCD")
// ✅ Reconnection Properties
@NiagaraProperty(name = "enableAutoReconnect", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "maxRetries", type = "int", defaultValue = "10")
@NiagaraProperty(name = "initialRetryDelay", type = "int", defaultValue = "1000")
@NiagaraProperty(name = "maxRetryDelay", type = "int", defaultValue = "60000")
@NiagaraProperty(name = "connectionStatus", type = "String", defaultValue = "Disconnected", flags = Flags.READONLY)
public class BMyEnumPoint extends BEnumWritable {
    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyEnumPoint(909359838)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BMyEnumPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    // ✅ Connection Management
    private int consecutiveErrors = 0;
    private int consecutiveSuccesses = 0;
    private long lastSuccessTime = 0;
    private int currentRetryDelay = 0;

    // ✅ Value Retention
    private BEnum lastValidEnum = null;
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

    private void handleSuccessfulRead(BEnum enumVal) {
        lastValidEnum = enumVal;
        hasValidValue = true;
        lastSuccessTime = System.currentTimeMillis();

        consecutiveSuccesses++;

        if (consecutiveErrors > 0) {
            System.out.println("✅ Connection restored [" + getName() + "] after " + consecutiveErrors + " failures");
            updateConnectionStatus("Connected (Recovered)");
        } else if (consecutiveSuccesses == 1) {
            updateConnectionStatus("Connected");
        }

        consecutiveErrors = 0;
        currentRetryDelay = getInitialRetryDelay();

        setFallback(new BStatusEnum(enumVal, BStatus.ok));
    }

    private void handleFailedRead(Exception e) {
        consecutiveErrors++;
        consecutiveSuccesses = 0;

        String errorMsg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();

        if (consecutiveErrors == 1) {
            System.err.println("❌ Connection lost [" + getName() + "]: " + errorMsg);
            updateConnectionStatus("Disconnected");
        } else if (consecutiveErrors % 5 == 0) {
            System.err.println("⚠️  Still disconnected [" + getName() + "] (attempt " + consecutiveErrors + "): " + errorMsg);
        }

        if (shouldStopRetrying()) {
            System.err.println("🛑 Max retries reached [" + getName() + "], stopping reconnection attempts");
            updateConnectionStatus("Failed (Max Retries)");
            setFallback(new BStatusEnum(hasValidValue && lastValidEnum != null ? lastValidEnum : BDynamicEnum.DEFAULT, BStatus.fault));
            return;
        }

        if (hasValidValue && lastValidEnum != null) {
            if (consecutiveErrors == 1) {
                System.out.println("💾 Keeping last value [" + getName() + "]: " + lastValidEnum.getTag());
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

            setFallback(new BStatusEnum(lastValidEnum, BStatus.down));
        } else {
            updateConnectionStatus("Failed (No Data)");
            setFallback(new BStatusEnum(BDynamicEnum.DEFAULT, BStatus.fault));
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
            try {
                Thread.sleep((long)(Math.random() * 2000));
            } catch (InterruptedException e) { return; }

            System.out.println("🔄 Polling started: " + getName() +
                    (getEnableAutoReconnect() ? " (Auto-Reconnect ON)" : ""));

            while (isPolling) {
                try {
                    double value = readFromDevice();
                    int intValue = (int) value;

                    BFacets facets = getFacets();
                    BEnumRange range = (BEnumRange) facets.get(BFacets.RANGE);

                    BEnum enumVal = null;
                    if (range != null) {
                        try {
                            enumVal = range.get(intValue);
                        } catch (Exception e) {
                            // Value not in range
                        }
                    }

                    if (enumVal != null) {
                        handleSuccessfulRead(enumVal);
                    } else {
                        throw new RuntimeException("Value " + intValue + " not in enum range");
                    }

                    Thread.sleep(getPollInterval());

                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    handleFailedRead(e);

                    if (shouldStopRetrying()) {
                        System.err.println("🛑 Stopping polling thread [" + getName() + "] - Max retries exceeded");
                        break;
                    }

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
        // BACnet write implementation
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