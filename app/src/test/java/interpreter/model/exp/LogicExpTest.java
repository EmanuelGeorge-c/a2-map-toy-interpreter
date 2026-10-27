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

class LogicExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp boolVal(boolean b) {
        return new ValueExp(new BoolValue(b));
    }


    @Test
    void andOperatorWorksCorrectly() throws MyException {
        IExp trueAndTrue = new LogicExp(LogicOp.AND, boolVal(true), boolVal(true));
        IExp trueAndFalse = new LogicExp(LogicOp.AND, boolVal(true), boolVal(false));

        assertEquals(new BoolValue(true), trueAndTrue.eval(symTable));
        assertEquals(new BoolValue(false), trueAndFalse.eval(symTable));
    }

    @Test
    void orOperatorWorksCorrectly() throws MyException {
        IExp falseOrTrue = new LogicExp(LogicOp.OR, boolVal(false), boolVal(true));
        IExp falseOrFalse = new LogicExp(LogicOp.OR, boolVal(false), boolVal(false));

        assertEquals(new BoolValue(true), falseOrTrue.eval(symTable));
        assertEquals(new BoolValue(false), falseOrFalse.eval(symTable));
    }

    @Test
    void firstOperandNotBooleanThrows() {
        IExp exp = new LogicExp(
                LogicOp.AND,
                new ValueExp(new IntValue(10)),
                boolVal(true)
        );
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("first operand is not a boolean", ex.getMessage());
    }

    @Test
    void secondOperandNotBooleanThrows() {
        IExp exp = new LogicExp(
                LogicOp.OR,
                boolVal(false),
                new ValueExp(new IntValue(5))
        );
        var ex = assertThrows(ExpressionException.class, () -> exp.eval(symTable));
        assertEquals("second operand is not a boolean", ex.getMessage());
    }
}