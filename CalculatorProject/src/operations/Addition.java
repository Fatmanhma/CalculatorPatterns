/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operations;



public class Addition implements Operation {
    @Override
    public float calculate(float a, float b) {
        return a + b;
    }
    
    @Override
    public String getSymbol() {
        return "+";
    }
}