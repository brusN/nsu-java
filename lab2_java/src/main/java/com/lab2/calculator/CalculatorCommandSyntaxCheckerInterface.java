package com.lab2.calculator;

import java.util.HashMap;
import java.util.Locale;

public interface CalculatorCommandSyntaxCheckerInterface {
    boolean isVariableNameCorrect(String variableName);
    boolean isDouble(String arg);
    boolean isCommandFound(String[] lineParts, HashMap<String, String> commands);
}
