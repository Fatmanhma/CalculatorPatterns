/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operations.adaptees;

public class ConversionOperations {
    
    public float sarToUsd(float sar) {
        return sar / 3.75f; //
    }
    
    public float usdToSar(float usd) {
        return usd * 3.75f; //
    }
    
    public float celsiusToFahrenheit(float c) {
        return (c * 9/5) + 32; //
    }
    
    public float fahrenheitToCelsius(float f) {
        return (f - 32) * 5/9; //
    }
    
    public float kgToLb(float kg) {
        return kg * 2.20462f; //
    }
    
    public float lbToKg(float lb) {
        return lb / 2.20462f; //
    }
    
    public float cmToInch(float cm) {
        return cm / 2.54f; //
    }
    
    public float inchToCm(float in) {
        return in * 2.54f; //
    }
}
