/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operations;

public class OperationFactory {
    public static Operation createOperation(String operator) {
        switch (operator) {
            case "+": return new Addition();
            case "-": return new Subtraction();
            case "×": return new Multiplication();
            case "÷": return new Division();
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}