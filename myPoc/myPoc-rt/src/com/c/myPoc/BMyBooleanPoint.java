package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.control.*;
import javax.baja.status.*;
import java.io.*;
import java.net.*;
import java.security.*;

/**
 * Boolean Point with Last Value Retention + Automatic Reconnection
 * ✅ Exponential Backoff Retry Strategy
 * ✅ Connection Health Monitoring
 * ✅ Smart Recovery Logic
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "reverse", type = "boolean", defaultValue = "false")
@NiagaraProperty(name = "useBooleanTag", type = "boolean", defaultValue = "false")
// ✅ Reconnection Properties
@NiagaraProperty(name = "enableAutoReconnect", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "maxRetries", type = "int", defaultValue = "10")
@NiagaraProperty(name = "initialRetryDelay", type = "int", defaultValue = "1000")
@NiagaraProperty(name = "maxRetryDelay", type = "int", defaultValue = "60000")
@NiagaraProperty(name = "connectionStatus", type = "String", defaultValue = "Disconnected", flags = Flags.READONLY)
@NiagaraAction(name = "forceActive", flags = Flags.SUMMARY)
@NiagaraAction(name = "forceInactive", flags = Flags.SUMMARY)
public class BMyBooleanPoint extends BBooleanWritable {
    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyBooleanPoint(3658249652)1.0$ @*/
    /* Generated Mon Dec 29 18:10:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Property "reverse"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code reverse} property.
     * @see #getReverse
     * @see #setReverse
     */
    public static final Property reverse = newProperty(0, false, null);

    /**
     * Get the {@code reverse} property.
     * @see #reverse
     */
    public boolean getReverse() { return getBoolean(reverse); }

    /**
     * Set the {@code reverse} property.
     * @see #reverse
     */
    public void setReverse(boolean v) { setBoolean(reverse, v, null); }

////////////////////////////////////////////////////////////////
// Property "useBooleanTag"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code useBooleanTag} property.
     * @see #getUseBooleanTag
     * @see #setUseBooleanTag
     */
    public static final Property useBooleanTag = newProperty(0, false, null);

    /**
     * Get the {@code useBooleanTag} property.
     * @see #useBooleanTag
     */
    public boolean getUseBooleanTag() { return getBoolean(useBooleanTag); }

    /**
     * Set the {@code useBooleanTag} property.
     * @see #useBooleanTag
     */
    public void setUseBooleanTag(boolean v) { setBoolean(useBooleanTag, v, null); }

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
// Action "forceActive"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code forceActive} action.
     * @see #forceActive()
     */
    public static final Action forceActive = newAction(Flags.SUMMARY, null);

    /**
     * Invoke the {@code forceActive} action.
     * @see #forceActive
     */
    public void forceActive() { invoke(forceActive, null, null); }

