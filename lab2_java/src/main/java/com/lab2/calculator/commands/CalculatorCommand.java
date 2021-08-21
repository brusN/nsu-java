package com.lab2.calculator.commands;

import com.lab2.calculator.exceptions.CalculatorCommandArgumentsException;
import com.lab2.calculator.exceptions.CalculatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Stack;

public abstract class CalculatorCommand {
    protected final Logger logger = LogManager.getLogger();
    abstract public boolean areArgumentsCorrect(String[] args) throws CalculatorCommandArgumentsException;
    abstract public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) throws CalculatorException;
}