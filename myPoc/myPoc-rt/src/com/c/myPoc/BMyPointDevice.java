package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.util.IFuture;

@NiagaraType
@NiagaraProperty(name = "deviceName", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceAddress", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceDescription", type = "String", defaultValue = "")
@NiagaraAction(name = "discoverPoints", flags = Flags.ASYNC | Flags.SUMMARY)  // ✅ เพิ่ม Action
@NiagaraAction(name = "clearPoints", flags = Flags.SUMMARY)  // ✅ เพิ่ม Action
public class BMyPointDevice extends BDevice {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyPointDevice(3249273190)1.0$ @*/
/* Generated Wed Dec 17 16:22:45 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "deviceName"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceName} property.
   * @see #getDeviceName
   * @see #setDeviceName
   */
  public static final Property deviceName = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceName} property.
   * @see #deviceName
   */
  public String getDeviceName() { return getString(deviceName); }
  
  /**
   * Set the {@code deviceName} property.
   * @see #deviceName
   */
  public void setDeviceName(String v) { setString(deviceName, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceAddress"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceAddress} property.
   * @see #getDeviceAddress
   * @see #setDeviceAddress
   */
  public static final Property deviceAddress = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public String getDeviceAddress() { return getString(deviceAddress); }
  
  /**
   * Set the {@code deviceAddress} property.
   * @see #deviceAddress
   */
  public void setDeviceAddress(String v) { setString(deviceAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceDescription"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code deviceDescription} property.
   * @see #getDeviceDescription
   * @see #setDeviceDescription
   */
  public static final Property deviceDescription = newProperty(0, "", null);
  
  /**
   * Get the {@code deviceDescription} property.
   * @see #deviceDescription
   */
  public String getDeviceDescription() { return getString(deviceDescription); }
  
  /**
   * Set the {@code deviceDescription} property.
   * @see #deviceDescription
   */
  public void setDeviceDescription(String v) { setString(deviceDescription, v, null); }

////////////////////////////////////////////////////////////////
// Action "discoverPoints"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code discoverPoints} action.
   * @see #discoverPoints()
   */
  public static final Action discoverPoints = newAction(Flags.ASYNC | Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code discoverPoints} action.
   * @see #discoverPoints
   */
  public void discoverPoints() { invoke(discoverPoints, null, null); }

////////////////////////////////////////////////////////////////
// Action "clearPoints"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code clearPoints} action.
   * @see #clearPoints()
   */
  public static final Action clearPoints = newAction(Flags.SUMMARY, null);
  
  /**
   * Invoke the {@code clearPoints} action.
   * @see #clearPoints
   */
  public void clearPoints() { invoke(clearPoints, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== BDevice Required Methods ====================

    @Override
    public Type getNetworkType() {
        return BMyUniversalNetwork.TYPE;  // ✅ เปลี่ยนเป็น UniversalNetwork
    }

    // ==================== Device Lifecycle ====================

    @Override
    public void started() throws Exception {
        super.started();
        System.out.println("MyPointDevice: Device started - " + getDeviceName());
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("MyPointDevice: Device stopped - " + getDeviceName());
        super.stopped();
    }

    // ==================== Device Communication ====================

    @Override
    public void doPing() throws Exception {
        System.out.println("MyPointDevice: Pinging device " + getDeviceName());
        try {
            setFaultCause(null);
            setStatus(BStatus.ok);
            System.out.println("MyPointDevice: Ping successful");
        } catch (Exception e) {
            System.err.println("MyPointDevice: Ping failed - " + e.getMessage());
            setFaultCause(e.getMessage());
            setStatus(BStatus.fault);
            throw e;
        }
    }

    @Override
    protected IFuture postPing() {
        return null;
    }

    // ==================== Point Discovery ====================

    /**
     * Discover points from device
     */
    public void doDiscoverPoints() throws Exception {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("MyPointDevice: Discovering points...");
        System.out.println("Device: " + getDeviceName());
        System.out.println("Address: " + getDeviceAddress());
        System.out.println("Description: " + getDeviceDescription());

        try {
            String protocol = detectProtocol();
            System.out.println("Detected Protocol: " + protocol);

            int pointsCreated = 0;

            switch (protocol) {
                case "bacnet":
                    pointsCreated = discoverBACnetPoints();
                    break;
                case "modbus":
                    pointsCreated = discoverModbusPoints();
                    break;
                case "http":
                    pointsCreated = discoverHTTPPoints();
                    break;
                default:
                    System.out.println("⚠️ Unknown protocol, creating test points...");
                    pointsCreated = createTestPoints();
            }

            System.out.println("✅ Discovery completed: " + pointsCreated + " points created");
            System.out.println("═══════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("❌ Point discovery failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Detect protocol from device description
     */
    private String detectProtocol() {
        String desc = getDeviceDescription().toLowerCase();

        if (desc.contains("bacnet")) {
            return "bacnet";
        } else if (desc.contains("modbus")) {
            return "modbus";
        } else if (desc.contains("http")) {
            return "http";
        }

        return "unknown";
    }

    /**
     * Discover BACnet Points
     */
    private int discoverBACnetPoints() throws Exception {
        System.out.println("🔍 Discovering BACnet objects...");

        // TODO: Implement actual BACnet object discovery
        // For now, create sample points

        int count = 0;
        String[] objectTypes = {"AnalogInput", "AnalogOutput", "BinaryInput", "BinaryOutput"};

        for (int i = 0; i < 4; i++) {
            String pointName = objectTypes[i] + "_" + i;

            if (addPoint(pointName, "bacnet", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Discover Modbus Points
     */
    private int discoverModbusPoints() throws Exception {
        System.out.println("🔍 Discovering Modbus registers...");

        int count = 0;

        // Scan holding registers 0-9
        for (int i = 0; i < 10; i++) {
            String pointName = "HoldingRegister_" + i;

            if (addPoint(pointName, "modbus", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Discover HTTP Points
     */
    private int discoverHTTPPoints() throws Exception {
        System.out.println("🔍 Discovering HTTP endpoints...");

        // TODO: Query HTTP API for available points
        // For now, create sample points

        int count = 0;
        String[] endpoints = {"temperature", "humidity", "pressure", "status"};

        for (int i = 0; i < endpoints.length; i++) {
            if (addPoint(endpoints[i], "http", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Create test points for unknown protocols
     */
    private int createTestPoints() throws Exception {
        int count = 0;

        for (int i = 0; i < 5; i++) {
            String pointName = "TestPoint_" + i;

            if (addPoint(pointName, "test", i)) {
                count++;
            }
        }

        return count;
    }

    /**
     * Add point to device
     */
    private boolean addPoint(String pointName, String protocol, int address) {
        try {
            // Check if point already exists
            if (get(pointName) != null) {
                System.out.println("  ⏭️  Point already exists: " + pointName);
                return false;
            }

            // Create new proxy point
            BMyProxyPoint point = new BMyProxyPoint();
            point.setProtocol(protocol);
            point.setRegisterAddress(address);

            // Add to device
            add(pointName, point);

            System.out.println("  ✅ Created point: " + pointName +
                    " (Protocol: " + protocol +
                    ", Address: " + address + ")");

            return true;

        } catch (Exception e) {
            System.err.println("  ❌ Failed to create point " + pointName + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Clear all points
     */
    /**
     * Clear all points
     */
    public void doClearPoints() throws Exception {
        System.out.println("Clearing all points from " + getDeviceName());

        int count = 0;

        // ✅ แก้ไข: ใช้ getPropertiesArray() แทน getSlots().list
        // เมธอดนี้จะคืนค่าเป็น Array ของ Property ทั้งหมดใน Device นี้
        Property[] properties = getPropertiesArray();

        for (Property p : properties) {
            // ไม่ควรพยายาม remove property ที่ไม่ใช่ Dynamic (เช่น property ที่ hardcode ไว้)
            // แต่การเช็ค instanceof BMyProxyPoint จะช่วยกรองได้อยู่แล้ว

            BObject child = get(p);

            // ตรวจสอบว่าเป็น Point ของเราหรือไม่ (BMyProxyPoint)
            if (child instanceof BMyProxyPoint) {
                // ใช้ p.getName() เพื่อเอาชื่อ Slot แล้วสั่งลบ
                remove(p.getName());
                count++;
            }
        }

        System.out.println("✅ Cleared " + count + " point(s)");
    }

    /**
     * Get network parent
     */
    public BMyUniversalNetwork getMyUniversalNetwork() {
        BComplex parent = getParent();
        while (parent != null && !(parent instanceof BMyUniversalNetwork)) {
            if (parent instanceof BComponent) {
                parent = ((BComponent) parent).getParent();
            } else {
                break;
            }
        }
        return (BMyUniversalNetwork) parent;
    }
}