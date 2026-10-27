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

class VarExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void evaluatesDefinedVariableCorrectly() throws MyException {
        symTable.put("v", new IntValue(42));
        symTable.put("flag", new BoolValue(true));

        IExp varExp1 = new VarExp("v");
        IExp varExp2 = new VarExp("flag");

        assertEquals(new IntValue(42), varExp1.eval(symTable));
        assertEquals(new BoolValue(true), varExp2.eval(symTable));
    }

    @Test
    void undefinedVariableThrowsExpressionException() {
        IExp varExp = new VarExp("x");
        var ex = assertThrows(ExpressionException.class, () -> varExp.eval(symTable));
        assertEquals("variable x is not defined", ex.getMessage());
    }

    @Test
    void toStringReturnsVariableName() {
        IExp varExp = new VarExp("myVar");
        assertEquals("myVar", varExp.toString());
    }
}