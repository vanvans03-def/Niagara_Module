package com.c.myPoc;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Enhanced BACnet Utility
 * Support: Object List, COV, Segmentation
 */
public class BACnetUtil {

    // ==================== APDU Types ====================
    public static final byte TYPE_CONFIRMED_REQ = 0x00;
    public static final byte TYPE_UNCONFIRMED_REQ = 0x10;
    public static final byte TYPE_SIMPLE_ACK = 0x20;
    public static final byte TYPE_COMPLEX_ACK = 0x30;
    public static final byte TYPE_SEGMENT_ACK = 0x40;
    public static final byte TYPE_ERROR = 0x50;
    public static final byte TYPE_REJECT = 0x60;
    public static final byte TYPE_ABORT = 0x70;

    // ==================== Services ====================
    public static final byte SERVICE_READ_PROPERTY = 0x0C;
    public static final byte SERVICE_WRITE_PROPERTY = 0x0F;
    public static final byte SERVICE_WHO_IS = 0x08;
    public static final byte SERVICE_SUBSCRIBE_COV = 0x05;

    // ==================== Tags ====================
    public static final byte TAG_OBJECT_ID = 0x0C;
    public static final byte TAG_PROP_ID = 0x19;
    public static final byte TAG_INDEX = 0x29;
    public static final byte TAG_OPEN = 0x3E;
    public static final byte TAG_CLOSE = 0x3F;

    // ==================== Properties ====================
    public static final int PROP_PRESENT_VALUE = 85;
    public static final int PROP_OBJECT_LIST = 76;
    public static final int PROP_OBJECT_NAME = 77;
    public static final int PROP_OBJECT_TYPE = 79;
    public static final int PROP_SYSTEM_STATUS = 112;

    // ==================== 1. Object List Discovery ====================

    /**
     * อ่าน Object List แบบ Array Index (รองรับ Segmentation)
     *
     * @param deviceId BACnet Device ID
     * @param index Array Index (0 = count, 1-N = objects)
     * @param invokeId Invoke ID
     * @return Packet bytes
     */
    public static byte[] buildReadObjectListPacket(int deviceId, int index, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);

        // BVLC
        bb.put((byte) 0x81);
        bb.put((byte) 0x0A);
        bb.putShort((short) 0); // Length placeholder

        // NPDU
        bb.put((byte) 0x01); // Version
        bb.put((byte) 0x04); // Control (Expecting Reply)

