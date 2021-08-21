package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.StackException;

import java.util.HashMap;
import java.util.Stack;

public class MultiplyCommand extends SingleCommand {

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the multiply command");
        if (areArgumentsCorrect(args)) {
            if (stack.size() < 2)
                throw new StackException("Not enough numbers on the stack!");
            double num1 = stack.pop();
            double num2 = stack.pop();
            stack.push(num1 * num2);
        }
    }

}
