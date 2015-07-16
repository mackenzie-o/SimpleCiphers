package com.mackenzie_o.simpleciphers.ciphers;

/**
 * TODO: comment
 */
class RailFence extends Ciphers{
    String plaintext;
    String ciphertext;
    int rails;
    char[][] fence;

    public RailFence(String message, int key, boolean keepCharacters, boolean encode) {
        this.rails = key;
        if (encode) {
            this.plaintext = message;
            buildFenceEncode();
        } else {
            this.ciphertext = message;
            buildFenceDecode();
        }

    }

    public String railFenceDiagram() {
        String out = "";
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < fence[i].length; j++) {
                if (fence[i][j] != NULLCHAR) out += fence[i][j] + " ";
                else out += "` ";
            }
            out += "\n";
        }
        return out;
    }

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

    private void buildFenceEncode() {
        buildFence(plaintext.length(), plaintext);
    }

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
