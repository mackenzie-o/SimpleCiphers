package com.mackenzie_o.simpleciphers.ciphers;

import java.math.BigInteger;

/**
 * The Affine class implements the affine monoalphabetic substitution cipher
 *
 * The affine cipher maps each character using the function (ax+b) mod 26.
 * a must be coprime with 26 to ensure that characters are decoded to the same
 * character. Therefore the valid choices for a are 1, 3, 5, 7, 9, 11, 15, 17,
 * 19, 21, 23, and 25.
 */
public class Affine extends Ciphers {

    // applies a affine cipher on the provided string with the given options and returns the result
    public static String affineShift(String message, int a, int b, boolean persistCaps, boolean keepCharacters, boolean encode) {
        // TODO: check that a is valid
        if (!persistCaps) message = message.toLowerCase();
        String out = "";
        for (int i = 0; i < message.length(); i++) {
            out += getAffineChar(message.charAt(i), a, b, keepCharacters, encode);
        }
        return out;
    }

    // gets a new character using a (ax+b)mod26
    public static String getAffineChar(char in, int a, int b,
                                       boolean keepCharacters, boolean encode) {
        boolean isCapitalized = isCapitalized(in);
        int index = findIndex(in);
        if (index == -1) {
            if (keepCharacters) return in + "";
            else return "";
        } else if (encode) {
            if (isCapitalized) return UPPER_ALPHABET[(a * index + b) % 26] + "";
            else return ALPHABET[(a * index + b) % 26] + "";
        } else { // TODO: fixed?
            int inverse = modMultiInverse(a);
            int newIndex = (inverse * (index - b)) % 26;
            if (newIndex < 0) {
                newIndex += 26;
            }
            if (isCapitalized) return UPPER_ALPHABET[newIndex] + "";
            else return ALPHABET[newIndex] + "";
        }
    }

    // gets the multiplicative inverse of an integer
    public static int modMultiInverse(int A) {
        BigInteger a = BigInteger.valueOf(A);
        BigInteger twentySix = BigInteger.valueOf(26);
        return (int) a.modInverse(twentySix).doubleValue();
    }

}
