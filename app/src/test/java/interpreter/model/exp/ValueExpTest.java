package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void evaluatesToItselfForIntValue() throws MyException {
        IExp exp = new ValueExp(new IntValue(100));
        assertEquals(new IntValue(100), exp.eval(symTable));
    }

    @Test
    void evaluatesToItselfForBoolValue() throws MyException {
        IExp exp = new ValueExp(new BoolValue(true));
        assertEquals(new BoolValue(true), exp.eval(symTable));
    }

    @Test
    void toStringReturnsValueRepresentation() {
        IExp intExp = new ValueExp(new IntValue(42));
        IExp boolExp = new ValueExp(new BoolValue(false));

        assertEquals("42", intExp.toString());
        assertEquals("false", boolExp.toString());
    }
}