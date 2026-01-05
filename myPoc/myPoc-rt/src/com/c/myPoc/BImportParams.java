package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;

/**
 * Parameter struct for Import API Action
 */
@NiagaraType
@NiagaraProperty(name = "apiUrl", type = "String", defaultValue = "http://localhost:3000/integration/niagara/export?protocol=BACNET")
@NiagaraProperty(name = "apiKey", type = "String", defaultValue = "niagara-secret-key-2025")
public class BImportParams extends BStruct {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BImportParams(379196022)1.0$ @*/
/* Generated Mon Jan 05 17:36:00 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "apiUrl"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code apiUrl} property.
   * @see #getApiUrl
   * @see #setApiUrl
   */
  public static final Property apiUrl = newProperty(0, "http://localhost:3000/integration/niagara/export?protocol=BACNET", null);
  
  /**
   * Get the {@code apiUrl} property.
   * @see #apiUrl
   */
  public String getApiUrl() { return getString(apiUrl); }
  
  /**
   * Set the {@code apiUrl} property.
   * @see #apiUrl
   */
  public void setApiUrl(String v) { setString(apiUrl, v, null); }

////////////////////////////////////////////////////////////////
// Property "apiKey"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code apiKey} property.
   * @see #getApiKey
   * @see #setApiKey
   */
  public static final Property apiKey = newProperty(0, "niagara-secret-key-2025", null);

  /**
   * Get the {@code apiKey} property.
   * @see #apiKey
   */
  public String getApiKey() { return getString(apiKey); }
  
  /**
   * Set the {@code apiKey} property.
   * @see #apiKey
   */
  public void setApiKey(String v) { setString(apiKey, v, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BImportParams.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}