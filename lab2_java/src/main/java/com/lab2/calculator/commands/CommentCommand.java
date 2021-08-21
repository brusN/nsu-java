package com.lab2.calculator.commands;

import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Logger;

public class CommentCommand extends SingleCommand {

    @Override
    public boolean areArgumentsCorrect(String[] args) {
        return true;
    }

    @Override
    public void doWork(String[] args, HashMap<String, Double> variables, Stack<Double> stack) {
        logger.info("Comment has been recognized. I miss a line");
        // Comment command does nothing
    }
}