/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package calculator.state;

import calculatorr.CalculatorLogic;
import operations.Operation;

/**
 *
 * @author Eng.Fatma
 */
public interface CalculatorState {
       
    public void handleOperation(CalculatorLogic context, Operation op);
        // لا تفعل شيئاً، لا يمكنك بدء بعملية
    

 
    public void handleEquals(CalculatorLogic context) ;
        // لا تفعل شيئاً
    
    
    
    public void handleClear(CalculatorLogic context);
        // نحن أصلاً في البداية، لا نفعل شيئاً
    
    public void handleNumber(CalculatorLogic context, float number);
}

    

