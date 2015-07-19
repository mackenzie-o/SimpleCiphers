package com.mackenzie_o.simpleciphers.ciphers;

/**
 *  The Scytale class implements the scytale transposition cipher.
 *
 *  The scytale cipher is named after a tool used by ancient greeks. It was
 *  a cylinder of a particular diameter wrapped in leather. The message was written
 *  across the cylinder, and then the leather was unwrapped. For example the message
 *  "examplescytale" on a would look like this:
 *  |e|x|a|m|p|
 *  |l|e|s|c|y|
 *  |t|a|l|e|~|
 *  Resulting in a cipher text of eltxeaaslmcpy.
 */

public class Scytale extends Ciphers{
    char[][] rod;
    int width;
    int height;

    // Creates a scytale object with the rod initalized from either the cipher or plain text
    // depending on encode flag.
    public Scytale(String message, int width, boolean persistCaps, boolean keepCharacters, boolean encode){
        if (!persistCaps) {
            message = message.toLowerCase();
        }
        if (!keepCharacters) {
            message = removeNonalphabeticChars(message);
        }
        this.width = width;
        this.height = message.length()%width==0 ? message.length()/width :  message.length()/width+1;
        if (encode) buildRodEncode(message);
        else buildRodDecode(message);
    }

    // initializes the rod from the plaintext
    private void buildRodEncode(String message){
        rod = new char[height][width];
        int index = 0;
        char[] text = message.toCharArray();

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                rod[i][j] = index < text.length ? text[index] : NULLCHAR;
                index++;
            }
        }
    }

    // initializes the rod from the ciphertext
    private void buildRodDecode(String message) {
        rod = new char[height][width];
        int index = 0;
        char[] text = message.toCharArray();

        for(int j=0; j<width; j++){
            for(int i=0; i<height; i++){
                rod[i][j] = index < message.length() ? text[index] : NULLCHAR;
                index++;
            }
        }
    }

    // gets the plaintext from the scytale
    public String getPlaintext(){
        String out = "";
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                out += rod[i][j];
            }
        }
        return out;
    }

    // gets the ciphertext from the scytale
    public String getCiphertext(){
        String out = "";
        for(int j=0; j<width; j++){
            for(int i=0; i<height; i++){
                out += rod[i][j];
            }
        }
        return out;
    }

    // returns a diagram of the scytale
    public String rodDiagram(){
        String out = "";
        for(int i=0; i<height; i++){
            out += "|";
            for(int j=0; j<width; j++){
                out += rod[i][j] != NULLCHAR ? rod[i][j] : '~';
                out += "|";
            }
            out += "\n";
        }
        return out;
    }

}

