package interpreter.repository;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.exceptions.RepositoryException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.stmt.AssignStmt;
import interpreter.model.stmt.CompStmt;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.PrintStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryLogTest {

    @TempDir
    Path tempDir;

    @Test
    void testLogFileContainsSectionsInCorrectOrderAfterAllStep() throws MyException, IOException {
        Path logFile = tempDir.resolve("test_log.txt");

        IStmt program = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new interpreter.model.exp.VarExp("v"))
                )
        );

        PrgState state = PrgState.of(program);
        IRepository repo = new Repository(state, logFile.toString());
        Controller controller = new Controller(repo,false);

        controller.allStep();

        assertTrue(Files.exists(logFile));
        String content = Files.readString(logFile);

        int idxExeStack = content.indexOf("ExeStack:");
        int idxSymTable = content.indexOf("SymTable:");
        int idxOut = content.indexOf("Out:");
        int idxFileTable = content.indexOf("FileTable:");

        assertTrue(idxExeStack >= 0, "Log should contain ExeStack:");
        assertTrue(idxSymTable > idxExeStack, "SymTable: should appear after ExeStack:");
        assertTrue(idxOut > idxSymTable, "Out: should appear after SymTable:");
        assertTrue(idxFileTable > idxOut, "FileTable: should appear after Out:");
    }

    @Test
    void testInvalidLogPathThrowsRepositoryException() {
        Path invalidPath = tempDir.resolve("non_existent_dir").resolve("log.txt");
        PrgState state = PrgState.of(new VarDeclStmt("v", new IntType()));
        IRepository repo = new Repository(state, invalidPath.toString());

        assertThrows(RepositoryException.class, repo::logPrgStateExec);
    }
}