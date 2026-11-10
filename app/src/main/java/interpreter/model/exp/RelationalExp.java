package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;

public record RelationalExp(Relop op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        IValue v1 = left.eval(symTable);
        if (!(v1 instanceof IntValue(int n1))) {
            throw new ExpressionException("first operand is not integer: " + v1);
        }

        IValue v2 = right.eval(symTable);
        if (!(v2 instanceof IntValue(int n2))) {
            throw new ExpressionException("second operand is not integer: " + v2);
        }

        boolean result = switch (op) {
            case LT -> n1 < n2;
            case LE -> n1 <= n2;
            case EQ -> n1 == n2;
            case NE -> n1 != n2;
            case GT -> n1 > n2;
            case GE -> n1 >= n2;
        };

        return new BoolValue(result);
    }

    @Override
    public String toString() {
        return "(" + left + " " + op + " " + right + ")";
    }
}