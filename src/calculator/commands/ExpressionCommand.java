package calculator.commands;

import calculatorr.CalculatorLogic;

public class ExpressionCommand implements Command {
    private CalculatorLogic logic;
    private String expression;
    private float oldVal; // للتراجع

    public ExpressionCommand(CalculatorLogic logic, String expression) {
        this.logic = logic;
        this.expression = expression;
    }

    @Override
    public void execute() {
        oldVal = logic.getCurrentDisplay(); // حفظ القيمة القديمة
        logic.calculateExpression(expression); // تنفيذ البارسر
    }

    @Override
    public void undo() {
        logic.setOperand(oldVal); // استرجاع القيمة
    }
}