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

@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "stringLength", type = "int", defaultValue = "10") // จำนวนตัวอักษรที่ต้องการอ่าน
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "10000") // String ไม่ต้อง Poll บ่อย
public class BMyStringPoint extends BStringWritable {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyStringPoint(2345069566)1.0$ @*/
/* Generated Tue Dec 23 15:19:27 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyStringPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

    @Override
    public void started() throws Exception {
        super.started();
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
                    String value = readFromDevice();
                    BStatusString statusValue = new BStatusString(value, BStatus.ok);
                    setFallback(statusValue);
                    Thread.sleep(getPollInterval());
                } catch (Exception e) {
                    try {
                        setFallback(new BStatusString("", BStatus.fault));
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

        // 1 Register = 2 Bytes = 2 Characters
        // ดังนั้นถ้าจะอ่าน 10 ตัวอักษร ต้องอ่าน 5 Register
        int chars = getStringLength();
        int quantity = (chars / 2) + (chars % 2);

        return AccessController.doPrivileged((PrivilegedAction<String>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                // Modbus: Read Holding Registers (FC 03)
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

                // แปลง Byte Array เป็น String
                // ข้อมูลเริ่มที่ Byte 9
                byte[] strBytes = new byte[quantity * 2];
                System.arraycopy(response, 9, strBytes, 0, quantity * 2);

                // แปลง ASCII -> String (ตัดตัว Null \0 ออกถ้ามี)
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