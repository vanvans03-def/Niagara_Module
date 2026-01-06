package com.c.myPoc;

import javax.baja.nre.annotations.*;
import javax.baja.sys.*;

@NiagaraType
// 1. ประกาศ Property ตรงนี้แทน
@NiagaraProperty(
        name = "protocol",
        type = "BProtocolSelect",
        defaultValue = "BProtocolSelect.bacnet"
)
@NiagaraProperty(
        name = "apiKey",
        type = "String",
        defaultValue = ""
)
public class BImportDialog extends BStruct {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BImportDialog(240805866)1.0$ @*/
/* Generated Tue Jan 06 17:18:39 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "protocol"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code protocol} property.
   * @see #getProtocol
   * @see #setProtocol
   */
  public static final Property protocol = newProperty(0, BProtocolSelect.bacnet, null);
  
  /**
   * Get the {@code protocol} property.
   * @see #protocol
   */
  public BProtocolSelect getProtocol() { return (BProtocolSelect)get(protocol); }
  
  /**
   * Set the {@code protocol} property.
   * @see #protocol
   */
  public void setProtocol(BProtocolSelect v) { set(protocol, v, null); }

////////////////////////////////////////////////////////////////
// Property "apiKey"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code apiKey} property.
   * @see #getApiKey
   * @see #setApiKey
   */
  public static final Property apiKey = newProperty(0, "", null);
  
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
  public static final Type TYPE = Sys.loadType(BImportDialog.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

}