package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
    }

    @Test
    void declareIntVariableSetsDefaultZero() throws Exception {
        IStmt decl = new VarDeclStmt("x", new IntType());
        decl.execute(state);

        assertTrue(state.getSymTable().isDefined("x"));
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void declareBoolVariableSetsDefaultFalse() throws Exception {
        IStmt decl = new VarDeclStmt("flag", new BoolType());
        decl.execute(state);

        assertTrue(state.getSymTable().isDefined("flag"));
        assertEquals(new BoolValue(false), state.getSymTable().lookup("flag"));
    }

    @Test
    void declareAlreadyDeclaredVariableThrowsException() throws Exception {
        IStmt decl = new VarDeclStmt("x", new IntType());
        decl.execute(state);

        var ex = assertThrows(StatementException.class, () -> decl.execute(state));
        assertEquals("variable x is already declared", ex.getMessage());
    }
}