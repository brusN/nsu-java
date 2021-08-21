package com.lab2.calculator;

import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.UnknownCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;

public class CalculatorCommandExecutor implements CalculatorCommandExecutorInterface {
    public void execute(FileInputStream inputStream, HashMap<String, String> commands) {
        Logger logger = LogManager.getLogger();
        Stack<Double> stack = new Stack<>();
        HashMap<String, Double> variables = new HashMap<>();
        CalculatorCommandFabric fabric = new CalculatorCommandFabric();
        CalculatorCommandSyntaxChecker syntaxChecker = new CalculatorCommandSyntaxChecker();
        int lineCounter = 1;
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line = fileReader.readLine();
            while (line != null) {
                String[] splitedLine = line.split(" ");
                if (syntaxChecker.isCommandFound(splitedLine, commands)) {
                    var currentOperation = fabric.createNewMathOperation(commands.get(splitedLine[0].toUpperCase()));
                    currentOperation.doWork(splitedLine, variables, stack);
                } else {
                    throw new UnknownCommandException("Command hasn't recognized");
                }
                line = fileReader.readLine();
                ++lineCounter;
            }
        } catch (CalculatorException ex) {
            logger.error("Error while calculating: " + ex.getLocalizedMessage() + " [Line #" + lineCounter + "]");
        } catch (Exception ex) {
            logger.error("Error while executing: " + ex.getLocalizedMessage() + " [Line #" + lineCounter + "]");
        }
    }
}
