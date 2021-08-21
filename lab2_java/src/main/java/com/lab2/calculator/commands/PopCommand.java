package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.StackException;

import java.util.HashMap;
import java.util.Stack;

public class PopCommand extends SingleCommand {

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the pop command");
        if (areArgumentsCorrect(args)) {
            if (stack.empty())
                throw new StackException("Not enough numbers on the stack!");
            stack.pop();
        }
    }
}
