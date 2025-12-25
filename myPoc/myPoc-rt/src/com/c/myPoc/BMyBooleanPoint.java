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

@NiagaraType
@NiagaraProperty(name = "address", type = "String", defaultValue = "")
@NiagaraProperty(name = "registerAddress", type = "int", defaultValue = "0")
@NiagaraProperty(name = "protocol", type = "String", defaultValue = "modbus")
@NiagaraProperty(name = "pollInterval", type = "int", defaultValue = "5000")
@NiagaraProperty(name = "reverse", type = "boolean", defaultValue = "false") // เพิ่มฟังชั่นกลับค่า True/False
public class BMyBooleanPoint extends BBooleanWritable {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyBooleanPoint(754008503)1.0$ @*/
/* Generated Tue Dec 23 15:19:13 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyBooleanPoint.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    private Thread pollingThread;
    private volatile boolean isPolling = false;

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

    @Override
    public void changed(Property p, Context cx) {
        super.changed(p, cx);
        if (p == out) {
            BStatusBoolean outVal = getOut();
            BStatusBoolean fbVal = getFallback();
            if (outVal.getStatus().isNull()) return;

            // เช็คว่าค่าเปลี่ยนหรือไม่ (boolean เทียบกันง่ายๆ)
            if (outVal.getValue() != fbVal.getValue()) {
                try {
                    writeToDevice(outVal.getValue());
                } catch (Exception e) {
                    System.err.println("Write failed: " + e.getMessage());
                }
            }
        }
    }

    private void startPolling() {
        if (isPolling) return;
        isPolling = true;

        pollingThread = new Thread(() -> {
            System.out.println("🔄 Boolean polling started: " + getName());

            while (isPolling) {
                try {
                    boolean value = readFromDevice();
                    if (getReverse()) value = !value;

                    BStatusBoolean statusValue = new BStatusBoolean(value, BStatus.ok);
                    setFallback(statusValue);

                    if (Math.random() < 0.1) { // 10% logging
                        System.out.println("📊 Bool Poll [" + getName() + "]: " + value);
                    }

                    Thread.sleep(getPollInterval());
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                    System.err.println("❌ Bool Poll Error [" + getName() + "]: " + e.getMessage());
                    try {
                        setFallback(new BStatusBoolean(false, BStatus.fault));
                        Thread.sleep(getPollInterval());
                    } catch (InterruptedException ie) {
                        break;
                    }
                }
            }

            System.out.println("⏹️  Boolean polling stopped: " + getName());
        });

        pollingThread.setDaemon(true);
        pollingThread.setName("BoolPoll-" + getName());
        pollingThread.start();
    }


    private void stopPolling() {
        isPolling = false;
        if (pollingThread != null) {
            pollingThread.interrupt();
        }
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

        // Parse object type from point name
        String nameStr = getName().toLowerCase();
        int objectType = 3; // Default = BI

        if (nameStr.contains("bi_")) objectType = 3;
        else if (nameStr.contains("bo_")) objectType = 4;
        else if (nameStr.contains("bv_")) objectType = 5;

        int instance = getRegisterAddress();
        int finalObjectType = objectType;

        return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
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
                    throw new RuntimeException("Invalid APDU type");
                }

                offset += 3; // APDU header
                if (data[offset] == (byte) 0x0C) offset += 5; // Object ID
                if (data[offset] == (byte) 0x19) offset += 2; // Property ID
                if (data[offset] == (byte) 0x3E) offset++; // Opening tag

                byte tag = data[offset];

                // Boolean value (Tag 0x11 = INACTIVE, 0x91 = ACTIVE)
                if ((tag & 0xF0) == 0x10 || (tag & 0xF0) == 0x90) {
                    return (tag & 0x01) == 1;
                }

                throw new RuntimeException("Unsupported boolean format");

            } catch (Exception e) {
                throw new RuntimeException("BACnet bool read failed: " + e.getMessage());
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

                // Modbus: อ่าน Coil (FC 01) - อ่านทีละ 1 bit
                // ถ้าอยากอ่าน Discrete Input ให้แก้ 0x01 เป็น 0x02
                byte[] request = {
                        0x00, 0x01, 0x00, 0x00, 0x00, 0x06, 0x01, 0x01,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        0x00, 0x01 // Quantity 1
                };

                socket.getOutputStream().write(request);

                InputStream in = socket.getInputStream();
                byte[] response = new byte[256];
                int len = in.read(response);

                if (len < 9) throw new RuntimeException("Response too short");

                // ข้อมูลอยู่ที่ Byte ที่ 9 (1 byte เก็บได้ 8 coils, bit แรกคือค่าของเรา)
                return (response[9] & 0x01) == 1;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
        });
    }

    private void writeToDevice(boolean value) throws Exception {
        BMyPointDevice device = getParentDevice();
        if (device == null) return;
        String[] addrParts = device.getDeviceAddress().split(":");
        String ip = addrParts[0];
        int port = addrParts.length > 1 ? Integer.parseInt(addrParts[1]) : 502;
        int regAddr = getRegisterAddress();

        if (getReverse()) value = !value;

        boolean finalValue = value;
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), 2000);

                // Modbus: Write Single Coil (FC 05)
                // ON = 0xFF00, OFF = 0x0000
                int outputVal = finalValue ? 0xFF00 : 0x0000;

                byte[] request = {
                        0x00, 0x02, 0x00, 0x00, 0x00, 0x06, 0x01, 0x05,
                        (byte)(regAddr >> 8), (byte)(regAddr & 0xFF),
                        (byte)(outputVal >> 8), (byte)(outputVal & 0xFF)
                };

                socket.getOutputStream().write(request);
                System.out.println("Write Boolean: " + finalValue);
            } catch (Exception e) {
                System.err.println("Write Error: " + e.getMessage());
            } finally {
                try { if (socket != null) socket.close(); } catch (Exception e) {}
            }
            return null;
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