package core;

import Expr.Expression;
import Parser.ExpressionParser;
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
        currentOperand = "0";
        previousOperand = "";
        operation = "";
        resetScreen = false;
    }

    public void appendNumber(String number) {
        if (resetScreen) {
            currentOperand = "";
            resetScreen = false;
        }

        if (number.equals(".") && currentOperand.contains(".")) return;

        if (currentOperand.equals("0") && !number.equals(".")) {
            currentOperand = number;
        } else {
            currentOperand += number;
        }
    }

    public void chooseOperation(String operation) {
        if (currentOperand.isEmpty()) return;

        if (isConversionOperation(operation) || isSmartOperation(operation)) {
            this.operation = operation;
            compute(); // Execute directly
        } else {
            if (!previousOperand.isEmpty()) {
                compute();
            }
            this.operation = operation;
            this.previousOperand = currentOperand;
            this.resetScreen = true;
        }
    }

    public void compute() {
        try {
            float result;

            if (operation.isEmpty()) {
                result = evaluateExpression(currentOperand);
            } else if (isConversionOperation(operation) || isSmartOperation(operation)) {
                float current = Float.parseFloat(currentOperand);
                Operation op = OperationFactory.createOperation(operation);
                result = op.calculate(current, 0); // Pass dummy second operand
            } else {
                if (previousOperand.isEmpty() || currentOperand.isEmpty()) return;

                float prev = Float.parseFloat(previousOperand);
                float curr = Float.parseFloat(currentOperand);
                Operation op = OperationFactory.createOperation(operation);
                result = op.calculate(prev, curr);
            }

            if (Float.isNaN(result)) {
                currentOperand = "Error";
            } else if (result == (int) result) {
                currentOperand = String.valueOf((int) result);
            } else {
                currentOperand = String.format("%.2f", result);
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

    public float evaluateExpression(String expressionText) {
        try {
            Expression expr = ExpressionParser.parse(expressionText);
            return expr.evaluate();
        } catch (Exception e) {
            System.err.println("خطأ في التحليل: " + e.getMessage());
            return Float.NaN;
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
            currentOperand = currentOperand.startsWith("-")
                ? currentOperand.substring(1)
                : "-" + currentOperand;
        }
    }

    public void setCurrentOperand(String value) {
        this.currentOperand = value;
        this.previousOperand = "";
        this.operation = "";
        this.resetScreen = true;
    }

    public String getCurrentDisplay() {
        return currentOperand;
    }

    public String getFullOperation() {
        if (previousOperand.isEmpty()) {
            return operation.isEmpty() ? "" : currentOperand + " " + operation;
        }
        return previousOperand + " " + operation + " " + currentOperand;
    }

    // ==== Helpers =====
    private boolean isConversionOperation(String op) {
        return op.matches("R→\\$|\\$→R|C→F|F→C|kg→lb|cm→in");
    }

    private boolean isSmartOperation(String op) {
        return op.matches("[²³½2×]");
    }
}
