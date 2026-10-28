package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> states;

    public Repository(PrgState initialState) {
        this.states = new ArrayList<>();
        this.states.add(initialState);
    }

    @Override
    public PrgState getCrtPrg() {
        return states.get(0);
    }
}