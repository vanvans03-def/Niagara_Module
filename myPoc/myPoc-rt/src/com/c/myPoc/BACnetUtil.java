package com.c.myPoc;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Enhanced BACnet Utility
 * Support: Object List, COV, Segmentation, Write Priority
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
    public static final byte TAG_PRIORITY = 0x49; // ✅ เพิ่ม Tag Priority

    // ==================== Properties ====================
    public static final int PROP_PRESENT_VALUE = 85;
    public static final int PROP_OBJECT_LIST = 76;
    public static final int PROP_OBJECT_NAME = 77;
    public static final int PROP_OBJECT_TYPE = 79;
    public static final int PROP_SYSTEM_STATUS = 112;

    // ==================== 1. Object List Discovery ====================

    public static byte[] buildReadObjectListPacket(int deviceId, int index, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short) 0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02));
        bb.put((byte) 0xFF);
        bb.put((byte) invokeId);
        bb.put(SERVICE_READ_PROPERTY);

        int objectIdPacked = (8 << 22) | (deviceId & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);
        bb.put(TAG_PROP_ID).put((byte) PROP_OBJECT_LIST);

        if (index >= 0) {
            bb.put((byte) 0x29).put((byte) index);
        }

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    public static int parseObjectCount(byte[] data) {
        try {
            int offset = findPropertyValueOffset(data);
            if (offset < 0) return 0;
            if (data[offset] == 0x21) return data[offset + 1] & 0xFF;
            else if (data[offset] == 0x22) return ((data[offset + 1] & 0xFF) << 8) | (data[offset + 2] & 0xFF);
        } catch (Exception e) {}
        return 0;
    }

    public static int parseObjectId(byte[] data) {
        try {
            int offset = findPropertyValueOffset(data);
            if (offset < 0) return -1;
            if (data[offset] == (byte) 0xC4) {
                return ((data[offset + 1] & 0xFF) << 24) | ((data[offset + 2] & 0xFF) << 16) | ((data[offset + 3] & 0xFF) << 8) | (data[offset + 4] & 0xFF);
            }
        } catch (Exception e) {}
        return -1;
    }

    // ==================== 2. COV Subscription ====================

    public static byte[] buildSubscribeCOVPacket(int objectType, int instance, int processId, int lifetime, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short) 0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_SUBSCRIBE_COV);

        bb.put((byte) 0x09).put((byte) processId);
        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put((byte) 0x1C).putInt(objectIdPacked);
        bb.put((byte) 0x29).put((byte) 0x01);
        bb.put((byte) 0x39).put((byte) lifetime);

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    public static Map<String, Object> parseCOVNotification(byte[] data) {
        Map<String, Object> result = new HashMap<>();
        try {
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }

            if ((data[offset] & 0xF0) != TYPE_UNCONFIRMED_REQ) return result;
            offset++;
            byte serviceChoice = data[offset++];
            if (serviceChoice != 0x02) return result;

            offset += 2; // Process ID
            offset += 5; // Initiating Device

            if (data[offset] == 0x2C) {
                offset++;
                int objId = ((data[offset] & 0xFF) << 24) | ((data[offset + 1] & 0xFF) << 16) | ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);
                result.put("objectType", (objId >> 22) & 0x3FF);
                result.put("instance", objId & 0x3FFFFF);
                offset += 4;
            }
            offset += 2; // Time Remaining

            if (data[offset] == 0x4E) {
                offset++;
                if (data[offset] == 0x09) {
                    offset++;
                    result.put("propertyId", data[offset++] & 0xFF);
                }
                if (data[offset] == 0x2E) {
                    offset++;
                    byte tag = data[offset++];
                    if (tag == 0x44) {
                        int bits = ((data[offset] & 0xFF) << 24) | ((data[offset + 1] & 0xFF) << 16) | ((data[offset + 2] & 0xFF) << 8) | (data[offset + 3] & 0xFF);
                        result.put("value", Float.intBitsToFloat(bits));
                    } else if (tag == 0x91 || tag == 0x21) {
                        result.put("value", (data[offset] & 0xFF));
                    }
                }
            }
        } catch (Exception e) {}
        return result;
    }

    // ==================== 3. Segmentation Support ====================

    public static boolean isSegmentedResponse(byte[] data) {
        try {
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }
            return ((data[offset] & 0x08) != 0);
        } catch (Exception e) { return false; }
    }

    public static int getSegmentSequence(byte[] data) {
        try {
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }
            offset += 2;
            return data[offset] & 0xFF;
        } catch (Exception e) { return -1; }
    }

    public static byte[] buildSegmentACK(int sequenceNumber, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(32);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short) 0);
        bb.put((byte) 0x01).put((byte) 0x00);
        bb.put(TYPE_SEGMENT_ACK).put((byte) 0x00).put((byte) invokeId).put((byte) sequenceNumber).put((byte) 0x00);
        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    public static class SegmentAssembler {
        private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        private int expectedSeq = 0;
        private boolean moreFollows = true;

        public boolean addSegment(byte[] data) {
            try {
                int seq = getSegmentSequence(data);
                if (seq != expectedSeq) return false;

                int offset = findSegmentDataOffset(data);
                if (offset < 0) return false;

                int apduOffset = 6;
                byte npduControl = data[4];
                if ((npduControl & 0x20) != 0) {
                    apduOffset += 2;
                    int dlen = data[apduOffset++] & 0xFF;
                    apduOffset += dlen + 1;
                }
                moreFollows = ((data[apduOffset] & 0x04) != 0);

                buffer.write(data, offset, data.length - offset);
                expectedSeq++;
                return true;
            } catch (Exception e) { return false; }
        }

        public boolean isComplete() { return !moreFollows; }
        public byte[] getData() { return buffer.toByteArray(); }
    }

    // ==================== Helper Methods & Core Services ====================

    private static int findPropertyValueOffset(byte[] data) {
        try {
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }
            byte apduType = data[offset++];
            if ((apduType & 0xF0) == TYPE_COMPLEX_ACK) {
                offset += 2;
            }
            if (data[offset] == TAG_OBJECT_ID) offset += 5;
            if (data[offset] == TAG_PROP_ID) offset += 2;
            if (data[offset] == TAG_INDEX) offset += 2;
            if (data[offset] == TAG_OPEN) offset++;
            return offset;
        } catch (Exception e) { return -1; }
    }

    private static int findSegmentDataOffset(byte[] data) {
        try {
            int offset = 6;
            byte npduControl = data[4];
            if ((npduControl & 0x20) != 0) {
                offset += 2;
                int dlen = data[offset++] & 0xFF;
                offset += dlen + 1;
            }
            offset += 4; // APDU, MaxSegs, Seq, Win
            return offset;
        } catch (Exception e) { return -1; }
    }

    // -------------------------------------------------------------------------
    // ✅ Read Property Packet
    // -------------------------------------------------------------------------
    public static byte[] buildReadPropertyPacket(int objectType, int instance, int propertyId, int invokeId) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short) 0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_READ_PROPERTY);

        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);
        bb.put(TAG_PROP_ID).put((byte) propertyId);

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    // -------------------------------------------------------------------------
    // ✅ Write Property Real (with Priority)
    // -------------------------------------------------------------------------
    public static byte[] buildWritePropertyReal(int objectType, int instance, int propertyId, float value, int invokeId, int priority) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short)0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_WRITE_PROPERTY);

        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);
        bb.put(TAG_PROP_ID).put((byte) propertyId);

        // Value (Real)
        bb.put(TAG_OPEN);
        bb.put((byte) 0x44); // Tag Real
        bb.putFloat(value);
        bb.put(TAG_CLOSE);

        // Priority (Optional)
        if (priority >= 1 && priority <= 16) {
            bb.put(TAG_PRIORITY).put((byte) priority);
        }

        int len = bb.position();
        bb.putShort(2, (short) len);
        byte[] packet = new byte[len];
        bb.rewind();
        bb.get(packet);
        return packet;
    }

    // -------------------------------------------------------------------------
    // ✅ Write Property Boolean (with Priority)
    // -------------------------------------------------------------------------
    public static byte[] buildWritePropertyBoolean(int objectType, int instance, int propertyId, boolean value, int invokeId, int priority) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        bb.put((byte) 0x81).put((byte) 0x0A).putShort((short)0);
        bb.put((byte) 0x01).put((byte) 0x04);
        bb.put((byte) (TYPE_CONFIRMED_REQ | 0x02)).put((byte) 0x05).put((byte) invokeId).put(SERVICE_WRITE_PROPERTY);

        int objectIdPacked = (objectType << 22) | (instance & 0x3FFFFF);
        bb.put(TAG_OBJECT_ID).putInt(objectIdPacked);
        bb.put(TAG_PROP_ID).put((byte) propertyId);

        // Value (Boolean/Enum)
        bb.put(TAG_OPEN);
        bb.put((byte) 0x91); // Tag Enumerated (Standard for Binary PV)
        bb.put((byte) (value ? 1 : 0));
        bb.put(TAG_CLOSE);

        // Priority (Optional)
        if (priority >= 1 && priority <= 16) {
            bb.put(TAG_PRIORITY).put((byte) priority);
        }

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