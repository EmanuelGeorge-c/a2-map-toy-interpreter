package interpreter.model.stmt;

import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void executionPushesSecondThenFirstToStack() throws Exception {
        IStmt stmt1 = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt stmt2 = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt comp = new CompStmt(stmt1, stmt2);

        comp.execute(state);

        assertEquals(stmt1, state.getExeStack().pop());
        assertEquals(stmt2, state.getExeStack().pop());
    }
}