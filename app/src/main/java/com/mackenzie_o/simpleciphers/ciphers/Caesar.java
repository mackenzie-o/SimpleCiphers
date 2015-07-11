package com.mackenzie_o.simpleciphers.ciphers;

/**
 * The Caesar class implements the caesar shift substitution cipher.
 *
 * The caesar (aka shift) cipher encodes messages by shifting all letters
 * in the plain text by an integer. A caesar shift of 1 encodes all 'a' to
 * 'b', 'b' to 'c', ect.
 */
public class Caesar extends Ciphers {

    // gets the resulting index after applying an encoding or decoding shift
    private static int getNewIndex(int startIndex, int shift, boolean encode) {
        int endIndex;
        if (!encode) shift *= -1;
        endIndex = (startIndex + shift) % 26;
        if (endIndex < 0) endIndex += 26;
        return endIndex;
    }

    // gets the resulting character after applying a shift with the given options
    public static String getChar(char in, int shift, boolean persistCaps, boolean keepCharacters, boolean encode) {
        int startingPosition = findIndex(in);

        if (startingPosition == -1) { // char is non-alphabetic
            if (keepCharacters) return charToString(in);
            else return "";
        }
        if (persistCaps && isCapitalized(in)) {
            return charToString(UPPER_ALPHABET[getNewIndex(startingPosition, shift, encode)]);
        }
        return charToString(ALPHABET[getNewIndex(startingPosition, shift, encode)]);
    }

    // applies a caesar shift on the provided string with the given options and returns the result
    public static String caesarShift(String message, int shift, boolean persistCaps, boolean keepCharacters, boolean encode) {
        String newMessage = "";
        for (int i = 0; i < message.length(); i++) {
            newMessage += getChar(message.charAt(i), shift, persistCaps, keepCharacters, encode);
        }
        return newMessage;
    }
}
