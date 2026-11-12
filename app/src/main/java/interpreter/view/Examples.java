package interpreter.view;

import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import interpreter.model.exp.Relop;

public class Examples {
    // int v; v=2; print(v)
    public static final IStmt EX1 = new CompStmt(
            new VarDeclStmt("v", new IntType()),
            new CompStmt(
                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))
            )
    );

    // int a; int b; a=2+3*5; b=a+1; print(b)
    public static final IStmt EX2 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new VarDeclStmt("b", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ArithExp(
                                    ArithOp.ADD,
                                    new ValueExp(new IntValue(2)),
                                    new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)))
                            )),
                            new CompStmt(
                                    new AssignStmt("b", new ArithExp(
                                            ArithOp.ADD,
                                            new VarExp("a"),
                                            new ValueExp(new IntValue(1))
                                    )),
                                    new PrintStmt(new VarExp("b"))
                            )
                    )
            )
    );

    // bool a; int v; a=true; (if a then v=2 else v=3); print(v)
    public static final IStmt EX3 = new CompStmt(
            new VarDeclStmt("a", new BoolType()),
            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(
                                    new IfStmt(
                                            new VarExp("a"),
                                            new AssignStmt("v", new ValueExp(new IntValue(2))),
                                            new AssignStmt("v", new ValueExp(new IntValue(3)))
                                    ),
                                    new PrintStmt(new VarExp("v"))
                            )
                    )
            )
    );

    //bool a; a=2; print(a);
    public static final IStmt EX4 = new CompStmt(
      new VarDeclStmt("a",new BoolType()),
      new  CompStmt(
              new AssignStmt("a", new ValueExp(new IntValue(2))),
              new PrintStmt(new VarExp("a"))
      )
    );

    // string varf; varf = "test.in"; openRFile(varf);
    // int varc; readFile(varf, varc); print(varc);
    // readFile(varf, varc); print(varc); closeRFile(varf);

    public static final IStmt EX5 = new CompStmt(
            new VarDeclStmt("varf", new StringType()),
            new CompStmt(
                    new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
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
                                                                    new CloseRFileStmt(new VarExp("varf"))
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
    );

    // int a; int b; a = 2; b = 3;
    // if (a < b) then print(5) else print(10);
    public static final IStmt EX6 = new CompStmt(
            new VarDeclStmt("a", new IntType()),
            new CompStmt(
                    new VarDeclStmt("b", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ValueExp(new IntValue(2))),
                            new CompStmt(
                                    new AssignStmt("b", new ValueExp(new IntValue(3))),
                                    new IfStmt(
                                            new RelationalExp(Relop.LT, new VarExp("a"), new VarExp("b")),
                                            new PrintStmt(new ValueExp(new IntValue(5))),
                                            new PrintStmt(new ValueExp(new IntValue(10)))
                                    )
                            )
                    )
            )
    );

}