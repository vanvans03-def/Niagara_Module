package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;
import javax.baja.util.IFuture;

/**
 * Custom Point Device
 *
 * Represents a device discovered through the discovery process
 * Can contain proxy points that communicate with the external API
 */
@NiagaraType
@NiagaraProperty(name = "deviceName", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceAddress", type = "String", defaultValue = "")
@NiagaraProperty(name = "deviceDescription", type = "String", defaultValue = "")
public class BMyPointDevice extends BDevice {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyPointDevice(166836883)1.0$ @*/
/* Generated Wed Dec 17 13:36:40 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

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
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== BDevice Required Methods ====================

    @Override
    public Type getNetworkType() {
        return BMyPointNetwork.TYPE;
    }

    // ==================== Device Lifecycle ====================

    @Override
    public void started() throws Exception {
        super.started();

        System.out.println("MyPointDevice: Device started - " + getDeviceName());
        System.out.println("  Address: " + getDeviceAddress());
        System.out.println("  Description: " + getDeviceDescription());
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("MyPointDevice: Device stopped - " + getDeviceName());
        super.stopped();
    }

    // ==================== Device Communication ====================

    /**
     * Override to provide custom ping behavior
     */
    @Override
    public void doPing() throws Exception {
        System.out.println("MyPointDevice: Pinging device " + getDeviceName());

        try {
            // TODO: Implement actual device ping via API
            // For now, just set status to OK
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

    /**
     * postPing() - Called after doPing() completes
     * Must return IFuture
     */
    @Override
    protected IFuture postPing() {
        // Return null to indicate no async operation needed
        return null;
    }

    /**
     * Get network parent (renamed from getNetwork to avoid conflict)
     */
    public BMyPointNetwork getMyPointNetwork() {
        BComplex parent = getParent();
        while (parent != null && !(parent instanceof BMyPointNetwork)) {
            if (parent instanceof BComponent) {
                parent = ((BComponent) parent).getParent();
            } else {
                break;
            }
        }
        return (BMyPointNetwork) parent;
    }
}