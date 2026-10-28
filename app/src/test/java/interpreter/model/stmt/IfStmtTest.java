package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void trueConditionPushesThenStmt() throws Exception {
        IStmt thenStmt = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseStmt = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), thenStmt, elseStmt);

        ifStmt.execute(state);

        assertEquals(thenStmt, state.getExeStack().pop());
    }

    @Test
    void falseConditionPushesElseStmt() throws Exception {
        IStmt thenStmt = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt elseStmt = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(false)), thenStmt, elseStmt);

        ifStmt.execute(state);

        assertEquals(elseStmt, state.getExeStack().pop());
    }

    @Test
    void nonBooleanConditionThrowsException() {
        IStmt ifStmt = new IfStmt(
                new ValueExp(new IntValue(10)),
                new NopStmt(),
                new NopStmt()
        );

        var ex = assertThrows(StatementException.class, () -> ifStmt.execute(state));
        assertEquals("conditional expression is not a boolean", ex.getMessage());
    }
}