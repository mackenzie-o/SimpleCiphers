package com.mackenzie_o.simpleciphers.ciphers;

/**
 * The Keyword class implements the keyword monoalphabetic substitution cipher.
 *
 * The keyword cipher is encodes messages by creating a table based around a keyword.
 * The first n letters of the alphabet, where n is the length of the keyword, are
 * mapped to the keyword. The remaining letters of the alphabet are mapped to the
 * letters not used by the keyword.
 *
 * For example, the table created with the keyword 'cipher' would be:
 * [ a b c d e f g h i j k l m n o p q r s t u v w x y z ]
 * [ c i p h e r a b d f g j k l m n o q s t u v w x y z ]
 */
public class Keyword extends Ciphers {

    // applies a keyword cipher on the provided string with the given options and returns the result
    public static String keywordCipher(String message, String key, boolean persistCaps, boolean keepCharacters, boolean encode) {
        String out = "";
        char[] table = createTable(key.toLowerCase());
        if (!persistCaps) message = message.toLowerCase();
        for (int i = 0; i < message.length(); i++) {
            out += getChar(message.charAt(i), table, keepCharacters, encode);
        }
        return out;
    }

    // gets the corresponding character from the plaintext/ciphertext table
    private static String getChar(char in, char[] table, boolean keepCharacters, boolean encode) {
        boolean isCapitalized = isCapitalized(in);
        char loweredIn = lowerChar(in);
        int index = getIndex(loweredIn, encode, table);
        if (index == -1) {
            if (keepCharacters) return charToString(in);
            else return "";
        } else if (encode) {
            if (isCapitalized) return charToString(table[index]).toUpperCase();
            else return charToString(table[index]);
        } else {
            if (isCapitalized) return charToString(UPPER_ALPHABET[index]);
            else return charToString(ALPHABET[index]);
        }
    }

    // gets the index of the character in the cipher or plaintext tables
    private static int getIndex(char in, boolean encode, char[] table) {
        if (encode) {
            for (int i = 0; i < 26; i++) {
                if (ALPHABET[i] == in) return i;
            }
            return -1;
        } else {
            for (int i = 0; i < 26; i++) {
                if (table[i] == in) return i;
            }
            return -1;
        }
    }

    // creates a ciphertext table from a given key
    private static char[] createTable(String key) {
        char[] table = new char[26];
        char[] alpha = ALPHABET.clone();
        for (int i = 0; i < key.length(); i++) {
            table[i] = key.charAt(i);
            for (int j = 0; j < 26; j++) {
                if (alpha[j] == table[i]) {
                    alpha[j] = NULLCHAR;
                    break;
                }
            }
        }
        int index = key.length();
        for (int i = 0; i < 26; i++) {
            if (alpha[i] != NULLCHAR) {
                table[index] = alpha[i];
                index++;
            }
        }
        return table;
    }
}
