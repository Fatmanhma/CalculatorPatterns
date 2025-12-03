/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculator.state;
import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * تم إدخال الرقم الثاني (مثل 10 + 5).
 * الآن جاهزون لـ (=) أو لعملية متسلسلة (مثل 10 + 5 *).
 */
public class StateOperand2 implements CalculatorState {
    @Override
    public void handleNumber(CalculatorLogic context, float number) {
        context.setCurrentInput(number); // يستبدل الرقم الثاني
        // نبقى في نفس الحالة
    }

    @Override
    public void handleOperation(CalculatorLogic context, Operation op) { //
        // عملية متسلسلة (مثل 10 + 5 * 2)
        // 1. نفذ العملية السابقة (10 + 5)
        float result = context.getPendingOperation().calculate( //
            context.getPreviousInput(), 
            context.getCurrentInput()
        );
        // 2. حضّر للعملية الجديدة
        context.setCurrentInput(result);
        context.setPreviousInput(result);
        context.setPendingOperation(op); //
        context.setState(new StateOperator()); // ارجع لحالة انتظار الرقم التالي
    }

    @Override
    public void handleEquals(CalculatorLogic context) {
        // 1. نفذ العملية
        float result = context.getPendingOperation().calculate( //
            context.getPreviousInput(), 
            context.getCurrentInput()
        );
        // 2. اعرض الناتج
        context.setCurrentInput(result);
        context.setState(new StateResult()); // ننتقل لحالة عرض الناتج
    }
    
    @Override
    public void handleClear(CalculatorLogic context) {
        context.setCurrentInput(0);
        context.setPreviousInput(0);
        context.setPendingOperation(null);
        context.setState(new StateStart()); // العودة للبداية
    }
}
