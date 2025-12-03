/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.state;

import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * الحالة الابتدائية (الشاشة = 0).
 * تنتظر إدخال الرقم الأول.
 */
public class StateStart implements CalculatorState {
    @Override
    public void handleNumber(CalculatorLogic context, float number) {
        context.setCurrentInput(number);
        context.setState(new StateOperand1()); // ننتقل لحالة الرقم الأول
    }

    @Override
    public void handleOperation(CalculatorLogic context, Operation op) { //
        // لا تفعل شيئاً، لا يمكنك بدء بعملية
    }

    @Override
    public void handleEquals(CalculatorLogic context) {
        // لا تفعل شيئاً
    }
    
    @Override
    public void handleClear(CalculatorLogic context) {
        // نحن أصلاً في البداية، لا نفعل شيئاً
    }
}
