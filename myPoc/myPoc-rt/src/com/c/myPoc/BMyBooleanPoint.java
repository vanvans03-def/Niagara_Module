package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;
import javax.baja.control.*;
import javax.baja.status.*;
import java.io.*;
import java.net.*;
import java.security.*;

@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "reverse", type = "boolean", defaultValue = "false")
@NiagaraProperty(name = "useBooleanTag", type = "boolean", defaultValue = "false")
@NiagaraAction(name = "forceActive", flags = Flags.SUMMARY)
@NiagaraAction(name = "forceInactive", flags = Flags.SUMMARY)
public class BMyBooleanPoint extends BBooleanWritable {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyBooleanPoint(4262909873)1.0$ @*/
/* Generated Mon Dec 29 16:57:39 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
    private volatile boolean forceWriteInProgress = false; // ✅ Flag เพื่อป้องกัน loop

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("🔵 Boolean Point Started: " + getName());
        startPolling();
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("🔴 Boolean Point Stopped: " + getName());
        stopPolling();
        super.stopped();
    }

    /**
     * ✅ แก้ไข changed() ให้มี logging ชัดเจน
     */
    @Override
    public void changed(Property p, Context cx) {
        super.changed(p, cx);

        if (p == out) {
            System.out.println("📢 changed() triggered for: " + getName());

            // ✅ ป้องกัน recursive call จาก force write
            if (forceWriteInProgress) {
                System.out.println("   ⏭️  Skipping (force write in progress)");
                return;
            }

            BStatusBoolean outVal = getOut();
            BStatusBoolean fbVal = getFallback();

            // ✅ แสดง debug info
            System.out.println("   📊 Out: " + outVal);
            System.out.println("   📊 Fallback: " + fbVal);

            // ✅ ตรวจสอบ status ก่อน
            if (outVal.getStatus().isNull()) {
                System.out.println("   ⚠️  Out status is null, skipping write");
                return;
            }

            // ✅ ตรวจสอบว่าค่าต่างกันจริงหรือไม่
            boolean outValue = outVal.getValue();
            boolean fbValue = fbVal.getValue();

            System.out.println("   🔍 Comparing: out=" + outValue + " vs fb=" + fbValue);

            if (outValue != fbValue) {
                System.out.println("   ⚡ Values different! Writing to device...");
                try {
                    writeToDevice(outValue);
                } catch (Exception e) {
                    System.err.println("   ❌ Write failed: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                System.out.println("   ✅ Values same, no write needed");
            }
        }
    }

    // ================= Action Handlers (Fixed) =================

    /**
     * ✅ แก้ไข: Force Active - Update out property
     */
    public void doForceActive() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("🔧 Force Active Action Called");
        System.out.println("   Point: " + getName());
        System.out.println("   Current Out: " + getOut());
        System.out.println("   Current Fallback: " + getFallback());

        try {
            forceWriteInProgress = true;

            // ✅ 1. เขียนไปที่ device ทันที
            System.out.println("   → Step 1: Writing to device...");
            writeToDevice(true);

            // ✅ 2. Update out property เพื่อให้ UI แสดงค่าใหม่
            System.out.println("   → Step 2: Updating out property...");
            BStatusBoolean newOut = new BStatusBoolean(true, BStatus.ok);
            setOut(newOut);

            // ✅ 3. Update fallback ด้วย (เพื่อให้ polling เห็นค่าที่ถูกต้อง)
            System.out.println("   → Step 3: Updating fallback...");
            setFallback(newOut);

            System.out.println("✅ Force Active completed successfully");
            System.out.println("   New Out: " + getOut());
            System.out.println("   New Fallback: " + getFallback());

        } catch (Exception e) {
            System.err.println("❌ Force Active failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            forceWriteInProgress = false;
            System.out.println("═══════════════════════════════════════════");
        }
    }

    /**
     * ✅ แก้ไข: Force Inactive - Update out property
     */
    public void doForceInactive() {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("🔧 Force Inactive Action Called");
        System.out.println("   Point: " + getName());
        System.out.println("   Current Out: " + getOut());
        System.out.println("   Current Fallback: " + getFallback());

        try {
            forceWriteInProgress = true;

            System.out.println("   → Step 1: Writing to device...");
            writeToDevice(false);

            System.out.println("   → Step 2: Updating out property...");
            BStatusBoolean newOut = new BStatusBoolean(false, BStatus.ok);
            setOut(newOut);

            System.out.println("   → Step 3: Updating fallback...");
            setFallback(newOut);

            System.out.println("✅ Force Inactive completed successfully");
            System.out.println("   New Out: " + getOut());
            System.out.println("   New Fallback: " + getFallback());

        } catch (Exception e) {
            System.err.println("❌ Force Inactive failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            forceWriteInProgress = false;
            System.out.println("═══════════════════════════════════════════");
        }
    }

    // ================= Core Logic =================

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

    /**
     * ✅ แก้ไข: เพิ่ม debug logging
     */
    private void writeToDevice(boolean value) throws Exception {
        System.out.println("───────────────────────────────────────────");
        System.out.println("📤 writeToDevice() called");
        System.out.println("   Input value: " + value);
        System.out.println("   Reverse enabled: " + getReverse());

        if (getReverse()) {
            value = !value;
            System.out.println("   🔄 After reverse: " + value);
        }

        String proto = getProtocol().toLowerCase();
        System.out.println("   📡 Protocol: " + proto);
        System.out.println("   📍 Register: " + getRegisterAddress());

        if ("bacnet".equals(proto)) {
            writeBACnet(value);
        } else if ("modbus".equals(proto)) {
            writeModbus(value);
        } else {
            System.err.println("   ❌ Unsupported protocol: " + proto);
        }

        System.out.println("───────────────────────────────────────────");
    }

    /**
     * ✅ BACnet Write with enhanced logging
     */
    private void writeBACnet(boolean value) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 47808;

        // หา Object Type และ Instance
        String nameStr = getName().toLowerCase();
        int objectType = 3; // Default BI
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

                // ✅ อัพเดท Fallback ทันทีหลังเขียนสำเร็จ
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

    /**
     * ✅ Modbus Write with enhanced logging
     */
    private void writeModbus(boolean value) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) {
            throw new Exception("Device not found");
        }

        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();

        System.out.println("   🌐 Target: " + ip + ":" + port);
        System.out.println("   📋 Register: " + regAddr);
        System.out.println("   💾 Value: " + value);

        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                int outputVal = value ? 0xFF00 : 0x0000;
                System.out.println("   → Modbus value: 0x" +
                        Integer.toHexString(outputVal));

                byte[] request = {
                        0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x05,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                };

                socket.getOutputStream().write(request);
                System.out.println("   ✅ Modbus Write sent successfully");

            } catch (Exception e) {
                System.err.println("   ❌ Modbus Write Error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {}
            }
            return null;
        });
    }

    // ================= Polling (ไม่เปลี่ยน) =================

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;
        pollingThread = new Thread(() -> {
            System.out.println("🔄 Polling started: " + getName());
            while (isPolling) {
                try {
                    boolean val = readFromDevice();
                    if (getReverse()) val = !val;

                    // ✅ แสดง log เฉพาะเมื่อค่าเปลี่ยน
                    BStatusBoolean currentFb = getFallback();
                    if (currentFb.getValue() != val || !currentFb.getStatus().isOk()) {
                        System.out.println("📊 Poll update [" + getName() + "]: " + val);
                    }

                    setFallback(new BStatusBoolean(val, BStatus.ok));
                    Thread.sleep(getPollInterval());
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("❌ Poll error [" + getName() + "]: " + e.getMessage());
                    setFallback(new BStatusBoolean(false, BStatus.fault));
                    try { Thread.sleep(getPollInterval()); } catch (InterruptedException ie) { break; }
                }
            }
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