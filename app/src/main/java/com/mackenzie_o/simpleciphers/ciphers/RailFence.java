package com.mackenzie_o.simpleciphers.ciphers;

/**
 *  The RailFence class implements the rail fence (aka zigzag) transposition cipher.
 *
 *  The rail fence cipher encodes messages by taking a message and writing each letter
 *  on a "rail" or line. For example "FenceDiagram" written on three rails would look
 *  like this:
 *  F ~ ~ ~ e ~ ~ ~ g ~ ~ ~
 *  ~ e ~ c ~ D ~ a ~ r ~ m
 *  ~ ~ n ~ ~ ~ i ~ ~ ~ a ~
 *  The message is then read from left to right, top to bottom, resulting in the cipher
 *  text "FegecDarmnia".
 */
public class RailFence extends Ciphers{
    String plaintext;
    String ciphertext;
    int rails;
    char[][] fence;

    // Creates a rail fence object with the fence initalized from either the cipher or plain text
    // depending on encode flag.
    public RailFence(String message, int key, boolean persistCaps, boolean keepCharacters, boolean encode) {
        if (!persistCaps) {
            message = message.toLowerCase();
        }
        if (!keepCharacters) {
            message = removeNonalphabeticChars(message);
        }
        this.rails = key;
        if (encode) {
            this.plaintext = message;
            buildFenceEncode();
        } else {
            this.ciphertext = message;
            buildFenceDecode();
        }

    }

    // returns a string representing the fence with ~ representing a null character.
    public String railFenceDiagram() {
        String out = "";
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < fence[i].length; j++) {
                if (fence[i][j] != NULLCHAR) out += fence[i][j] + " ";
                else out += "~ ";
            }
            out += "\n";
        }
        return out;
    }

    // Creates a fence array
    private void buildFence(int length, String text) {
        fence = new char[rails][length];
        char[] m = text.toCharArray();
        int row = 0;
        boolean down = true;

        for (int col = 0; col < length; col++) {
            fence[row][col] = m[col];
            if (down) {
                if (row < rails - 1) {
                    row++;
                } else {
                    down = false;
                    row--;
                }
            } else {
                if (row > 0) {
                    row--;
                } else {
                    down = true;
                    row++;
                }
            }
        }
    }

    // builds a fence from the plaintext
    private void buildFenceEncode() {
        buildFence(plaintext.length(), plaintext);
    }

    // builds a fence from the cipher text by building a plaintext fence of the same length
    // with placeholder characters, and then replacing those characters with the ciphertext
    private void buildFenceDecode() {
        String placeholder = "";
        char phChar = 'X';
        for (int i = 0; i < ciphertext.length(); i++) {
            placeholder += phChar;
        }
        buildFence(ciphertext.length(), placeholder);

        char[] m = ciphertext.toCharArray();
        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < ciphertext.length(); j++) {
                if (fence[i][j] == phChar) {
                    fence[i][j] = m[index];
                    index++;
                }
            }
        }
    }

    // returns the plaintext of the fence by reading top to bottom, left to right
    public String getPlaintext() {
        if (plaintext != null) {
            return plaintext;
        }
        plaintext = "";
        for (int j = 0; j < fence[0].length; j++) {
            for (int i = 0; i < rails; i++) {
                if (fence[i][j] != NULLCHAR) plaintext += fence[i][j];
            }
        }
        return plaintext;
    }

    // returns the ciphertext of the fence by reading left to right, top to bottom
    public String getCiphertext() {
        if (ciphertext != null) {
            return ciphertext;
        }

        ciphertext = "";
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < fence[i].length; j++) {
                if (fence[i][j] != NULLCHAR) ciphertext += fence[i][j];
            }
        }
        return ciphertext;
    }

    // prints out the fence to standard out for debugging
    public void printFence() {
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < fence[i].length; j++) {
                if (fence[i][j] != NULLCHAR) System.out.print(fence[i][j] + " ");
                else System.out.print("` ");
            }
            System.out.println();
        }
    }
}
