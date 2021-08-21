package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.StackException;

import java.util.HashMap;
import java.util.Stack;

public class SqrtCommand extends SingleCommand {

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the sqrt command");
        if (areArgumentsCorrect(args)) {
            if (stack.empty())
                throw new StackException("Not enough numbers on the stack!");
            stack.push(Math.sqrt(stack.pop()));
        }
    }
}
