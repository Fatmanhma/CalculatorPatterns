/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expr;

import java.util.List;

public class NumberExpression implements Expression {
    private float value;

    public NumberExpression(float value) {
        this.value = value;
    }

    @Override
    public float evaluate() {
        return value;
    }

    @Override
    public void add(Expression expr) {
    }

    @Override
    public void remove(Expression expr) {
     }

    @Override
    public List<Expression> getChildren() {
        return null;
    }
    @Override
   public String toString() {
    return String.valueOf(value);
}}

