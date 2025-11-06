/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculator;

import Expr.CompositeOperationExpression;
import Expr.Expression;
import Expr.NumberExpression;
import app.CalculatorApp;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        CalculatorApp.getInstance();
        
        // (5 + 5)
        Expression five1 = new NumberExpression(5);
        Expression five2 = new NumberExpression(5);
        Expression addGroup = new CompositeOperationExpression('+');
        addGroup.add(five1);
        addGroup.add(five2);

        // (10 + 10)
        Expression ten1 = new NumberExpression(10);
        Expression ten2 = new NumberExpression(10);
        Expression addGroup2 = new CompositeOperationExpression('+');
        addGroup2.add(ten1);
        addGroup2.add(ten2);

        // ((5+5) * (10+10))
        Expression multiplyGroup = new CompositeOperationExpression('*');
        multiplyGroup.add(addGroup);
        multiplyGroup.add(addGroup2);

        // Evaluate the whole thing
        float result = multiplyGroup.evaluate();
        List<Expression> x=multiplyGroup.getChildren();
        System.out.println("Result of ((5 + 5) * (10 + 10)) = " + result);
        System.out.println(x);
}}

        

