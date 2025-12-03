/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.state;

import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * تم إدخال الرقم الأول.
 * تنتظر إما عملية (+) أو إدخال رقم جديد (يستبدل القديم).
 */
public class StateOperand1 implements CalculatorState {
    @Override
    public void handleNumber(CalculatorLogic context, float number) {
        context.setCurrentInput(number); // يستبدل الرقم الحالي
        // نبقى في نفس الحالة
    }

    @Override
    public void handleOperation(CalculatorLogic context, Operation op) { //
        context.setPreviousInput(context.getCurrentInput()); // خزّن الرقم الأول
        context.setPendingOperation(op); // خزّن العملية
        context.setState(new StateOperator()); // ننتقل لحالة انتظار الرقم الثاني
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
