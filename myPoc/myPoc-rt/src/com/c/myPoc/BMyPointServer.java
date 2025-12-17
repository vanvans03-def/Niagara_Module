package com.c.myPoc;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.c.myPoc.services.DefaultPointRepository;
import com.c.myPoc.services.DefaultPointService;
import com.c.myPoc.services.PointRepository;
import com.c.myPoc.services.PointService;

import com.cocoad.extension.service.ServiceCollection;

/**
 * Point API Server
 *
 * วิธีใช้งาน:
 * 1. Add MyPointServer to Services
 * 2. Services จะ register และพร้อมใช้งาน
 * 3. PointController ต้อง register manual ใน WebService > Servlets
 */
@NiagaraType
@NiagaraProperty(name = "version", type = "String", defaultValue = "1.0.0", flags = Flags.READONLY)
@NiagaraProperty(name = "status", type = "String", defaultValue = "Stopped", flags = Flags.READONLY)
@NiagaraAction(name = "restart", flags = Flags.SUMMARY)
public class BMyPointServer extends BComponent implements BIService {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMyPointServer(2746801522)1.0$ @*/
/* Generated Fri Dec 12 15:11:54 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "version"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code version} property.
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(Flags.READONLY, "1.0.0", null);
  
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
// Property "status"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY, "Stopped", null);
  
  /**
   * Get the {@code status} property.
   * @see #status
   */
  public String getStatus() { return getString(status); }
  
  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(String v) { setString(status, v, null); }

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
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyPointServer.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    @Override
    public Type[] getServiceTypes() {
        return new Type[] { TYPE };
    }

    @Override
    public void serviceStarted() throws Exception {
        System.out.println("===========================================");
        System.out.println("MyPointServer: Starting...");

        // Register services in ServiceCollection
        ServiceCollection.getInstance()
                .addSingleton(PointRepository.class, DefaultPointRepository.class)
                .addSingleton(PointService.class, DefaultPointService.class);

        System.out.println("MyPointServer: Services registered successfully");
        System.out.println("");
        System.out.println("📝 Next Steps:");
        System.out.println("1. Go to: Platform > Services > WebService > Servlets");
        System.out.println("2. Right Click > New > PointController");
        System.out.println("3. Set Name: pointApi");
        System.out.println("4. Save");
        System.out.println("5. API will be available at: /pointApi");
        System.out.println("");

        setStatus("Running");
        System.out.println("MyPointServer: Started successfully ✅");
        System.out.println("===========================================");
    }

    @Override
    public void serviceStopped() throws Exception {
        System.out.println("MyPointServer: Stopping...");
        setStatus("Stopped");
        System.out.println("MyPointServer: Stopped");
    }

    public void doRestart() throws Exception {
        serviceStopped();
        serviceStarted();
    }
}