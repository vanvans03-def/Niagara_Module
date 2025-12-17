package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;

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
    /*@ $com.c.myPoc.BMyPointDevice(3542891236)1.0$ @*/
    /* Generated Fri Dec 17 10:30:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "deviceName"
////////////////////////////////////////////////////////////////

    public static final Property deviceName = newProperty(0, "", null);
    public String getDeviceName() { return getString(deviceName); }
    public void setDeviceName(String v) { setString(deviceName, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceAddress"
////////////////////////////////////////////////////////////////

    public static final Property deviceAddress = newProperty(0, "", null);
    public String getDeviceAddress() { return getString(deviceAddress); }
    public void setDeviceAddress(String v) { setString(deviceAddress, v, null); }

////////////////////////////////////////////////////////////////
// Property "deviceDescription"
////////////////////////////////////////////////////////////////

    public static final Property deviceDescription = newProperty(0, "", null);
    public String getDeviceDescription() { return getString(deviceDescription); }
    public void setDeviceDescription(String v) { setString(deviceDescription, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyPointDevice.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

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
     * Get network parent (renamed from getNetwork to avoid conflict)
     */
    public BMyPointNetwork getMyPointNetwork() {
        BComponent parent = getParent();
        while (parent != null && !(parent instanceof BMyPointNetwork)) {
            parent = parent.getParent();
        }
        return (BMyPointNetwork) parent;
    }
}