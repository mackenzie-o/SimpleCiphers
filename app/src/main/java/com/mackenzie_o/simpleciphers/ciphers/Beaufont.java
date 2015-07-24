package com.mackenzie_o.simpleciphers.ciphers;

/**
 * TODO: comment
 */
public class Beaufont extends Ciphers{

    public static String beaufontCipher(String message, String keytext, boolean persistCaps, boolean keepCharacters){
        if (!persistCaps) {
            message.toLowerCase();
        }
        if (!keepCharacters) {
            message = removeNonalphabeticChars(message);
        }

        String out = "";
        char[] plain = message.toCharArray();
        char[] key = keytext.toCharArray();
        int keyLength = key.length;
        int index = 0;

        for (int i = 0; i< plain.length; i++) {
            if (findIndex(plain[i]) != -1) {
                out += getChar(plain[i], key[index]);
                index = (index + 1)%keyLength;
            } else if (keepCharacters) {
                out += plain[i];
            }

        }
        return out;
    }

    public static char getChar(char plain, char key) {
        int plainIndex = findIndex(plain);
        int keyIndex = findIndex(key);
        if (isCapitalized(plain)){
            return UPPER_ALPHABET[(keyIndex - plainIndex + 26) %26 ];
        }
        return ALPHABET[(keyIndex - plainIndex + 26) %26 ];
    }
}
