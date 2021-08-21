package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;

import java.util.HashMap;
import java.util.Stack;

abstract public class SingleCommand extends CalculatorCommand {

    @Override
    public boolean areArgumentsCorrect(String[] args) throws CalculatorCommandArgumentsException {
        if (args.length != 1)
            throw new CalculatorCommandArgumentsException("Wrong count arguments!");
        return true;
    }
}
