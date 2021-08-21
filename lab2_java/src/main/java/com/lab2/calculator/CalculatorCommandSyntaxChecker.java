package com.lab2.calculator;

import java.util.HashMap;

public class CalculatorCommandSyntaxChecker implements CalculatorCommandSyntaxCheckerInterface {
    public boolean isVariableNameCorrect(String variableName) {
        return variableName.matches("^[a-zA-z].*");
    }

    public boolean isDouble(String arg) {
        return arg.matches("[+-]?([0-9]*[.,])?[0-9]+");
    }

    public boolean isCommandFound(String[] lineParts, HashMap<String, String> commands) {
        return commands.containsKey(lineParts[0].toUpperCase());
    }
}
