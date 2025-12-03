package calculatorr;

import operations.Operation;
import calculator.state.*; 
// إضافات لدعم Composite Pattern والبارسر الجديد
import Parser.ExpressionParser;
import Expr.Expression;

public class CalculatorLogic {
    
    // --- متغيرات الحالة الداخلية ---
    private float currentInput = 0;
    private float previousInput = 0;
    private Operation pendingOperation = null;
    
    // --- متغير نمط الحالة (State Pattern) ---
    private CalculatorState currentState; 

    public CalculatorLogic() {
        // نبدأ دائماً بالحالة الابتدائية
        currentState = new StateStart();
    }
    
    // ============================================================
    // 1. دوال نمط Command (العمليات العادية)
    // ============================================================
    
    public void setOperand(float number) {
        currentState.handleNumber(this, number);
        // System.out.println("  State: " + currentState.getClass().getSimpleName()); // للتجربة
    }
    
    public void setOperation(Operation op) {
        currentState.handleOperation(this, op);
    }

    public void performCalculation() {
        currentState.handleEquals(this);
    }
    
    public void clear() {
        currentState.handleClear(this);
    }

    // ============================================================
    // 2. دالة نمط Composite (التحليل الذكي للمعادلات) - إضافة جديدة
    // ============================================================
    
    /**
     * هذه الدالة تستخدم ExpressionParser لتحليل سلسلة نصية كاملة
     * (مفيدة للأقواس أو المعادلات المعقدة)
     */
    public void calculateExpression(String expressionText) {
        try {
            // استخدام البارسر (Composite Pattern)
            Expression expr = ExpressionParser.parse(expressionText);
            float result = expr.evaluate();
            
            // تحديث حالة النظام بالنتيجة
            this.currentInput = result;
            this.previousInput = 0;
            this.pendingOperation = null;
            
            // ننتقل لحالة النتيجة ليكون جاهزاً للعمليات التالية
            this.currentState = new StateResult();
            
            System.out.println("Expression Calculated: " + expressionText + " = " + result);
            
        } catch (Exception e) {
            System.err.println("Error parsing expression: " + e.getMessage());
            this.currentInput = Float.NaN; // أو 0 ليدل على خطأ
            this.currentState = new StateResult();
        }
    }

    // ============================================================
    // 3. دوال مساعدة (Getters/Setters) ونمط Undo
    // ============================================================
    
    public float getCurrentInput() { return currentInput; }
    public void setCurrentInput(float val) { this.currentInput = val; }
    
    public float getPreviousInput() { return previousInput; }
    public void setPreviousInput(float val) { this.previousInput = val; }
    
    public Operation getPendingOperation() { return pendingOperation; }
    public void setPendingOperation(Operation op) { this.pendingOperation = op; }
    
    public float getCurrentDisplay() {
        return currentInput;
    }
    
    public void setState(CalculatorState newState) {
        this.currentState = newState;
    }
    
    // دالة التراجع (يستدعيها OperationCommand.undo)
    public void restoreState(float current, float prev, Operation op) {
        this.currentInput = current;
        this.previousInput = prev;
        this.pendingOperation = op;
        
        // عند التراجع، من الأسلم العودة لحالة انتظار رقم أو عملية
        // هنا نفترض العودة لـ Operand1 كحالة آمنة
        this.currentState = new StateOperand1();
    }
}