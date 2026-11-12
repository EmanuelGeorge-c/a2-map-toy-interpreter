package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path tempDir;

    @Test
    void testFileOperationsFullFlowAndEOF() throws IOException, MyException {
        Path testFile = tempDir.resolve("test.in");
        Files.writeString(testFile, "15\n42\n");

        // string varf; varf = "<path>"; openRFile(varf); int varc;
        // readFile(varf, varc); print(varc); readFile(varf, varc); print(varc);
        // readFile(varf, varc); print(varc); closeRFile(varf);
        IStmt program = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue(testFile.toString()))),
                        new CompStmt(
                                new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(
                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CompStmt(
                                                                                new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("varc")),
                                                                                        new CloseRFileStmt(new VarExp("varf"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        PrgState state = PrgState.of(program);

        while (!state.getExeStack().isEmpty()) {
            IStmt currentStmt = state.getExeStack().pop();
            currentStmt.execute(state);
        }

        // Verify output: 15, 42, and 0 on EOF
        assertEquals(3, state.getOut().size());
        assertEquals(new IntValue(15), state.getOut().get(0));
        assertEquals(new IntValue(42), state.getOut().get(1));
        assertEquals(new IntValue(0), state.getOut().get(2));

        // Verify the file was removed from FileTable after closeRFile
        assertFalse(state.getFileTable().isDefined(new StringValue(testFile.toString())));
    }

    @Test
    void testOpenNonExistentFileThrowsException() {
        Path missingFile = tempDir.resolve("does_not_exist.in");
        IStmt stmt = new OpenRFileStmt(new ValueExp(new StringValue(missingFile.toString())));
        PrgState state = PrgState.of(stmt);

        assertThrows(MyException.class, () -> stmt.execute(state));
    }

    @Test
    void testOpenAlreadyOpenedFileThrowsException() throws IOException, MyException {
        Path testFile = tempDir.resolve("duplicate.in");
        Files.writeString(testFile, "10\n");

        IStmt openTwice = new CompStmt(
                new OpenRFileStmt(new ValueExp(new StringValue(testFile.toString()))),
                new OpenRFileStmt(new ValueExp(new StringValue(testFile.toString())))
        );
        PrgState state = PrgState.of(openTwice);

        // Execute CompStmt and first OpenRFileStmt
        state.getExeStack().pop().execute(state);
        state.getExeStack().pop().execute(state);

        // Second OpenRFileStmt must throw MyException
        IStmt secondOpen = state.getExeStack().pop();
        assertThrows(MyException.class, () -> secondOpen.execute(state));

        // Cleanup open file handle on Windows
        new CloseRFileStmt(new ValueExp(new StringValue(testFile.toString()))).execute(state);
    }

    @Test
    void testOpenWithNonStringExpressionThrowsException() {
        IStmt stmt = new OpenRFileStmt(new ValueExp(new IntValue(123)));
        PrgState state = PrgState.of(stmt);

        assertThrows(MyException.class, () -> stmt.execute(state));
    }

    @Test
    void testReadFileExceptions() throws IOException, MyException {
        Path testFile = tempDir.resolve("read_err.in");
        Files.writeString(testFile, "99\n");

        // 1. Variable not declared in SymTable
        IStmt readUndeclared = new ReadFileStmt(new ValueExp(new StringValue(testFile.toString())), "missingVar");
        PrgState state1 = PrgState.of(readUndeclared);
        assertThrows(MyException.class, () -> readUndeclared.execute(state1));

        // 2. Variable is not of IntType
        IStmt readWrongVarType = new CompStmt(
                new VarDeclStmt("strVar", new StringType()),
                new ReadFileStmt(new ValueExp(new StringValue(testFile.toString())), "strVar")
        );
        PrgState state2 = PrgState.of(readWrongVarType);
        state2.getExeStack().pop().execute(state2);
        state2.getExeStack().pop().execute(state2);
        IStmt readStmt2 = state2.getExeStack().pop();
        assertThrows(MyException.class, () -> readStmt2.execute(state2));

        // 3. File is not opened in FileTable
        IStmt readUnopenedFile = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new ReadFileStmt(new ValueExp(new StringValue(testFile.toString())), "x")
        );
        PrgState state3 = PrgState.of(readUnopenedFile);
        state3.getExeStack().pop().execute(state3);
        state3.getExeStack().pop().execute(state3);
        IStmt readStmt3 = state3.getExeStack().pop();
        assertThrows(MyException.class, () -> readStmt3.execute(state3));
    }

    @Test
    void testCloseUnopenedFileThrowsException() {
        Path testFile = tempDir.resolve("not_opened.in");
        IStmt closeStmt = new CloseRFileStmt(new ValueExp(new StringValue(testFile.toString())));
        PrgState state = PrgState.of(closeStmt);

        assertThrows(MyException.class, () -> closeStmt.execute(state));
    }
}