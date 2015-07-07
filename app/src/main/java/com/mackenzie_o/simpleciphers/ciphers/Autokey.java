package com.mackenzie_o.simpleciphers.ciphers;

/**
 * The Autokey class implements the autokey polyalphabetic substitution cipher.
 *
 * The autokey cipher is a vigenere cipher where the body of the message is
 * appended to the key.
 */
public class Autokey extends Vigenere {

    // applies a autokey shift on the provided string with the given options and returns the result
    public static String autokeyShift(String message, String key,
                                      boolean persistCaps, boolean keepCharacters, boolean encode) {
        if (encode) {
            return vigenereShift(message, key + message, persistCaps, keepCharacters, encode);
        } else {
            String newMessage = "";
            int shiftKey[] = new int[(message + key).length()];
            int index = key.length();

            for (int i = 0; i < index; i++) {
                shiftKey[i] = findIndex(key.charAt(i));
            }

            String cur;

            for (int i = 0; i < message.length(); i++) {
                cur = getChar(message.charAt(i), shiftKey[i], persistCaps, keepCharacters, encode);
                newMessage += cur;
                shiftKey[index] = findIndex(cur.charAt(0));
                index++;
            }
            return newMessage;
        }
    }
}
