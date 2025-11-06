package operations;

public class OperationFactory {

    public static Operation createOperation(String operator) {
        switch (operator) {

            // العمليات الأساسية
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
            case "×":
                return new Multiplication(); 
            case "/":
            case "÷":
                return new Division();       

            // العمليات الذكية (مربع، تكعيب، نصف)
            case "²":
                return new SmartOperationsAdapter("square");
            case "³":
                return new SmartOperationsAdapter("cube");
            case "½":
                return new SmartOperationsAdapter("half");

            // تحويلات العملات
            case "R→$":
                return new ConversionAdapter("SAR_TO_USD");
            case "$→R":
                return new ConversionAdapter("USD_TO_SAR");

            // تحويلات الحرارة
            case "C→F":
                return new ConversionAdapter("C_TO_F");
            case "F→C":
                return new ConversionAdapter("F_TO_C");

            // تحويلات الوزن والطول
            case "kg→lb":
                return new ConversionAdapter("KG_TO_LB");
            case "cm→in":
                return new ConversionAdapter("CM_TO_INCH");

            // خطأ في حال وجود عامل غير معروف
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
