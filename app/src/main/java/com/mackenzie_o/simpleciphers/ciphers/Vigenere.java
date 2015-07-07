package com.mackenzie_o.simpleciphers.ciphers;

/**
 * The Vigenere class implements the vigenere polyalphabetic substitution cipher.
 *
 * The vigenere shift applies a series of caesar shifts based off of a keyword.
 * The position of each letter in the alphabet is used as the number of the shift,
 * and each letter in the plain text is shifted by the corresponding letter of the
 * keyword. For example, with a three letter key, the 1st, 4th, 7th, ect plaintext
 * encoded by the 1st letter of the key, the 2nd, 5th, 8th, ect letter is encoded
 * by the 2nd letter of the key and so on.
 */
public class Vigenere extends Caesar {

    // applies a vigenere shift on the provided string with the given options and returns the result
    public static String vigenereShift(String message, String key, boolean persistCaps, boolean keepCharacters, boolean encode) {
        String newMessage = "";
        int keyLength = key.length();
        int shiftKey[] = new int[keyLength];
        int index = 0;

        for (int i = 0; i < keyLength; i++) {
            shiftKey[i] = findIndex(key.charAt(i));
        }

        for (int i = 0; i < message.length(); i++) {
            newMessage += getChar(message.charAt(i), shiftKey[index], persistCaps, keepCharacters, encode);
            index = (index + 1) % keyLength;
        }
        return newMessage;
    }

}
