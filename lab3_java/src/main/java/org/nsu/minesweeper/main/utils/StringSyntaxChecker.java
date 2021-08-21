package org.nsu.minesweeper.main.utils;

public class StringSyntaxChecker {
     public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
