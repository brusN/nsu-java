package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.StackException;

import java.util.HashMap;
import java.util.Stack;

public class PrintCommand extends SingleCommand {

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the print command");
        if (areArgumentsCorrect(args)) {
            if (stack.empty())
                throw new StackException("Stack is empty!");
            System.out.println(stack.peek());
        }
    }
}
