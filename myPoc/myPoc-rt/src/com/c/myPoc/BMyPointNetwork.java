package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.driver.*;
import javax.baja.status.*;

import com.c.myPoc.services.DefaultPointRepository;
import com.c.myPoc.services.DefaultPointService;
import com.c.myPoc.services.PointRepository;
import com.c.myPoc.services.PointService;
import com.cocoad.extension.service.ServiceCollection;

/**
 * Point API Network Driver - Full BDeviceNetwork Implementation with BIService
 *
 * วิธีใช้งาน:
 * 1. ลาก MyPointNetwork ไปวางที่ Services (ไม่ใช่ Drivers)
 * 2. API พร้อมใช้งานทันทีที่ /pointApi
 *
 * หมายเหตุ:
 * - ใช้ร่วมกับ PointController ที่ register ใน web.xml
 * - เป็น Network Driver ที่ทำงานเป็น Service
 * - Servlet จะ auto-register เมื่อ Station Start
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "2.0.0", flags = Flags.READONLY)
@NiagaraProperty(name = "endpoint", type = "String", defaultValue = "/pointApi", flags = Flags.READONLY)
@NiagaraAction(name = "restart", flags = Flags.SUMMARY)
@NiagaraAction(name = "ping")
public class BMyPointNetwork extends BDeviceNetwork implements BIService {

    /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
    /*@ $com.c.myPoc.BMyPointNetwork(3542891234)1.0$ @*/
    /* Generated Fri Dec 15 16:30:00 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "version"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code version} property.
     * @see #getVersion
     * @see #setVersion
     */
    public static final Property version = newProperty(Flags.READONLY, "2.0.0", null);

    /**
     * Get the {@code version} property.
     * @see #version
     */
    public String getVersion() { return getString(version); }

    /**
     * Set the {@code version} property.
     * @see #version
     */
    public void setVersion(String v) { setString(version, v, null); }

////////////////////////////////////////////////////////////////
// Property "endpoint"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code endpoint} property.
     * @see #getEndpoint
     * @see #setEndpoint
     */
    public static final Property endpoint = newProperty(Flags.READONLY, "/pointApi", null);

    /**
     * Get the {@code endpoint} property.
     * @see #endpoint
     */
    public String getEndpoint() { return getString(endpoint); }

    /**
     * Set the {@code endpoint} property.
     * @see #endpoint
     */
    public void setEndpoint(String v) { setString(endpoint, v, null); }

////////////////////////////////////////////////////////////////
// Action "restart"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code restart} action.
     * @see #restart()
     */
    public static final Action restart = newAction(Flags.SUMMARY, null);

    /**
     * Invoke the {@code restart} action.
     * @see #restart
     */
    public void restart() { invoke(restart, null, null); }

////////////////////////////////////////////////////////////////
// Action "ping"
////////////////////////////////////////////////////////////////

    /**
     * Slot for the {@code ping} action.
     * @see #ping()
     */
    public static final Action ping = newAction(0, null);

    /**
     * Invoke the {@code ping} action.
     * @see #ping
     */
    public void ping() { invoke(ping, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

    @Override
    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyPointNetwork.class);

    /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // ==================== BIService Implementation ====================

    @Override
    public Type[] getServiceTypes() {
        return new Type[] { TYPE };
    }

    @Override
    public void serviceStarted() throws Exception {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Service Starting...");

        try {
            // Register services in ServiceCollection
            ServiceCollection.getInstance()
                    .addSingleton(PointRepository.class, DefaultPointRepository.class)
                    .addSingleton(PointService.class, DefaultPointService.class);

            System.out.println("MyPointNetwork: Services registered successfully");
            System.out.println("");
            System.out.println("✅ API Ready at: " + getEndpoint() + "/*");
            System.out.println("");
            System.out.println("📝 Available Endpoints:");
            System.out.println("  GET " + getEndpoint() + "/              - Get all points");
            System.out.println("  GET " + getEndpoint() + "/?query=...    - Custom BQL query");
            System.out.println("  GET " + getEndpoint() + "/?points=...   - Filter points");
            System.out.println("  GET " + getEndpoint() + "/?limit=N      - Limit results");
            System.out.println("  GET " + getEndpoint() + "/total         - Get total count");
            System.out.println("  GET " + getEndpoint() + "/{name}        - Get specific point");
            System.out.println("");
            System.out.println("💡 Servlet ถูก register อัตโนมัติผ่าน web.xml");
            System.out.println("💡 วางไว้ที่ Services (เหมือน obix network)");
            System.out.println("");
            System.out.println("MyPointNetwork: Service Started successfully ✅");

        } catch (Exception e) {
            System.err.println("MyPointNetwork: Failed to start service - " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        System.out.println("===========================================");
    }

    @Override
    public void serviceStopped() throws Exception {
        System.out.println("MyPointNetwork: Service Stopping...");
        System.out.println("MyPointNetwork: Service Stopped");
    }

    // ==================== BDeviceNetwork Required Methods ====================

    @Override
    public Type getDeviceType() {
        // Return BDevice type - required by BDeviceNetwork
        return BDevice.TYPE;
    }

    @Override
    public Type getDeviceFolderType() {
        // Return BDeviceFolder type - required by BDeviceNetwork
        return BDeviceFolder.TYPE;
    }

    // ==================== Network Lifecycle ====================

    @Override
    public void started() throws Exception {
        super.started();

        // ไม่ต้องทำอะไรที่นี่ เพราะจะทำใน serviceStarted() แทน
        // serviceStarted() จะถูกเรียกอัตโนมัติโดย Niagara Service Framework
        System.out.println("MyPointNetwork: Network started() called");
    }

    @Override
    public void stopped() throws Exception {
        System.out.println("MyPointNetwork: Network stopping...");
        super.stopped();
    }

    // ==================== Action Handlers ====================

    public void doRestart() throws Exception {
        System.out.println("MyPointNetwork: Restarting service...");
        serviceStopped();
        serviceStarted();
        System.out.println("MyPointNetwork: Service restarted");
    }

    public void doPing() {
        System.out.println("===========================================");
        System.out.println("MyPointNetwork: Ping Response");
        System.out.println("  - Status: " + getStatus());
        System.out.println("  - Running: " + isRunning());
        System.out.println("  - Endpoint: " + getEndpoint());
        System.out.println("  - Version: " + getVersion());

        // Count devices (จะเป็น 0 เพราะเราไม่ใช้ devices)
        BDevice[] devices = getDevices();
        System.out.println("  - Devices: " + (devices != null ? devices.length : 0));

        // Check service registration
        try {
            PointService pointService = ServiceCollection.getInstance().getService(PointService.class);
            System.out.println("  - PointService: " + (pointService != null ? "✅ Registered" : "❌ Not Found"));

            PointRepository pointRepo = ServiceCollection.getInstance().getService(PointRepository.class);
            System.out.println("  - PointRepository: " + (pointRepo != null ? "✅ Registered" : "❌ Not Found"));
        } catch (Exception e) {
            System.out.println("  - Services: ❌ Error checking - " + e.getMessage());
        }

        System.out.println("===========================================");
    }

    // ==================== Override ping behavior ====================

    /**
     * Override เพื่อป้องกัน BPingMonitor ไป scan devices
     * เพราะเราไม่มี devices จริงๆ
     */
    @Override
    public BDevice[] getDevices() {
        // Return empty array แทน null เพื่อป้องกัน NullPointerException
        return new BDevice[0];
    }
}