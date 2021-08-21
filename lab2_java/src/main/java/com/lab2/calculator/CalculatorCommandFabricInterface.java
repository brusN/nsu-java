package com.lab2.calculator;
import com.lab2.calculator.commands.CalculatorCommand;

import java.lang.reflect.InvocationTargetException;

public interface CalculatorCommandFabricInterface {
    CalculatorCommand createNewMathOperation(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
