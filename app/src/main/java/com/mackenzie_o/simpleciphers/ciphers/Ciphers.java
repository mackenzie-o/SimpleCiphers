package com.mackenzie_o.simpleciphers.ciphers;

/**
 * Ciphers is a superclass of the specific cipher classes
 * containing utility functions and constants.
 */
public class Ciphers {
    public static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] UPPER_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    public static final char NULLCHAR = '\u0000';

    // gets index in the alphabet of the given character
    // @note assumes that that character is lowercase
    public static int findIndex(char in) {
        for (int i = 0; i < 26; i++) {
            if (ALPHABET[i] == in) return i;
        }
        return -1;
    }

    // TODO: use a regex you scrub?
    public static boolean isCapitalized(char in) {
        String input = "" + in;
        if (input.toLowerCase().equals(input)) {
            return true;
        }
        return false;
    }

    // returns a copy of the given string with all non-alphabetic characters removed
    // TODO: use a regex you scrub
    public static String removeNonalphabeticChars(String in) {
        char[] input = in.toLowerCase().toCharArray();
        String out = "";
        for (int i = 0; i < input.length; i++) {
            if (findIndex(input[i]) != -1) out += input[i];
        }
        return out;
    }
}
