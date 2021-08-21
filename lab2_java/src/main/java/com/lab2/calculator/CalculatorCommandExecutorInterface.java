package com.lab2.calculator;

import java.io.FileInputStream;
import java.util.HashMap;

interface CalculatorCommandExecutorInterface {
    void execute(FileInputStream inputStream, HashMap<String, String> commands);
}
