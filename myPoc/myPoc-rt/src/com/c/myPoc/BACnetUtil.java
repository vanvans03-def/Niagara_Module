package com.c.myPoc;

import java.io.*;
import java.nio.ByteBuffer;

public class BACnetUtil {

    // APDU Types
    public static final byte TYPE_CONFIRMED_REQ = 0x00;
    public static final byte TYPE_COMPLEX_ACK = 0x30;

    // Services
    public static final byte SERVICE_READ_PROPERTY = 0x0C;
    public static final byte SERVICE_WRITE_PROPERTY = 0x0F;
    public static final byte SERVICE_WHO_IS = 0x08;

    // Tags
    public static final byte TAG_OBJECT_ID = 0x0C; // Context 0, Len 4
    public static final byte TAG_PROP_ID = 0x19;   // Context 1, Len 1
    public static final byte TAG_INDEX = 0x29;     // Context 2, Len 1
    public static final byte TAG_OPEN = 0x3E;      // Opening Tag (Context 3)
    public static final byte TAG_CLOSE = 0x3F;     // Closing Tag (Context 3)

    // Standard Properties
    public static final int PROP_PRESENT_VALUE = 85;
    public static final int PROP_OBJECT_LIST = 76;
    public static final int PROP_OBJECT_NAME = 77;

    /**
     * สร้าง BVLC + NPDU + APDU Header สำหรับ Confirmed Request
     */
    public static byte[] buildReadPropertyPacket(int objectType, int instance, int propertyId, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);

        // 1. BVLC (BACnet/IP)
        bb.put((byte) 0x81);
        bb.put((byte) 0x0A); // Function: Unicast
        bb.putShort((short) 0); // Length placeholder (index 2,3)

        // 2. NPDU
        bb.put((byte) 0x01); // Version
        bb.put((byte) 0x04); // Control (Expecting Reply)

        // 3. APDU (Confirmed Request)
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)); // Seg=0, More=0, SA=1 (Segmented Accepted)
        bb.put((byte) 0x05); // Max Segs
        bb.put((byte) invokeId);
        bb.put(SERVICE_READ_PROPERTY);

        // 4. Service Request Data
        // Object ID (Tag 0)
        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID);
        bb.putInt(objectIdPacked);

        // Property ID (Tag 1)
        bb.put(TAG_PROP_ID);
        bb.put((byte) propertyId);

        // Fill Length
        int len = bb.position();
        bb.putShort(2, (short) len);

        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    /**
     * สร้าง Write Property Packet (รองรับเฉพาะ Real/Float ก่อนสำหรับ PoC)
     */
    public static byte[] buildWritePropertyReal(int objectType, int instance, int propertyId, float value, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);

        // BVLC & NPDU (เหมือนเดิม)
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short)0);
        bb.put((byte) 0x01).put((byte) 0x04);

        // APDU (Write Property)
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_WRITE_PROPERTY);

        // Object ID
        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);

        // Property ID
        bb.put(TAG_PROP_ID).put((byte) propertyId);

        // Value (Constructed Tag 3)
        bb.put(TAG_OPEN); // Opening Tag 3
        bb.put((byte) 0x44); // Application Tag 4 (Real)
        bb.putFloat(value);
        bb.put(TAG_CLOSE); // Closing Tag 3

        // Fill Length
        int len = bb.position();
        bb.putShort(2, (short) len);

        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    /**
     * Parse Object ID จาก int (32-bit)
     */
    public static String getObjectTypeName(int rawId) {
        int type = (rawId >> 22) & 0x3FF;
        switch(type) {
            case 0: return "AI"; // Analog Input
            case 1: return "AO"; // Analog Output
            case 2: return "AV"; // Analog Value
            case 3: return "BI"; // Binary Input
            case 4: return "BO"; // Binary Output
            case 5: return "BV"; // Binary Value
            case 8: return "Device";
            default: return "ObjType" + type;
        }
    }

    public static int getInstance(int rawId) {
        return rawId & 0x3FFFFF;
    }
}