////////////////////////////////////////////////////////////////
// Action "forceInactive"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code forceInactive} action.
     * @see #forceInactive()
     */
    public static final Action forceInactive = newAction(Flags.SUMMARY, null);

    /**
     * Invoke the {@code forceInactive} action.
     * @see #forceInactive
     */
    public void forceInactive() { invoke(forceInactive, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyBooleanPoint.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;
    private volatile boolean forceWriteInProgress = false;

    // ✅ Connection Management
    private int consecutiveErrors = 0;
    private int consecutiveSuccesses = 0;
    private long lastSuccessTime = 0;
    private int currentRetryDelay = 0;

    // ✅ Value Retention
    private boolean lastValidValue = false;
    private boolean hasValidValue = false;

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("🔵 Boolean Point Started: " + getName());
        resetConnectionState();
        startPolling();
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("🔴 Boolean Point Stopped: " + getName());
        stopPolling();
        super.stopped();
    }

    @Override
    public void changed(Property p, Context cx) {
        super.changed(p, cx);

        if (p == enableAutoReconnect) {
            if (getEnableAutoReconnect()) {
                resetConnectionState();
            }
        }

        if (p == out) {
            if (forceWriteInProgress) {
                return;
            }

            BStatusBoolean outVal = getOut();
            BStatusBoolean fbVal = getFallback();

            if (outVal.getStatus().isNull()) {
                return;
            }

            boolean outValue = outVal.getValue();
            boolean fbValue = fbVal.getValue();

            if (outValue != fbValue) {
                try {
                    writeToDevice(outValue);
                } catch (Exception e) {
                    System.err.println("❌ Write failed: " + e.getMessage());
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

    private void handleSuccessfulRead(boolean value) {
        lastValidValue = value;
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

        setFallback(new BStatusBoolean(value, BStatus.ok));
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
            setFallback(new BStatusBoolean(hasValidValue ? lastValidValue : false, BStatus.fault));
            return;
        }

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

            setFallback(new BStatusBoolean(lastValidValue, BStatus.down));
        } else {
            updateConnectionStatus("Failed (No Data)");
            setFallback(new BStatusBoolean(false, BStatus.fault));
        }
    }

    public void doForceActive() {
        System.out.println("🔧 Force Active: " + getName());
        try {
            forceWriteInProgress = true;
            writeToDevice(true);
            BStatusBoolean newOut = new BStatusBoolean(true, BStatus.ok);
            setOut(newOut);
            setFallback(newOut);
            System.out.println("✅ Force Active completed");
        } catch (Exception e) {
            System.err.println("❌ Force Active failed: " + e.getMessage());
        } finally {
            forceWriteInProgress = false;
        }
    }

    public void doForceInactive() {
        System.out.println("🔧 Force Inactive: " + getName());
        try {
            forceWriteInProgress = true;
            writeToDevice(false);
            BStatusBoolean newOut = new BStatusBoolean(false, BStatus.ok);
            setOut(newOut);
            setFallback(newOut);
            System.out.println("✅ Force Inactive completed");
        } catch (Exception e) {
            System.err.println("❌ Force Inactive failed: " + e.getMessage());
        } finally {
            forceWriteInProgress = false;
        }
    }

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;

        pollingThread = new Thread(() -> {
            try {
                // Random start delay
                Thread.sleep((long)(Math.random() * 2000));
            } catch (InterruptedException e) { return; }

            System.out.println("🔄 Polling started: " + getName() +
                    (getEnableAutoReconnect() ? " (Auto-Reconnect ON)" : ""));

            while (isPolling) {
                try {
                    boolean val = readFromDevice();
                    if (getReverse()) val = !val;

                    handleSuccessfulRead(val);

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
        pollingThread.setName("BoolPoll-" + getName());
        pollingThread.start();
    }

    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) pollingThread.interrupt();
    }

    private BMyPointDevice getParentDevice() {
        BComplex parent = getParent();
        int retry = 0;
        while (parent != null && retry < 5) {
            if (parent instanceof BMyPointDevice) return (BMyPointDevice) parent;
            if (parent instanceof BComponent) parent = ((BComponent) parent).getParent();
            else break;
            retry++;
        }
        return null;
    }

    private void writeToDevice(boolean value) throws Exception {
        if (getReverse()) {
            value = !value;
        }

        String proto = getProtocol().toLowerCase();

        if ("bacnet".equals(proto)) {
            writeBACnet(value);
        } else if ("modbus".equals(proto)) {
            writeModbus(value);
        }
    }

    private void writeBACnet(boolean value) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        String nameStr = getName().toLowerCase();
        int objectType = 3;
        if (nameStr.contains("bo_")) objectType = 4;
        else if (nameStr.contains("bv_")) objectType = 5;

        int instance = getRegisterAddress();
        int finalObjectType = objectType;
        boolean finalValue = value;

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                InetAddress addr = InetAddress.getByName(ip);

                int invokeId = (int) (Math.random() * 255);
                byte[] tx = BACnetUtil.buildWritePropertyBoolean(finalObjectType, instance, 85, finalValue, invokeId, 16);
                socket.send(new DatagramPacket(tx, tx.length, addr, port));

                setFallback(new BStatusBoolean(finalValue, BStatus.ok));
                System.out.println("BACnet Write Success: " + finalValue);
            } catch (Exception e) {
                System.err.println("BACnet Write Error: " + e.getMessage());
            } finally {
                if (socket != null) socket.close();
            }
            return null;
        });
    }

    private void writeModbus(boolean value) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) {
            throw new Exception("Device not found");
        }

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                int outputVal = value ? 0xFF00 : 0x0000;

                byte[] request = {
                        0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x05,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                };

                socket.getOutputStream().write(request);
                System.out.println("✅ Modbus Write sent successfully");

            } catch (Exception e) {
                System.err.println("❌ Modbus Write Error: " + e.getMessage());
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {}
            }
            return null;
        });
    }

    private boolean readFromDevice() throws Exception {
        String proto = getProtocol().toLowerCase();
        if ("modbus".equals(proto)) return readModbus();
        if ("bacnet".equals(proto)) return readBACnet();
        return false;
    }

    private boolean readBACnet() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;
        String nameStr = getName().toLowerCase();
        int objectType = 3;
        if (nameStr.contains("bo_")) objectType = 4;
        else if (nameStr.contains("bv_")) objectType = 5;
        int instance = getRegisterAddress();
        int finalObjectType = objectType;
        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(3000);
                int invokeId = (int) (Math.random() * 255);
                byte[] tx = BACnetUtil.buildReadPropertyPacket(finalObjectType, instance, 85, invokeId);
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
                if ((data[offset] & 0xF0) != 0x30) throw new RuntimeException("Invalid APDU");
                offset += 3;
                if (data[offset] == 0x0C) offset += 5;
                if (data[offset] == 0x19) offset += 2;
                if (data[offset] == 0x3E) offset++;
                byte tag = data[offset];
                if ((tag & 0xF0) == 0x10 || (tag & 0xF0) == 0x90) return (tag & 0x01) == 1;
                throw new RuntimeException("Unsupported boolean format");
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (socket != null) socket.close();
            }
        });
    }

    private boolean readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();
        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);
                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x01,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        0x00, 0x01
                };
                socket.getOutputStream().write(request);
                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);
                if (len < 9) throw new RuntimeException("Response too short");
                return (response[9] & 0x01) == 1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {}
            }
        });
    }
}