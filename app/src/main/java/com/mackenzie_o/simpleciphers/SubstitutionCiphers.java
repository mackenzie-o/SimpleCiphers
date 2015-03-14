package com.mackenzie_o.simpleciphers;

/**
 * Created by MacKenzie_2 on 3/9/2015.
 */

//TODO: rename this class to something more appropriate, perhaps organizing the ciphers into categories
public class SubstitutionCiphers {
    private static char [] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static char [] UPPER_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
    private static char NULLCHAR = '\u0000';
    
    public static String keyword(String message, String key, 
                                 boolean persistCaps, boolean keepCharacters, boolean encode){
        String out = "";
        char [] table = createTable(key.toLowerCase());
        if(!persistCaps) message = message.toLowerCase();
        for(int i=0; i<message.length(); i++){
            out += getChar(message.charAt(i), table, keepCharacters, encode);
        }
        return out;
    };
    
    private static String getChar(char in, char[] table, boolean keepCharacters, boolean encode){
        boolean isCapitalized = isCapitalized(in);
        char loweredIn = (""+in).toLowerCase().charAt(0);
        int index = getIndex(loweredIn, encode, table);
        if(index == -1){
            if(keepCharacters) return in+"";
            else return "";
        }else if(encode){
            if(isCapitalized) return (table[index]+"").toUpperCase();
            else return table[index]+"";
        }else{
            if(isCapitalized) return UPPER_ALPHABET[index]+"";
            else return ALPHABET[index]+"";
        }
    };
    
    private static int getIndex(char in, boolean encode, char[] table){
        if (encode){
            for(int i=0; i<26; i++){
                if(ALPHABET[i]==in) return i;
            }
            return -1;
        }
        else{
            for(int i=0; i<26; i++){
                if(table[i]==in) return i;
            }
            return -1;
        }
    }
    
    private static boolean isCapitalized(char in){
        String input = in+"";
        if (input.toLowerCase() != input){
            return true;
        }
        return false;
    }
    
    private static char[] createTable(String key){
        char [] table = new char [26];
        char [] alpha = ALPHABET.clone();
        for(int i=0; i<key.length(); i++){
            table[i] = key.charAt(i);
            for(int j=0; j<26; j++){
                if(alpha[j] == table[i]){
                    alpha[j] = NULLCHAR;
                    break;
                }
            }
        }
        int index = key.length();
        for(int i=0; i<26; i++){
            if(alpha[i] != NULLCHAR){
                table[index] = alpha[i];
                index++;
            }
        }
        return table;
    };
}