        // APDU
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x08)); // Segmented Response Accepted
        bb.put((byte) 0xFF); // Max Segs = More than 64
        bb.put((byte) invokeId);
        bb.put(SERVICE_READ_PROPERTY);

        // Object ID (Device:deviceId)
        int objectIdPacked = (8 << 22) | (deviceId & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID);
        bb.putInt(objectIdPacked);

        // Property ID (Object List = 76)
        bb.put(TAG_PROP_ID);
        bb.put((byte) PROP_OBJECT_LIST);

        // Array Index (Tag 2)
        if (index >= 0) {
            bb.put((byte) 0x29); // Context Tag 2, Length 1
            bb.put((byte) index);
        }

        // Fill Length
        int len = bb.position();
        bb.putShort(2, (short) len);

        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    /**
     * Parse Object Count จาก Response
     */
    public static int parseObjectCount(byte[] data) {
        try {
            int offset = findPropertyValueOffset(data);
            if (offset < 0) return 0;

            // Application Tag 2 (Unsigned Int)
            if (data[offset] == 0x21) {
                return data[offset + 1] & 0xFF;
            } else if (data[offset] == 0x22) {
                return ((data[offset + 1] & 0xFF) << 8) | (data[offset + 2] & 0xFF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Parse Object ID จาก Response (Array Index > 0)
     */
    public static int parseObjectId(byte[] data) {
        try {
            int offset = findPropertyValueOffset(data);
            if (offset < 0) return -1;

            // Application Tag C (Object ID)
            if (data[offset] == (byte) 0xC4) {
                return ((data[offset + 1] & 0xFF) << 24) |
                        ((data[offset + 2] & 0xFF) << 16) |
                        ((data[offset + 3] & 0xFF) << 8) |
                        (data[offset + 4] & 0xFF);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ==================== 2. COV Subscription ====================

    /**
     * Subscribe COV (Change of Value)
     *
     * @param objectType Object Type (0=AI, 1=AO, etc)
     * @param instance Object Instance
     * @param processId Process ID (client identifier)
     * @param lifetime Subscription lifetime (seconds, 0 = cancel)
     * @param invokeId Invoke ID
     * @return Packet bytes
     */
    public static byte[] buildSubscribeCOVPacket(
            int objectType,
            int instance,
            int processId,
            int lifetime,
            int invokeId
    ) {
        ByteBuffer bb = ByteBuffer.allocate(1024);

        // BVLC
        bb.put((byte) 0x81);
        bb.put((byte) 0x0A);
        bb.putShort((short) 0);

        // NPDU
        bb.put((byte) 0x01);
        bb.put((byte) 0x04);

        // APDU (Confirmed Request)
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02));
        bb.put((byte) 0x05);
        bb.put((byte) invokeId);
        bb.put(SERVICE_SUBSCRIBE_COV);

        // Process ID (Tag 0)
        bb.put((byte) 0x09); // Context 0, Length 1
        bb.put((byte) processId);

        // Monitored Object ID (Tag 1)
        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put((byte) 0x1C); // Context 1, Length 4
        bb.putInt(objectIdPacked);

        // Confirmed Notifications (Tag 2) - Optional (ใส่เป็น true)
        bb.put((byte) 0x29); // Context 2, Length 1
        bb.put((byte) 0x01); // True

        // Lifetime (Tag 3) - 0 = Cancel subscription
        bb.put((byte) 0x39); // Context 3, Length 1
        bb.put((byte) lifetime);

        // Fill Length
        int len = bb.position();
        bb.putShort(2, (short) len);

        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    /**
     * Parse COV Notification (Unconfirmed)
     *
     * @return Map with keys: objectType, instance, propertyId, value
     */
    public static Map<String, Object> parseCOVNotification(byte[] data) {
        Map<String, Object> result = new HashMap<>();

        try {
            // Skip BVLC (4) + NPDU (2+)
            int offset = 6;

            // Check NPDU Control
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                // Has DNET/DLEN/DADR
                offset += 2; // DNET
                int dlen = data[offset++] & 0xFF;
                offset += dlen;
                offset++; // Hop count
            }

            // Check APDU Type (should be Unconfirmed Request 0x10)
            if ((data[offset] & 0xF0) != TYPE_UNCONFIRMED_REQ) {
                return result;
            }

            offset++; // Skip APDU Type
            byte serviceChoice = data[offset++];

            // Should be UnconfirmedCOVNotification (0x02)
            if (serviceChoice != 0x02) {
                return result;
            }

            // Parse fields (simplified - assumes standard format)
            // Tag 0: Process ID
            offset += 2;

            // Tag 1: Initiating Device
            offset += 5;

            // Tag 2: Monitored Object
            if (data[offset] == 0x2C) { // Context 2, Length 4
                offset++;
                int objId = ((data[offset] & 0xFF) << 24) |
                        ((data[offset + 1] & 0xFF) << 16) |
                        ((data[offset + 2] & 0xFF) << 8) |
                        (data[offset + 3] & 0xFF);

                result.put("objectType", (objId >> 22) & 0x3FF);
                result.put("instance", objId & 0x3FFFFF);
                offset += 4;
            }

            // Tag 3: Time Remaining (skip)
            offset += 2;

            // Tag 4: List of Values (Opening Tag)
            if (data[offset] == 0x4E) {
                offset++;

                // Property ID
                if (data[offset] == 0x09) {
                    offset++;
                    int propId = data[offset++] & 0xFF;
                    result.put("propertyId", propId);
                }

                // Value (Opening Tag 2)
                if (data[offset] == 0x2E) {
                    offset++;

                    // Parse value based on application tag
                    byte tag = data[offset++];

                    if (tag == 0x44) { // Real (Float)
                        int bits = ((data[offset] & 0xFF) << 24) |
                                ((data[offset + 1] & 0xFF) << 16) |
                                ((data[offset + 2] & 0xFF) << 8) |
                                (data[offset + 3] & 0xFF);
                        result.put("value", Float.intBitsToFloat(bits));
                    }
                    else if (tag == 0x91 || tag == 0x21) { // Boolean or Enum
                        result.put("value", (data[offset] & 0xFF));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ==================== 3. Segmentation Support ====================

    /**
     * ตรวจสอบว่า Response เป็น Segmented หรือไม่
     */
    public static boolean isSegmentedResponse(byte[] data) {
        try {
            // Skip BVLC (4) + NPDU
            int offset = 6;
            byte npduControl = data[4];

            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }

            // Check APDU Type & Segmented bit
            byte apduType = data[offset];
            return ((apduType & 0x08) != 0); // Bit 3 = Segmented

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * ดึง Sequence Number จาก Segmented Response
     */
    public static int getSegmentSequence(byte[] data) {
        try {
            int offset = 6;
            byte npduControl = data[4];

            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }

            offset++; // Skip APDU Type
            offset++; // Skip Max Segs/Response

            return data[offset] & 0xFF; // Sequence Number

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * สร้าง Segment ACK Packet
     */
    public static byte[] buildSegmentACK(int sequenceNumber, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(32);

        // BVLC
        bb.put((byte) 0x81);
        bb.put((byte) 0x0A);
        bb.putShort((short) 0);

        // NPDU
        bb.put((byte) 0x01);
        bb.put((byte) 0x00); // No reply expected

        // APDU (Segment ACK)
        bb.put(TYPE_SEGMENT_ACK);
        bb.put((byte) 0x00); // Negative ACK = 0, Server = 0
        bb.put((byte) invokeId);
        bb.put((byte) sequenceNumber);
        bb.put((byte) 0x00); // Actual Window Size

        int len = bb.position();
        bb.putShort(2, (short) len);

        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    /**
     * รวม Segmented Data
     */
    public static class SegmentAssembler {
        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private int expectedSeq = 0;
        private boolean moreFollows = true;

        public boolean addSegment(byte[] data) {
            try {
                int seq = getSegmentSequence(data);
                if (seq != expectedSeq) {
                    System.err.println("Sequence mismatch: expected " + expectedSeq + ", got " + seq);
                    return false;
                }

                // Extract segment data
                int offset = findSegmentDataOffset(data);
                if (offset < 0) return false;

                // Check if more segments follow
                int apduOffset = 6;
                byte npduControl = data[4];
                if ((npduControl & 0x20) != 0) {
                    apduOffset += 2;
                    int dlen = data[apduOffset++] & 0xFF;
                    apduOffset += dlen + 1;
                }

                byte apduType = data[apduOffset];
                moreFollows = ((apduType & 0x04) != 0); // Bit 2 = More Follows

                // Write segment data
                buffer.write(data, offset, data.length - offset);

                expectedSeq++;
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public boolean isComplete() {
            return !moreFollows;
        }

        public byte[] getData() {
            return buffer.toByteArray();
        }
    }

    // ==================== Helper Methods ====================

    /**
     * หา Offset ของ Property Value
     */
    private static int findPropertyValueOffset(byte[] data) {
        try {
            int offset = 6; // Skip BVLC

            // Skip NPDU
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen;
                offset++;
            }

            // Skip APDU Header
            byte apduType = data[offset++];
            if ((apduType & 0xF0) == TYPE_COMPLEX_ACK) {
                offset++; // Invoke ID
                offset++; // Service ACK Choice
            }

            // Skip Object ID (Tag 0xC)
            if (data[offset] == TAG_OBJECT_ID) {
                offset += 5;
            }

            // Skip Property ID (Tag 0x19)
            if (data[offset] == TAG_PROP_ID) {
                offset += 2;
            }

            // Skip Array Index if present
            if (data[offset] == TAG_INDEX) {
                offset += 2;
            }

            // Opening Tag 3
            if (data[offset] == TAG_OPEN) {
                offset++;
            }

            return offset;

        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * หา Offset ของ Segment Data
     */
    private static int findSegmentDataOffset(byte[] data) {
        try {
            int offset = 6;

            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }

            offset++; // APDU Type
            offset++; // Max Segs
            offset++; // Sequence
            offset++; // Window

            return offset;

        } catch (Exception e) {
            return -1;
        }
    }

    // ==================== Existing Methods ====================

    public static byte[] buildReadPropertyPacket(int objectType, int instance, int propertyId, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81);
        bb.put((byte) 0x0A);
        bb.putShort((short) 0);
        bb.put((byte) 0x01);
        bb.put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02));
        bb.put((byte) 0x05);
        bb.put((byte) invokeId);
        bb.put(SERVICE_READ_PROPERTY);

        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID);
        bb.putInt(objectIdPacked);
        bb.put(TAG_PROP_ID);
        bb.put((byte) propertyId);

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    public static byte[] buildWritePropertyReal(int objectType, int instance, int propertyId, float value, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short)0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_WRITE_PROPERTY);

        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);
        bb.put(TAG_PROP_ID).put((byte) propertyId);
        bb.put(TAG_OPEN);
        bb.put((byte) 0x44);
        bb.putFloat(value);
        bb.put(TAG_CLOSE);

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    public static String getObjectTypeName(int rawId) {
        int type = (rawId >> 22) & 0x3FF;
        switch(type) {
            case 0: return "AI";
            case 1: return "AO";
            case 2: return "AV";
            case 3: return "BI";
            case 4: return "BO";
            case 5: return "BV";
            case 8: return "Device";
            default: return "ObjType" + type;
        }
    }

    public static int getInstance(int rawId) {
        return rawId & 0x3FFFFF;
    }
}