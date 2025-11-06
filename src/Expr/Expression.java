/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Expr;

import java.util.List;

public interface Expression {
    float evaluate();
    void add(Expression expr);
    void remove(Expression expr);
    List<Expression> getChildren();
    public String toString();
     
    
}

