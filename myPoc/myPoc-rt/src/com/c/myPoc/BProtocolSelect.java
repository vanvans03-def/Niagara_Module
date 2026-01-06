package com.c.myPoc;

import javax.baja.sys.*;
import javax.baja.nre.annotations.*;

@NiagaraType
@NiagaraEnum(
        range = {
                @Range(ordinal = 0, value = "bacnet"),
                @Range(ordinal = 1, value = "modbus")
        },
        defaultValue = "bacnet"
)
public final class BProtocolSelect extends BFrozenEnum {
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BProtocolSelect(995253214)1.0$ @*/
/* Generated Tue Jan 06 17:04:33 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */
  
  /** Ordinal value for bacnet. */
  public static final int BACNET = 0;
  /** Ordinal value for modbus. */
  public static final int MODBUS = 1;
  
  /** BProtocolSelect constant for bacnet. */
  public static final BProtocolSelect bacnet = new BProtocolSelect(BACNET);
  /** BProtocolSelect constant for modbus. */
  public static final BProtocolSelect modbus = new BProtocolSelect(MODBUS);
  
  /** Factory method with ordinal. */
  public static BProtocolSelect make(int ordinal)
  {
    return (BProtocolSelect)bacnet.getRange().get(ordinal, false);
  }
  
  /** Factory method with tag. */
  public static BProtocolSelect make(String tag)
  {
    return (BProtocolSelect)bacnet.getRange().get(tag);
  }
  
  /** Private constructor. */
  private BProtocolSelect(int ordinal)
  {
    super(ordinal);
  }
  
  public static final BProtocolSelect DEFAULT = bacnet;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProtocolSelect.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    // (ปล่อยว่างไว้รอ Slot-o-Matic สร้างไส้ใน)

}