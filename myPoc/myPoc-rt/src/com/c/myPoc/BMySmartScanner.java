package com.c.myPoc;

import javax.baja.sys.*;
import javax.baja.naming.*;
import javax.baja.collection.*;
import javax.baja.nre.annotations.*;

@NiagaraType
@NiagaraProperty(
        name = "jsonOutput",
        type = "BString",
        defaultValue = "[]",
        flags = Flags.SUMMARY | Flags.READONLY
)
@NiagaraProperty(
        name = "myQuery",
        type = "BString",
        // ใส่ Default query ไว้ให้ดูเป็นตัวอย่าง
        defaultValue = "station:|slot:/|bql:select name, out.value, slotPath from baja:Numeric",
        flags = Flags.SUMMARY // ลบ ReadOnly ออก เพื่อให้เราแก้ค่าได้
)
@NiagaraAction(
        name = "scanPoints"
)
public class BMySmartScanner extends BComponent {

    
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.c.myPoc.BMySmartScanner(2036407634)1.0$ @*/
/* Generated Tue Dec 09 17:57:58 ICT 2025 by Slot-o-Matic (c) Tridium, Inc. 2012 */

////////////////////////////////////////////////////////////////
// Property "jsonOutput"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code jsonOutput} property.
   * @see #getJsonOutput
   * @see #setJsonOutput
   */
  public static final Property jsonOutput = newProperty(Flags.SUMMARY | Flags.READONLY, "[]", null);
  
  /**
   * Get the {@code jsonOutput} property.
   * @see #jsonOutput
   */
  public String getJsonOutput() { return getString(jsonOutput); }
  
  /**
   * Set the {@code jsonOutput} property.
   * @see #jsonOutput
   */
  public void setJsonOutput(String v) { setString(jsonOutput, v, null); }

////////////////////////////////////////////////////////////////
// Property "myQuery"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code myQuery} property.
   * @see #getMyQuery
   * @see #setMyQuery
   */
  public static final Property myQuery = newProperty(Flags.SUMMARY, "station:|slot:/|bql:select name, out.value, slotPath from baja:Numeric", null);
  
  /**
   * Get the {@code myQuery} property.
   * @see #myQuery
   */
  public String getMyQuery() { return getString(myQuery); }
  
  /**
   * Set the {@code myQuery} property.
   * @see #myQuery
   */
  public void setMyQuery(String v) { setString(myQuery, v, null); }

////////////////////////////////////////////////////////////////
// Action "scanPoints"
////////////////////////////////////////////////////////////////
  
  /**
   * Slot for the {@code scanPoints} action.
   * @see #scanPoints()
   */
  public static final Action scanPoints = newAction(0, null);
  
  /**
   * Invoke the {@code scanPoints} action.
   * @see #scanPoints
   */
  public void scanPoints() { invoke(scanPoints, null, null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMySmartScanner.class);

/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

    public void doScanPoints() {
        try {
            // 1. อ่าน Query ที่คุณกรอกหน้าเว็บมาใช้ดื้อๆ เลย
            String rawQuery = getMyQuery();

            // กันเหนียว: ถ้าช่องว่าง ให้เตือนหน่อย
            if(rawQuery == null || rawQuery.trim().isEmpty()) {
                setJsonOutput("[{\"error\": \"Please enter a valid BQL query in 'myQuery' slot\"}]");
                return;
            }

            // 2. รัน Query
            BOrd ord = BOrd.make(rawQuery);
            BITable result = (BITable) ord.resolve(this, null).get();

            // 3. เตรียม JSON Builder
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("[");

            // ดึง Column ทั้งหมดออกมา (Dynamic Column)
            // ข้อดี: คุณจะ select กี่ตัวก็ได้ โค้ดนี้รองรับหมด ไม่ต้องแก้ Java
            Column[] columns = result.getColumns().list();

            TableCursor cursor = result.cursor();
            boolean firstRow = true;

            while (cursor.next()) {
                if (!firstRow) jsonBuilder.append(",");

                jsonBuilder.append("{");

                // วนลูปทุก Column ที่ Select มา แล้วสร้าง JSON Key-Value
                for (int i = 0; i < columns.length; i++) {
                    if(i > 0) jsonBuilder.append(",");

                    String colName = columns[i].getName(); // ชื่อ field (เช่น name, value)
                    String colVal = cursor.cell(columns[i]).toString(); // ค่าข้อมูล

                    // สร้าง "key": "value"
                    jsonBuilder.append("\"").append(colName).append("\":");
                    jsonBuilder.append("\"").append(colVal).append("\"");
                }

                jsonBuilder.append("}");
                firstRow = false;
            }

            jsonBuilder.append("]");

            // 4. ส่งออก
            setJsonOutput(jsonBuilder.toString());
            System.out.println("Custom Query Executed!");

        } catch (Exception e) {
            e.printStackTrace();
            // ส่ง Error กลับไปโชว์ใน JSON ด้วย จะได้รู้ว่า BQL ผิดตรงไหน
            setJsonOutput("[{\"error\": \"Query Failed: " + e.getMessage() + "\"}]");
        }
    }

    @Override
    public void started() throws Exception {
        super.started();
    }
}