package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorException;
import com.lab2.calculator.exceptions.MathOperationException;
import com.lab2.calculator.exceptions.StackException;

import java.util.HashMap;
import java.util.Stack;

public class DivisionCommand extends SingleCommand {
    private static final double ZERO = 0.0;
    private static final double EPSILON = 1e-5;

    boolean isZero(double number) {
        return (Math.abs(ZERO - number) < EPSILON);
    }

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException {
        logger.info("I start executing the division command");
        if (areArgumentsCorrect(args)) {
            if (stack.size() < 2) {
                throw new StackException("Not enough numbers on the stack!");
            }
            double num1 = stack.pop();
            double num2 = stack.pop();
            if (isZero(num2))
                throw new MathOperationException("Division by zero!");
            stack.push(num1 / num2);
        }
    }
}
