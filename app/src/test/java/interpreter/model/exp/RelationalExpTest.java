package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testAllRelationalOperators() throws MyException {
        ValueExp val3 = new ValueExp(new IntValue(3));
        ValueExp val5 = new ValueExp(new IntValue(5));

        assertTrue(((BoolValue) new RelationalExp(Relop.LT, val3, val5).eval(symTable)).val());
        assertTrue(((BoolValue) new RelationalExp(Relop.LE, val3, val5).eval(symTable)).val());
        assertFalse(((BoolValue) new RelationalExp(Relop.EQ, val3, val5).eval(symTable)).val());
        assertTrue(((BoolValue) new RelationalExp(Relop.NE, val3, val5).eval(symTable)).val());
        assertFalse(((BoolValue) new RelationalExp(Relop.GT, val3, val5).eval(symTable)).val());
        assertFalse(((BoolValue) new RelationalExp(Relop.GE, val3, val5).eval(symTable)).val());
    }

    @Test
    void testNonIntegerFirstOperandThrowsException() {
        var invalidExp = new RelationalExp(Relop.LT, new ValueExp(new StringValue("abc")), new ValueExp(new IntValue(5)));
        assertThrows(ExpressionException.class, () -> invalidExp.eval(symTable));
    }

    @Test
    void testNonIntegerSecondOperandThrowsException() {
        var invalidExp = new RelationalExp(Relop.LT, new ValueExp(new IntValue(5)), new ValueExp(new StringValue("abc")));
        assertThrows(ExpressionException.class, () -> invalidExp.eval(symTable));
    }
}