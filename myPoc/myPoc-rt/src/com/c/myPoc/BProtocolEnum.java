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
public final class BProtocolEnum extends BFrozenEnum {
    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BProtocolEnum(995253214)1.0$ @*/
/* Generated Tue Jan 06 17:04:59 ICT 2026 by Slot-o-Matic (c) Tridium, Inc. 2012 */
  
  /** Ordinal value for bacnet. */
  public static final int BACNET = 0;
  /** Ordinal value for modbus. */
  public static final int MODBUS = 1;
  
  /** BProtocolEnum constant for bacnet. */
  public static final BProtocolEnum bacnet = new BProtocolEnum(BACNET);
  /** BProtocolEnum constant for modbus. */
  public static final BProtocolEnum modbus = new BProtocolEnum(MODBUS);
  
  /** Factory method with ordinal. */
  public static BProtocolEnum make(int ordinal)
  {
    return (BProtocolEnum)bacnet.getRange().get(ordinal, false);
  }
  
  /** Factory method with tag. */
  public static BProtocolEnum make(String tag)
  {
    return (BProtocolEnum)bacnet.getRange().get(tag);
  }
  
  /** Private constructor. */
  private BProtocolEnum(int ordinal)
  {
    super(ordinal);
  }
  
  public static final BProtocolEnum DEFAULT = bacnet;

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProtocolEnum.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}