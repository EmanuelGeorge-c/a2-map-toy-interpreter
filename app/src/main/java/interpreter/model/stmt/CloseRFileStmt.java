package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName)) {
            throw new StatementException("closeRFile: " + exp + " is not a string");
        }

        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName)) {
            throw new StatementException("closeRFile: file " + fileName.val() + " is not open");
        }

        var reader = fileTable.lookup(fileName);
        try {
            reader.close();
            fileTable.remove(fileName);
        } catch (IOException e) {
            throw new StatementException("closeRFile: cannot close " + fileName.val() + ": " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}