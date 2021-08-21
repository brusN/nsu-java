package com.lab2.calculator.commands;

import com.lab2.calculator.CalculatorCommandSyntaxCheckerInterface;
import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;

import java.util.HashMap;
import java.util.Stack;

public class DefineCommand extends CalculatorCommand {

    @Override
    public boolean areArgumentsCorrect(String... args) throws CalculatorCommandArgumentsException {
        if (args.length != 3)
            throw new CalculatorCommandArgumentsException("Wrong count arguments!");
        if (!CalculatorCommandSyntaxCheckerInterface.isVariableNameCorrect(args[1]))
            throw new CalculatorCommandArgumentsException("Wrong variable name!");
        if (!CalculatorCommandSyntaxCheckerInterface.isDouble(args[2]))
            throw new CalculatorCommandArgumentsException("Wrong number!");
        return true;
    }

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the define command");
        if (areArgumentsCorrect(args))
            variables.put(args[1], Double.valueOf(args[2]));
    }
}
