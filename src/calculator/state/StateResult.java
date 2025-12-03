package calculator.state;
import calculatorr.CalculatorLogic;
import operations.Operation; //

/**
 * تم عرض الناتج على الشاشة.
 */
public class StateResult implements CalculatorState {
    @Override
    public void handleNumber(CalculatorLogic context, float number) {
        // بدء عملية حسابية جديدة
        context.setCurrentInput(number);
        context.setState(new StateOperand1());
    }

    @Override
    public void handleOperation(CalculatorLogic context, Operation op) { //
        // استخدام الناتج كرقم أول لعملية جديدة
        context.setPreviousInput(context.getCurrentInput());
        context.setPendingOperation(op); //
        context.setState(new StateOperator());
    }

    @Override
    public void handleEquals(CalculatorLogic context) {
        // لا تفعل شيئاً (أو يمكن جعلها تكرر آخر عملية)
    }
    
    @Override
    public void handleClear(CalculatorLogic context) {
        context.setCurrentInput(0);
        context.setPreviousInput(0);
        context.setPendingOperation(null);
        context.setState(new StateStart()); // العودة للبداية
    }
}
