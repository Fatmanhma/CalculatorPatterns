package Parser;

import Expr.*;
import java.util.Stack;

public class ExpressionParser {

    public static Expression parse(String expression) {
        return parseExpression(
                expression.replaceAll("×", "*")
                          .replaceAll("÷", "/")
        );
    }

    private static Expression parseExpression(String expr) {

        Stack<Expression> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {

            char c = expr.charAt(i);

            // رقم
            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c);
            }

            // فتح قوس
            else if (c == '(') {
                flushNumberBuffer(numberBuffer, values);
                ops.push(c);
            }

            // قفلة قوس
            else if (c == ')') {
                flushNumberBuffer(numberBuffer, values);

                while (!ops.isEmpty() && ops.peek() != '(') {
                    applyTopOperator(values, ops);
                }

                if (!ops.isEmpty()) ops.pop(); // remove '('
            }

            // العمليات
            else if (isOperator(c)) {
                flushNumberBuffer(numberBuffer, values);

                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    applyTopOperator(values, ops);
                }

                ops.push(c);
            }
        }

        flushNumberBuffer(numberBuffer, values);

        while (!ops.isEmpty()) {
            applyTopOperator(values, ops);
        }

        // safety
        if (values.isEmpty()) return new NumberExpression(0);
        if (values.size() > 1) throw new IllegalArgumentException("Invalid expression");

        return values.pop();
    }

    private static void flushNumberBuffer(StringBuilder numberBuffer, Stack<Expression> values) {
        if (numberBuffer.length() > 0) {
            float number = Float.parseFloat(numberBuffer.toString());
            values.push(new NumberExpression(number));
            numberBuffer.setLength(0);
        }
    }

    private static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/');
    }

    private static int precedence(char op) {
        return (op == '+' || op == '-') ? 1 :
               (op == '*' || op == '/') ? 2 :
               0;
    }

    private static void applyTopOperator(Stack<Expression> values, Stack<Character> ops) {
        Expression right = values.pop();
        Expression left = values.pop();
        char op = ops.pop();

        CompositeOperationExpression composite = new CompositeOperationExpression(op);
        composite.add(left);
        composite.add(right);

        values.push(composite);
    }
}
