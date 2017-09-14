package MDP;

import java.util.ArrayList;

/**
 * Created by Jan on 14-9-2017.
 */
public class State {

    Block inClaw = null;
    Table table = new Table();

    public State() {

    }

    public State(Block inClaw, Table table) {
        this.inClaw = inClaw;
        this.table = table.copy();
    }

    public State(ArrayList<Tower> table) {
        this.table.addAll(table);
    }

    public State(Block inClaw) {
        this.inClaw = inClaw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        if (inClaw != null ? !inClaw.equals(state.inClaw) : state.inClaw != null) return false;
        return table.equals(state.table);
    }

    @Override
    public int hashCode() {
        int result = inClaw != null ? inClaw.hashCode() : 0;
        result = 31 * result + table.hashCode();
        return result;
    }

    static State copy(State s){
        return new State(s.inClaw, s.table);
    }
}
