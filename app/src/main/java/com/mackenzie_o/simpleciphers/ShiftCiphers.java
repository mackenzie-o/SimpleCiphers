package com.mackenzie_o.simpleciphers;

/**
 * Created by MacKenzie O'Bleness on 12/30/2014.
 */
public class ShiftCiphers {
    private static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char [] UPPER_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    private static int findIndex(char in){
        for(int i=0; i<26; i++){
            if(ALPHABET[i]==in) return i;
        }
        return -1;
    }
    private static boolean isCapitalized(char in){
        String input = ""+in;
        if(input.toLowerCase() != input){
            return true;
        }
        return false;
    }
    public static String removeNonalphabeticChars(String in){
        char [] input = in.toLowerCase().toCharArray();
        String out = "";
        for(int i=0; i<input.length ; i++){
            if(findIndex(input[i]) != -1) out += input[i];
        }
        return out;
    } 
    private static int getNewIndex(int startIndex, int shift, boolean encode){
        int endIndex;
        if(!encode) shift*=-1;
        endIndex = (startIndex+shift)%26;
        if(endIndex < 0) endIndex+=26;
        return endIndex;
    }
    public static String getChar(char in, int shift,
                                 boolean persistCaps, boolean keepCharacters, boolean encode){
        int startingPosition = findIndex((""+in).toLowerCase().charAt(0));
        if(startingPosition == -1){
            if(keepCharacters) return ""+in;
            else return "";
        }


        if(persistCaps && isCapitalized(in)){
            return ""+UPPER_ALPHABET[getNewIndex(startingPosition, shift, encode)];
        }
        return ""+ALPHABET[getNewIndex(startingPosition, shift, encode)];
    }

    public static String caesarShift(String message, int shift,
                                     boolean persistCaps, boolean keepCharacters, boolean encode){
        String newMessage = "";
        for(int i=0; i<message.length(); i++){
            newMessage += getChar(message.charAt(i), shift, persistCaps, keepCharacters, encode);
        }
        return newMessage;
    }

    public static String vigenereShift(String message, String key,
                                       boolean persistCaps, boolean keepCharacters, boolean encode){
        String newMessage = "";
        int keyLength = key.length();
        int shiftKey[] = new int[key.length()];
        int index = 0;

        for(int i=0; i<keyLength; i++){
            shiftKey[i] = findIndex(key.charAt(i));
        }

        for(int i=0; i<message.length(); i++){
            newMessage += getChar(message.charAt(i), shiftKey[index], persistCaps, keepCharacters, encode);
            index = (index + 1) % keyLength;
        }
        return newMessage;
    }

    public static String autokeyShift(String message, String key,
                                      boolean persistCaps, boolean keepCharacters, boolean encode){
        if(encode) {
            return vigenereShift(message, key + message, persistCaps, keepCharacters, encode);
        }else {
            String newMessage = "";
            int shiftKey[] = new int[(message+key).length()];
            int index = key.length();
            
            for(int i=0; i<index; i++){
                shiftKey[i] = findIndex(key.charAt(i));
            }
            
            String cur;

            for(int i=0; i<message.length(); i++){
                cur = getChar(message.charAt(i), shiftKey[i], persistCaps, keepCharacters, encode);
                newMessage += cur;
                shiftKey[index] = findIndex(cur.charAt(0));
                index++;
            }
            return newMessage;
        }
    }

    public static String affineShift(String message, int a, int b,
                                      boolean persistCaps, boolean keepCharacters, boolean encode){
        if(!persistCaps) message = message.toLowerCase();
        String out = "";
        for(int i = 0; i < message.length(); i++){
            out += getAffineChar(message.charAt(i), a, b, keepCharacters, encode);
        }
        return out;
    }
    
    public static String getAffineChar(char in, int a, int b, 
                                       boolean keepCharacters, boolean encode){
        boolean isCapitalized = isCapitalized(in);
        int index = findIndex(in);
        if(index == -1){
            if (keepCharacters) return in+"";
            else return "";
        }else if(encode){
            if(isCapitalized) return UPPER_ALPHABET[(a*index + b)%26]+"";
            else return ALPHABET[(a*index + b)%26]+"";
        }else{ //TODO: FIX
            int inverse = modMultiInverse(a);
            int newIndex = (inverse*(index - b))%26;
            if(newIndex < 0){
                newIndex+=26;
            }
            if(isCapitalized) return UPPER_ALPHABET[newIndex]+"";
            else return ALPHABET[newIndex]+"";
        }
    }
    public static int modMultiInverse(int A) {
        int b = 26;
        int a = A, s = 1, t = 0, u = 0, v = 1;
        int r, q, newu, newv;
        while (b != 0) {
            r = a % b;
            q = a / b;
            a = b;
            b = r;
            newu = s - u * q;
            newv = t - v * q;
            s = u;
            t = v;
            u = newu;
            v = newv;
        }
        if (s < 0){
            return b+s;
        }
        return s;
    }
    
}
