package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.control.*;
import javax.baja.status.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * String Point with Last Value Retention + Automatic Reconnection
 * ✅ Exponential Backoff Retry Strategy
 * ✅ Connection Health Monitoring
 */
@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "stringLength", type = "int", defaultValue = "10")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "10000")
// ✅ Reconnection Properties
@NiagaraProperty(name = "enableAutoReconnect", type = "boolean", defaultValue = "true")
@NiagaraProperty(name = "maxRetries", type = "int", defaultValue = "10")
@NiagaraProperty(name = "initialRetryDelay", type = "int", defaultValue = "1000")
@NiagaraProperty(name = "maxRetryDelay", type = "int", defaultValue = "60000")
@NiagaraProperty(name = "connectionStatus", type = "String", defaultValue = "Disconnected", flags = Flags.READONLY)
public class BMyStringPoint extends BStringWritable {
    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyStringPoint(3904147835)1.0$ @*/
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
// Property "stringLength"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code stringLength} property.
   * @see #getStringLength
   * @see #setStringLength
   */
  public static final Property stringLength = newProperty(0, 10, null);
  
  /**
   * Get the {@code stringLength} property.
   * @see #stringLength
   */
  public int getStringLength() { return getInt(stringLength); }
  
  /**
   * Set the {@code stringLength} property.
   * @see #stringLength
   */
  public void setStringLength(int v) { setInt(stringLength, v, null); }

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
  public static final Property pollInterval = newProperty(0, 10000, null);
  
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
  public static final Type TYPE = Sys.loadType(BMyStringPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    // ✅ Connection Management
    private int consecutiveErrors = 0;
    private int consecutiveSuccesses = 0;
    private long lastSuccessTime = 0;
    private int currentRetryDelay = 0;

    // ✅ Value Retention
    private String lastValidValue = "";
    private boolean hasValidValue = false;

    @Override
    public void started() throws Exception {
        super.started();
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

        if (p == enableAutoReconnect) {
            if (getEnableAutoReconnect()) {
                resetConnectionState();
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

    private void handleSuccessfulRead(String value) {
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

        setFallback(new BStatusString(value, BStatus.ok));
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
            setFallback(new BStatusString(hasValidValue ? lastValidValue : "", BStatus.fault));
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

            setFallback(new BStatusString(lastValidValue, BStatus.down));
        } else {
            updateConnectionStatus("Failed (No Data)");
            setFallback(new BStatusString("", BStatus.fault));
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
                    String value = readFromDevice();

                    handleSuccessfulRead(value);

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

    private String readFromDevice() throws Exception {
        if ("modbus".equalsIgnoreCase(getProtocol())) return readModbus();
        return "N/A";
    }

    private String readModbus() throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) throw new Exception("Device not found");
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();

        int chars = getStringLength();
        int quantity = (chars / 2) + (chars % 2);

        return AccessController.doPrivileged((PrivilegedAction<String>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x03,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(quantity >> 8), (byte)(quantity & 0xFF)
                };

                socket.getOutputStream().write(request);

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len < 9) throw new RuntimeException("Response too short");

                byte[] strBytes = new byte[quantity * 2];
                System.arraycopy(response, 9, strBytes, 0, quantity * 2);

                return new String(strBytes, StandardCharsets.US_ASCII).trim();

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
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