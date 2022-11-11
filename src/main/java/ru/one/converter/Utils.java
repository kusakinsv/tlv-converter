package ru.one.converter;

import java.util.Arrays;

public class Utils {
    public static int bytesToIntLE(byte[] bytes) {
        int i = bytes.length;
        int value = bytes[--i];
        while (--i >= 0) {
            value <<= 8;
            value |= bytes[i] & 0xFF;
        }
        return value;
    }

    public static double bytesToFVLN(byte[] bytes) {
        byte[] lengthBytes = new byte[1];
        lengthBytes[0] = bytes[0];
        int length = bytesToIntLE(lengthBytes);
        byte[] amountBytes = Arrays.copyOfRange(bytes, 1, bytes.length);
        int amount = bytesToIntLE(amountBytes);
        double x = Math.pow(10.0, length);
        return amount/x;
    }

    public static byte[] floatToByteArray(float value) {
        int intBits =  Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits) };
    }

}
