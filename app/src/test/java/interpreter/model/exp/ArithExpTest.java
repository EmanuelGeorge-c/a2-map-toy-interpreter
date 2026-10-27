package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void additionWorksCorrectly() throws MyException {
        IExp exp = new ArithExp(ArithOp.ADD, num(10), num(5));
        assertEquals(new IntValue(15), exp.eval(symTable));
    }

    @Test
    void subtractionWorksCorrectly() throws MyException {
        IExp exp = new ArithExp(ArithOp.SUB, num(10), num(4));
        assertEquals(new IntValue(6), exp.eval(symTable));
    }

    @Test
    void multiplicationWorksCorrectly() throws MyException {
        IExp exp = new ArithExp(ArithOp.MUL, num(3), num(4));
        assertEquals(new IntValue(12), exp.eval(symTable));
    }

    @Test
    void divisionWorksCorrectly() throws MyException {
        IExp exp = new ArithExp(ArithOp.DIV, num(20), num(5));
        assertEquals(new IntValue(4), exp.eval(symTable));
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5))
        );
        assertEquals(new IntValue(17), exp.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp exp = new ArithExp(ArithOp.DIV, num(10), num(0));
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("division by zero", ex.getMessage());
    }

    @Test
    void firstOperandNotIntegerThrows() {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                new ValueExp(new BoolValue(true)),
                num(5)
        );
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("first operand is not an integer", ex.getMessage());
    }

    @Test
    void secondOperandNotIntegerThrows() {
        IExp exp = new ArithExp(
                ArithOp.ADD,
                num(5),
                new ValueExp(new BoolValue(false))
        );
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("second operand is not an integer", ex.getMessage());
    }
}