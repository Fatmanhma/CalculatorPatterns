/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.state;
import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * تم إدخال الرقم الأول والعملية (مثل 10 +).
 * تنتظر إدخال الرقم الثاني.
 */
public class StateOperator implements CalculatorState {
    @Override
    public void handleNumber(CalculatorLogic context, float number) {
        context.setCurrentInput(number); // هذا هو الرقم الثاني
        context.setState(new StateOperand2()); // ننتقل لحالة الرقم الثاني
    }

    @Override
    public void handleOperation(CalculatorLogic context, Operation op) { //
        context.setPendingOperation(op); // استبدال العملية (مثل 10 + * 5)
        // نبقى في نفس الحالة
    }

    @Override
    public void handleEquals(CalculatorLogic context) {
        // لا تفعل شيئاً
    }
    
    @Override
    public void handleClear(CalculatorLogic context) {
        context.setCurrentInput(0);
        context.setPreviousInput(0);
        context.setPendingOperation(null);
        context.setState(new StateStart()); // العودة للبداية
    }
}
