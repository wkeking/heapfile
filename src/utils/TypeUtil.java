package utils;

import config.TableConfig;

public class TypeUtil {
    public static int getTypeLen(String type) {
        int begin = type.indexOf (TableConfig.BRACKET_B);
        int after = type.indexOf (TableConfig.BRACKET_A);
        String s = type.substring (begin + 1, after);
        return Integer.parseInt (s);
    }

    public static String getType(String type) {
        int begin = type.indexOf (TableConfig.BRACKET_B);
        return type.substring (0, begin);
    }

    public static int bytesToInt(byte[] bytes) {
        return bytes[3] & 0xFF |
                (bytes[2] & 0xFF) << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[0] & 0xFF) << 24;
    }

    public static long bytesToLong(byte[] bytes) {
        return (0xff00000000000000L & ((long) bytes[0] << 56)) |
                (0x00ff000000000000L & ((long) bytes[1] << 48)) |
                (0x0000ff0000000000L & ((long) bytes[2] << 40)) |
                (0x000000ff00000000L & ((long) bytes[3] << 32)) |
                (0x00000000ff000000L & ((long) bytes[4] << 24)) |
                (0x0000000000ff0000L & ((long) bytes[5] << 16)) |
                (0x000000000000ff00L & ((long) bytes[6] << 8)) |
                (0x00000000000000ffL & (long) bytes[7]);
    }
}
