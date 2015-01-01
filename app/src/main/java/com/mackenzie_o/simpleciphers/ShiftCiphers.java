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
        for(int i=0; i<26; i++){
            if(UPPER_ALPHABET[i]==in) return true;
        }
        return false;
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
        return vigenereShift(message, key+message, persistCaps, keepCharacters, encode);
    }
    
}
