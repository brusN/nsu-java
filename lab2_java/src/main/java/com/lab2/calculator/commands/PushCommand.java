package com.lab2.calculator.commands;

import com.lab2.calculator.CalculatorCommandSyntaxCheckerInterface;
import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;

import java.util.HashMap;
import java.util.Stack;

public class PushCommand extends CalculatorCommand {

    @Override
    public boolean areArgumentsCorrect(String[] args) throws CalculatorCommandArgumentsException {
        if (args.length != 2)
            throw new CalculatorCommandArgumentsException("Wrong count arguments!");
        if (!CalculatorCommandSyntaxCheckerInterface.isDouble(args[1]))
            throw new CalculatorCommandArgumentsException("Can't define, wrong number!");
        return true;
    }

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the push command");
        if (areArgumentsCorrect(args)) {
            stack.push(Double.valueOf(args[1]));
        }
    }
}
