package com.mackenzie_o.simpleciphers.ciphers;

import java.lang.Character;
/**
 * Ciphers is a superclass of the specific cipher classes
 * containing utility functions and constants.
 */
public class Ciphers {
    public static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] UPPER_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    public static final char NULLCHAR = '\u0000';

    // gets index in the alphabet of the given character
    public static int findIndex(char in) {
        in = lowerChar(in);
        for (int i = 0; i < 26; i++) {
            if (ALPHABET[i] == in) return i;
        }
        return -1;
    }

    // returns a copy of the given string with all non-alphabetic characters removed
    public static String removeNonalphabeticChars(String in) {
        // TODO: maybe nonalphanumeric? maybe ignore spaces?
        return in.replaceAll("[^A-Za-z]", "");
    }

    // lazy method wrapper
    public static char lowerChar(char in) {
        // returns the lowercase equivalent of the character, if any; otherwise, the character itself.
        return Character.toLowerCase(in);
    }

    // lazy wrapper method
    public static boolean isCapitalized(char in) {
        return Character.isUpperCase(in);
    }

    // lazy wrapper method
    public static String charToString(char in) {
        return String.valueOf(in);
    }


}
