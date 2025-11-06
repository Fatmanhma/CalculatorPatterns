package Expr;

import java.util.ArrayList;
import java.util.List;
import operations.Operation;
import operations.OperationFactory;


public class CompositeOperationExpression implements Expression {
    private List<Expression> children = new ArrayList<>();
    private char operator;

     public CompositeOperationExpression(char operator) {
        this.operator = operator;
    }

    
    @Override
    public void add(Expression expr) {
        children.add(expr);
    }

    @Override
    public void remove(Expression expr) {
        children.remove(expr);
    }

    @Override
    public List<Expression> getChildren() {
        return new ArrayList<>(children);
    }

    
    @Override
    public float evaluate() {
    if (children.isEmpty()) {
        return 0;
    }

    float result = children.get(0).evaluate();

    Operation op = OperationFactory.createOperation(String.valueOf(operator));

    for (int i = 1; i < children.size(); i++) {
        float value = children.get(i).evaluate();
        result = op.calculate(result, value);
    }

    return result;
}
    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    for (int i = 0; i < children.size(); i++) {
        sb.append(children.get(i).toString());
        if (i < children.size() - 1) {
            sb.append(" ").append(operator).append(" ");
        }}
    sb.append(")");
    return sb.toString();
}}
