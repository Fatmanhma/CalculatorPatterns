/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;


import operations.Operation;
import operations.OperationFactory;

public class CalculatorEngine {
    private String currentOperand;
    private String previousOperand;
    private String operation;
    private boolean resetScreen;

    public CalculatorEngine() {
        clear();
    }

    public void clear() {
        this.currentOperand = "0";
        this.previousOperand = "";
        this.operation = "";
        this.resetScreen = false;
    }

    public void appendNumber(String number) {
        if (resetScreen) {
            currentOperand = "";
            resetScreen = false;
        }
        
        if (number.equals(".") && currentOperand.contains(".")) {
            return;
        }
        
        if (currentOperand.equals("0") && !number.equals(".")) {
            currentOperand = number;
        } else {
            currentOperand += number;
        }
    }

    public void chooseOperation(String operation) {
        if (currentOperand.isEmpty()) return;

        if (!previousOperand.isEmpty()) {
            compute();
        }

        this.operation = operation;
        this.previousOperand = currentOperand;
        this.resetScreen = true;
    }

    public void compute() {
        if (previousOperand.isEmpty() || currentOperand.isEmpty() || operation.isEmpty()) {
            return;
        }

        try {
            float prev = Float.parseFloat(previousOperand);
            float curr = Float.parseFloat(currentOperand);

            Operation op = OperationFactory.createOperation(operation);
            float result = op.calculate(prev, curr);

            if (result == (int) result) {
                currentOperand = String.valueOf((int) result);
            } else {
                currentOperand = String.valueOf(result);
            }
            
            previousOperand = "";
            operation = "";
            resetScreen = true;

        } catch (ArithmeticException e) {
            currentOperand = "Error";
            previousOperand = "";
            operation = "";
        } catch (Exception e) {
            currentOperand = "Error";
        }
    }

    public void deleteLast() {
        if (currentOperand.length() > 1) {
            currentOperand = currentOperand.substring(0, currentOperand.length() - 1);
        } else {
            currentOperand = "0";
        }
    }

    public void toggleSign() {
        if (!currentOperand.equals("0") && !currentOperand.equals("Error")) {
            if (currentOperand.startsWith("-")) {
                currentOperand = currentOperand.substring(1);
            } else {
                currentOperand = "-" + currentOperand;
            }
        }
    }

    public String getCurrentDisplay() {
        return currentOperand;
    }

    public String getFullOperation() {
        if (previousOperand.isEmpty()) {
            return "";
        }
        return previousOperand + " " + operation;
    }
}